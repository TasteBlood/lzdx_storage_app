package com.cloudcreativity.storage.ui.restaurantPrice;

import android.databinding.ObservableField;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.base.BaseActivity;
import com.cloudcreativity.storage.base.BaseBindingRecyclerViewAdapter;
import com.cloudcreativity.storage.base.BaseDialogImpl;
import com.cloudcreativity.storage.base.BaseModel;
import com.cloudcreativity.storage.base.BaseResult;
import com.cloudcreativity.storage.databinding.ActivityRestaurentPriceBinding;
import com.cloudcreativity.storage.databinding.ItemLayoutRestaurantSpecsItemBinding;
import com.cloudcreativity.storage.databinding.ItemRestaurantPriceLayoutBinding;
import com.cloudcreativity.storage.entity.Category;
import com.cloudcreativity.storage.entity.Goods;
import com.cloudcreativity.storage.entity.Provider;
import com.cloudcreativity.storage.utils.CategoryWindowUtils;
import com.cloudcreativity.storage.utils.DefaultObserver;
import com.cloudcreativity.storage.utils.HttpUtils;
import com.cloudcreativity.storage.utils.RestaurantPriceUtils;
import com.cloudcreativity.storage.utils.ToastUtils;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RestaurantPriceModel extends BaseModel<BaseActivity, ActivityRestaurentPriceBinding> {

    public ObservableField<String> key = new ObservableField<>();
    public ObservableField<String> selectCategory = new ObservableField<>();
    private int pageNum = 1;
    private int pageSize = 20;
    private int oneId;
    private int twoId;
    public BaseBindingRecyclerViewAdapter<Goods.Entity, ItemRestaurantPriceLayoutBinding> adapter;
    private RestaurantPriceUtils priceUtils;
    private Provider.Entity provider;

    RestaurantPriceModel(BaseActivity context, ActivityRestaurentPriceBinding binding, BaseDialogImpl baseDialog) {
        super(context, binding, baseDialog);
        selectCategory.set("选择分类");
    }

    @Override
    protected void initView() {
        binding.tlbRestaurant.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.finish();
            }
        });

        binding.refreshRestaurant.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                pageNum = 1;
                loadData(pageNum,pageSize,key.get());
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                loadData(pageNum,pageSize,key.get());
            }
        });

        binding.rcvRestaurant.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
    }

    private void loadData(final int page, int size, String key) {
        HttpUtils.getInstance().getFullGoods(oneId,twoId,key,pageNum,size,2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<Goods>(getBaseDialog()) {
                    @Override
                    public void onSuccess(Goods goods) {
                        if(page==1){
                            binding.refreshRestaurant.finishRefreshing();
                        }else{
                            binding.refreshRestaurant.finishLoadmore();
                        }

                        if(goods.getInfo().getResultlist().isEmpty()){
                            //没数据
                            if(page==1){
                                adapter.getItems().clear();
                            }

                        }else{
                            if(page==1){
                                adapter.getItems().clear();
                            }
                            adapter.getItems().addAll(goods.getInfo().getResultlist());
                            pageNum ++;
                        }
                    }

                    @Override
                    public void onFail(ExceptionReason msg) {
                        super.onFail(msg);
                        if(page==1){
                            binding.refreshRestaurant.finishRefreshing();
                        }else{
                            binding.refreshRestaurant.finishLoadmore();
                        }
                    }
                });
    }

    @Override
    protected void initData() {

        adapter = new BaseBindingRecyclerViewAdapter<Goods.Entity, ItemRestaurantPriceLayoutBinding>(context) {
            @Override
            protected int getLayoutResId(int viewType) {
                return R.layout.item_restaurant_price_layout;
            }

            @Override
            protected void onBindItem(ItemRestaurantPriceLayoutBinding binding, Goods.Entity goods, final int groupPos) {
                binding.setItem(goods);
                final BaseBindingRecyclerViewAdapter<Goods.Specs, ItemLayoutRestaurantSpecsItemBinding> adapter =
                        new BaseBindingRecyclerViewAdapter<Goods.Specs, ItemLayoutRestaurantSpecsItemBinding>(context) {
                            @Override
                            protected int getLayoutResId(int viewType) {
                                return R.layout.item_layout_restaurant_specs_item;
                            }

                            @Override
                            protected void onBindItem(ItemLayoutRestaurantSpecsItemBinding binding, final Goods.Specs item, int position) {
                                binding.setItem(item);
                                binding.tvPrice.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        priceUtils = new RestaurantPriceUtils(context, R.style.myProgressDialogStyle,RestaurantPriceModel.this.context);

                                        if(provider!=null)
                                            priceUtils.setProvider(provider);

                                        priceUtils.setOnOkListener(new RestaurantPriceUtils.OnOkListener() {
                                            @Override
                                            public void onOk(Provider.Entity entity, float price, String address, String remarks) {
                                                addPrice(item.getGoodsId(),item.getId(),Float.valueOf(price*100).intValue(),address,remarks,entity.getId(),groupPos,item);
                                            }
                                        });
                                        priceUtils.show();

                                    }
                                });
                            }
                        };
                adapter.getItems().addAll(goods.getSpecsDomains());
                binding.rcvSpecs.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
                binding.rcvSpecs.setAdapter(adapter);
            }
        };

        binding.refreshRestaurant.startRefresh();
    }

    void onResult(Provider.Entity entity){
        priceUtils.setProvider(entity);
        this.provider = entity;
    }

    //添加采价
    private void addPrice(int goodsId, int id, final int money, String address, String remarks, int providerId, final int groupPos, final Goods.Specs item) {
        HttpUtils.getInstance().addRestaurantPrice(goodsId,id,providerId,money,remarks,address)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BaseResult>(context,true) {
                    @Override
                    public void onSuccess(BaseResult baseResult) {
                        ToastUtils.showShortToast(context,"采价成功");
                        item.setPrice(money);
                        adapter.notifyItemChanged(groupPos);

                    }
                });
    }

    public void onSearch(){
        binding.refreshRestaurant.startRefresh();
    }

    public void onCategoryClick(){
        CategoryWindowUtils categoryWindowUtils = new CategoryWindowUtils(context,getBaseDialog());
        categoryWindowUtils.show();
        categoryWindowUtils.setOnChooseListener(new CategoryWindowUtils.OnChooseListener() {
            @Override
            public void onChoose(Category.Entity one, Category.Entity two) {
                if(one!=null){
                    selectCategory.set(one.getName());
                    oneId = one.getId();
                }
                if(two!=null){
                    selectCategory.set(selectCategory.get()+"/"+two.getName());
                    twoId = two.getId();
                }

                binding.refreshRestaurant.startRefresh();
            }

            @Override
            public void onClear() {
                oneId = 0;
                twoId = 0;
                selectCategory.set("选择分类");
                binding.refreshRestaurant.startRefresh();
            }
        });
    }
}

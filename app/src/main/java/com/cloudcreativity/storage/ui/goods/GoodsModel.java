package com.cloudcreativity.storage.ui.goods;

import android.content.Intent;
import android.databinding.ObservableField;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.base.BaseActivity;
import com.cloudcreativity.storage.base.BaseBindingRecyclerViewAdapter;
import com.cloudcreativity.storage.base.BaseDialogImpl;
import com.cloudcreativity.storage.base.BaseModel;
import com.cloudcreativity.storage.base.BaseResult;
import com.cloudcreativity.storage.base.CommonWebActivity;
import com.cloudcreativity.storage.databinding.ActivityGoodsBinding;
import com.cloudcreativity.storage.databinding.ItemLayoutGoodsBinding;
import com.cloudcreativity.storage.entity.Category;
import com.cloudcreativity.storage.entity.StoreGoods;
import com.cloudcreativity.storage.utils.CategoryWindowUtils;
import com.cloudcreativity.storage.utils.DefaultObserver;
import com.cloudcreativity.storage.utils.HttpUtils;
import com.cloudcreativity.storage.utils.JudgeGoodsUtils;
import com.cloudcreativity.storage.utils.QrCodeDialogUtils;
import com.cloudcreativity.storage.utils.SPUtils;
import com.cloudcreativity.storage.utils.ToastUtils;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GoodsModel extends BaseModel<BaseActivity, ActivityGoodsBinding> {

    public BaseBindingRecyclerViewAdapter<StoreGoods.Entity, ItemLayoutGoodsBinding> adapter;
    private CategoryWindowUtils popupWindow;
    public ObservableField<String> selectCategory;

    private int pageNum = 1;
    private int size = 10;
    public ObservableField<String> key = new ObservableField<>();
    private Integer oneId = 0;
    private Integer twoId = 0;

    GoodsModel(BaseActivity context, ActivityGoodsBinding binding, BaseDialogImpl baseDialog) {
        super(context, binding, baseDialog);
    }

    @Override
    protected void initView() {
        selectCategory = new ObservableField<>();
        selectCategory.set("类别筛选");
        binding.tlbGoods.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.finish();
            }
        });

        binding.rcvGoods.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        binding.refreshGoods.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                pageNum = 1;
                loadData(pageNum,size,oneId,twoId,key.get());
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                loadData(pageNum,size,oneId,twoId,key.get());
            }
        });
    }

    @Override
    protected void initData() {
        adapter = new BaseBindingRecyclerViewAdapter<StoreGoods.Entity, ItemLayoutGoodsBinding>(context) {
            @Override
            protected int getLayoutResId(int viewType) {
                return R.layout.item_layout_goods;
            }

            @Override
            protected void onBindItem(ItemLayoutGoodsBinding binding, final StoreGoods.Entity item, int position) {
                binding.setItem(item);

                binding.tvEnterOut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(TextUtils.isEmpty(item.getBarCode())){
                            ToastUtils.showShortToast(context,"暂无出入库信息");
                        }else{
                            CommonWebActivity.startActivity(context,item.getBarCode());
                        }
                    }
                });

                binding.tvQrCode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(TextUtils.isEmpty(item.getBarCode())){
                            ToastUtils.showShortToast(context,"暂无二维码信息");
                            return;
                        }
                        new QrCodeDialogUtils(context,
                                R.style.myProgressDialogStyle,
                                item).show();
                    }
                });

                binding.tvCheck.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        JudgeGoodsUtils judgeGoodsUtils = new JudgeGoodsUtils(context, R.style.myProgressDialogStyle);
                        judgeGoodsUtils.setOnOkListener(new JudgeGoodsUtils.OnOkListener() {
                            @Override
                            public void onOk(int state, float number, String remarks) {
                                doJudge(state,number,remarks,item);
                            }
                        });

                        judgeGoodsUtils.show();
                    }
                });
            }

            @Override
            public int getItemViewType(int position) {
                return super.getItemViewType(position);
            }
        };

        binding.refreshGoods.startRefresh();
    }

    //开始盘点
    private void doJudge(int state, float number, String remarks, StoreGoods.Entity item) {
        int accountId = SPUtils.get().getUser().getAccountId();
        HttpUtils.getInstance().doJudge(accountId,item.getPrice(),item.getId(),item.getPosition(),remarks,state,item.getUnitId(),
                item.getProviderId(),item.getSpecsId(),number,item.getGoodsId(),item.getStoreId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BaseResult>(context,true) {
                    @Override
                    public void onSuccess(BaseResult baseResult) {
                        ToastUtils.showShortToast(context,"盘点成功");
                    }
                });
    }

    public void onCategoryClick(){
        if(popupWindow!=null&&popupWindow.isShowing()){
            popupWindow.dismiss();
            return;
        }
        if(popupWindow==null){
            popupWindow = new CategoryWindowUtils(context,getBaseDialog());
            popupWindow.setOnChooseListener(new CategoryWindowUtils.OnChooseListener() {
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
                    binding.refreshGoods.startRefresh();
                }

                @Override
                public void onClear() {
                    selectCategory.set("类别筛选");
                    oneId = 0;
                    twoId = 0;
                    binding.refreshGoods.startRefresh();
                }
            });
        }

        popupWindow.show();
    }

    public void onSearch(){
        binding.refreshGoods.startRefresh();
    }

    private void loadData(final int page, int size, int oneId, int twoId, String key){
        HttpUtils.getInstance().getStoreGoods(page,size, SPUtils.get().getUser().getStoreId(),key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<StoreGoods>(getBaseDialog(),false) {
                    @Override
                    public void onSuccess(StoreGoods storeGoods) {
                        if(page==1){
                            binding.refreshGoods.finishRefreshing();
                            adapter.getItems().clear();
                        }else{
                            binding.refreshGoods.finishLoadmore();
                        }

                        if(storeGoods.getInfo().getList().isEmpty()){
                            if(storeGoods.getInfo().isLastPage()){
                                binding.refreshGoods.setEnableLoadmore(false);
                            }else{
                                binding.refreshGoods.setEnableLoadmore(true);
                            }
                        }else{
                            if(storeGoods.getInfo().isLastPage()){
                                binding.refreshGoods.setEnableLoadmore(false);
                            }else{
                                binding.refreshGoods.setEnableLoadmore(true);
                            }
                            adapter.getItems().addAll(storeGoods.getInfo().getList());
                            pageNum ++;
                        }
                    }

                    @Override
                    public void onFail(ExceptionReason msg) {
                        super.onFail(msg);
                        if(page==1){
                            binding.refreshGoods.finishRefreshing();
                        }else{
                            binding.refreshGoods.finishLoadmore();
                        }
                    }
                });
    }
}

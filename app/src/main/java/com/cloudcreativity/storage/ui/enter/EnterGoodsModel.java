package com.cloudcreativity.storage.ui.enter;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.base.BaseActivity;
import com.cloudcreativity.storage.base.BaseBindingRecyclerViewAdapter;
import com.cloudcreativity.storage.base.BaseDialogImpl;
import com.cloudcreativity.storage.base.BaseModel;
import com.cloudcreativity.storage.base.BaseResult;
import com.cloudcreativity.storage.databinding.ActivityEnterGoodsBinding;
import com.cloudcreativity.storage.databinding.ItemLayoutWaitEnterGoodsBinding;
import com.cloudcreativity.storage.entity.BuyOrder;
import com.cloudcreativity.storage.entity.EnterGoods;
import com.cloudcreativity.storage.utils.DefaultObserver;
import com.cloudcreativity.storage.utils.EnterGoodsUtils;
import com.cloudcreativity.storage.utils.HttpUtils;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * All Rights Reserved By CloudCreativity Tech.
 *
 * @author : created by Xu Xiwu
 * date-time: 2019/8/5 10:56
 * e-mail: xxw0701@sina.com
 */
public class EnterGoodsModel extends BaseModel<BaseActivity, ActivityEnterGoodsBinding> {

    private BuyOrder.Entity entity;

    public BaseBindingRecyclerViewAdapter<EnterGoods.Entity, ItemLayoutWaitEnterGoodsBinding> adapter;

    EnterGoodsModel(BaseActivity context, ActivityEnterGoodsBinding binding, BaseDialogImpl baseDialog, BuyOrder.Entity entity) {
        super(context, binding, baseDialog);
        this.entity = entity;
        binding.refreshEnterGoods.startRefresh();
    }

    @Override
    protected void initView() {
        binding.rcvEnterGoods.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        binding.tlbEnterGoods.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.finish();
            }
        });

        binding.refreshEnterGoods.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                loadData();
            }
        });
    }

    @Override
    protected void initData() {
        adapter = new BaseBindingRecyclerViewAdapter<EnterGoods.Entity, ItemLayoutWaitEnterGoodsBinding>(context) {
            @Override
            protected int getLayoutResId(int viewType) {
                return R.layout.item_layout_wait_enter_goods;
            }

            @Override
            protected void onBindItem(ItemLayoutWaitEnterGoodsBinding binding, final EnterGoods.Entity item, final int position) {

                binding.setItem(item);

                binding.tvEnter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        enterGoods(item,position);
                    }
                });
            }
        };
    }

    /**
    * @author : xuxiwu
    * created: 2019/8/2 16:22
    * desc: 添加入库
    */
    private void enterGoods(final EnterGoods.Entity item,final int itemPos) {
        EnterGoodsUtils utils = new EnterGoodsUtils(context,R.style.myProgressDialogStyle,item);
        utils.setOnOkListener(new EnterGoodsUtils.OnOkListener() {
            @Override
            public void onOk(int number, String address, String position) {
                submit(item,itemPos,address,number,position);
            }
        });

        utils.show();
    }

    private void submit(final EnterGoods.Entity item, final int itemPos, final String address, int number, String position){
        HttpUtils.getInstance().enterStore(
                entity.getId(),
                item.getId(),
                item.getGoodsId(),
                item.getProviderId(),
                12,
                item.getSpecsId(),
                item.getUnitId(),
                item.getPrice(),
                number,
                position,
                address,
                entity.getWayState())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BaseResult>(getBaseDialog(),true) {
                    @Override
                    public void onSuccess(BaseResult baseResult) {
                        item.setEnterState(1);
                        adapter.notifyItemChanged(itemPos);
                        for(EnterGoods.Entity entity:adapter.getItems()){
                            if(entity.getEnterState()==0){
                                return;
                            }
                        }
                        EventBus.getDefault().post("refresh_enter_goods_list");
                    }
                });
    }

    private void loadData(){
        //餐饮商品
        if(entity.getWayState()==2){
            HttpUtils.getInstance().getRestaurantGoods(entity.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DefaultObserver<EnterGoods>(getBaseDialog(),false) {
                        @Override
                        public void onSuccess(EnterGoods enterGoods) {
                            binding.refreshEnterGoods.finishRefreshing();
                            adapter.getItems().clear();
                            adapter.getItems().addAll(enterGoods.getInfo());
                        }

                        @Override
                        public void onFail(ExceptionReason msg) {
                            super.onFail(msg);
                            binding.refreshEnterGoods.finishRefreshing();
                        }
                    });
        }else{
            HttpUtils.getInstance().getWaitEnterGoods(entity.getId(),2)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DefaultObserver<EnterGoods>(getBaseDialog(),false) {
                        @Override
                        public void onSuccess(EnterGoods enterGoods) {
                            binding.refreshEnterGoods.finishRefreshing();
                            adapter.getItems().clear();
                            adapter.getItems().addAll(enterGoods.getInfo());
                        }

                        @Override
                        public void onFail(ExceptionReason msg) {
                            binding.refreshEnterGoods.finishRefreshing();
                        }
                    });
        }

    }
}

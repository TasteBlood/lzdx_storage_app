package com.cloudcreativity.storage.ui.out;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.base.BaseActivity;
import com.cloudcreativity.storage.base.BaseBindingRecyclerViewAdapter;
import com.cloudcreativity.storage.base.BaseDialogImpl;
import com.cloudcreativity.storage.base.BaseModel;
import com.cloudcreativity.storage.base.BaseResult;
import com.cloudcreativity.storage.databinding.ActivityOutGoodsBinding;
import com.cloudcreativity.storage.databinding.ItemLayoutWaitOutGoodsBinding;
import com.cloudcreativity.storage.entity.OutGoods;
import com.cloudcreativity.storage.entity.OutOrder;
import com.cloudcreativity.storage.utils.DefaultObserver;
import com.cloudcreativity.storage.utils.HttpUtils;
import com.cloudcreativity.storage.utils.OutGoodsUtils;
import com.cloudcreativity.storage.utils.SPUtils;
import com.cloudcreativity.storage.utils.ToastUtils;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * All Rights Reserved By CloudCreativity Tech.
 *
 * @author : created by Xu Xiwu
 * date-time: 2019/8/7 18:19
 * e-mail: xxw0701@sina.com
 */
public class OutGoodsModel extends BaseModel<BaseActivity, ActivityOutGoodsBinding> {

    private OutOrder.Entity entity;
    public BaseBindingRecyclerViewAdapter<OutOrder.OutGoods, ItemLayoutWaitOutGoodsBinding> adapter;

    OutGoodsModel(BaseActivity context, ActivityOutGoodsBinding binding, BaseDialogImpl baseDialog,OutOrder.Entity entity) {
        super(context, binding, baseDialog);
        this.entity = entity;
        binding.refreshOutGoods.startRefresh();
    }

    @Override
    protected void initView() {
        binding.tlbOutGoods.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.finish();
            }
        });

        binding.rcvOutGoods.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        binding.refreshOutGoods.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                loadData();
            }
        });
    }

    private void loadData() {
        HttpUtils.getInstance().getOutListGoods(entity.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<OutGoods>(getBaseDialog(),false) {
                    @Override
                    public void onSuccess(OutGoods outGoods) {
                        adapter.getItems().clear();
                        adapter.getItems().addAll(outGoods.getInfo());
                        binding.refreshOutGoods.finishRefreshing();
                    }
                });
    }

    @Override
    protected void initData() {
        adapter = new BaseBindingRecyclerViewAdapter<OutOrder.OutGoods, ItemLayoutWaitOutGoodsBinding>(context) {
            @Override
            protected int getLayoutResId(int viewType) {
                return R.layout.item_layout_wait_out_goods;
            }

            @Override
            protected void onBindItem(ItemLayoutWaitOutGoodsBinding binding, final OutOrder.OutGoods item, final int position) {
                binding.setItem(item);
                binding.tvOut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(entity.getStoreAccountDomains()==null||entity.getStoreAccountDomains().isEmpty()){
                            ToastUtils.showShortToast(context,"物品领取人为空，无法出库");
                            return;
                        }
                        OutGoodsUtils utils = new OutGoodsUtils(context,R.style.myProgressDialogStyle,item,entity.getStoreAccountDomains());
                        utils.setOnOkListener(new OutGoodsUtils.OnOkListener() {
                            @Override
                            public void onOk(String address, int personId) {
                                outGoods(item,position,address,personId);
                            }
                        });
                        utils.show();
                    }
                });
            }
        };
    }

    private void outGoods(final OutOrder.OutGoods item, final int position,String address,int personId) {
        HttpUtils.getInstance().outRecord(item.getNumber(),
                SPUtils.get().getUser().getPersonName(),
                address,
                item.getSpecsId(),
                item.getId(),
                item.getGoodsId(),
                entity.getStoreId(),
                personId,
                item.getArticleId(),
                entity.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BaseResult>(getBaseDialog(),true) {
                    @Override
                    public void onSuccess(BaseResult baseResult) {
                        item.setState(2);
                        adapter.notifyItemChanged(position);

                        for(OutOrder.OutGoods goods:adapter.getItems()){
                            if(goods.getState()==1)
                                return;
                        }
                        EventBus.getDefault().post("refresh_out_list");
                    }
                });
    }
}

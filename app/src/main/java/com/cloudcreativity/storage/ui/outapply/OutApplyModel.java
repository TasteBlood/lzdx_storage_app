package com.cloudcreativity.storage.ui.outapply;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.base.BaseBindingRecyclerViewAdapter;
import com.cloudcreativity.storage.base.BaseDialogImpl;
import com.cloudcreativity.storage.base.BaseModel;
import com.cloudcreativity.storage.base.BaseResult;
import com.cloudcreativity.storage.databinding.ActivityOutApplyBinding;
import com.cloudcreativity.storage.databinding.ItemLayoutOutApplyBinding;
import com.cloudcreativity.storage.databinding.ItemLayoutOutApplyOrderDetailBinding;
import com.cloudcreativity.storage.entity.OutOrder;
import com.cloudcreativity.storage.entity.UserEntity;
import com.cloudcreativity.storage.utils.ConfirmDialog;
import com.cloudcreativity.storage.utils.DefaultObserver;
import com.cloudcreativity.storage.utils.HttpUtils;
import com.cloudcreativity.storage.utils.SPUtils;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * All Rights Reserved By CloudCreativity Tech.
 *
 * @author : created by Xu Xiwu
 * date-time: 2019/9/25 10:41
 * e-mail: xxw0701@sina.com
 */
public class OutApplyModel extends BaseModel<OutApplyActivity, ActivityOutApplyBinding>{

    public BaseBindingRecyclerViewAdapter<OutOrder.Entity, ItemLayoutOutApplyBinding> adapter;

    OutApplyModel(OutApplyActivity context, ActivityOutApplyBinding binding, BaseDialogImpl baseDialog) {
        super(context, binding, baseDialog);
    }

    @Override
    protected void initView() {
        binding.rcvOutApply.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        binding.refreshOutApply.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                loadData();
            }
        });
    }

    @Override
    protected void initData() {

        adapter = new BaseBindingRecyclerViewAdapter<OutOrder.Entity, ItemLayoutOutApplyBinding>(context) {
            @Override
            protected int getLayoutResId(int viewType) {
                return R.layout.item_layout_out_apply;
            }

            @Override
            protected void onBindItem(ItemLayoutOutApplyBinding binding, final OutOrder.Entity item, int position) {
                binding.setItem(item);
                binding.rcvOutApplyItem.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
                BaseBindingRecyclerViewAdapter<OutOrder.OutGoods, ItemLayoutOutApplyOrderDetailBinding> itemAdapter =
                        new BaseBindingRecyclerViewAdapter<OutOrder.OutGoods, ItemLayoutOutApplyOrderDetailBinding>(context) {
                            @Override
                            protected int getLayoutResId(int viewType) {
                                return R.layout.item_layout_out_apply_order_detail;
                            }

                            @Override
                            protected void onBindItem(ItemLayoutOutApplyOrderDetailBinding binding, OutOrder.OutGoods item, int position) {
                                binding.setItem(item);
                            }
                        };
                binding.rcvOutApplyItem.setAdapter(itemAdapter);
                itemAdapter.getItems().addAll(item.getGoodsDomains());
                binding.tvCheck.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkOut(item);
                    }
                });
            }
        };

        binding.refreshOutApply.startRefresh();
    }

    //出库审批
    private void checkOut(final OutOrder.Entity item) {
        ConfirmDialog dialog = new ConfirmDialog(context,R.style.myProgressDialogStyle);
        dialog.setOnConfirmListener(new ConfirmDialog.OnConfirmListener() {
            @Override
            public void onConfirm(String msg) {
                HttpUtils.getInstance().checkOut(item.getId(),2,msg,SPUtils.get().getUser().getAccountId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new DefaultObserver<BaseResult>(getBaseDialog(),true) {
                            @Override
                            public void onSuccess(BaseResult t) {
                                binding.refreshOutApply.startRefresh();
                            }
                        });
            }
        });

        dialog.show();
    }

    private void loadData(){
        UserEntity.Entity user = SPUtils.get().getUser();
        HttpUtils.getInstance().getOutList(1,user.getAccountId(),user.getStoreId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<OutOrder>(context,false) {
                    @Override
                    public void onSuccess(OutOrder t) {
                        adapter.getItems().clear();
                        binding.refreshOutApply.finishRefreshing();
                        if(t.getInfo()==null||t.getInfo().isEmpty()){
                            binding.noData.setVisibility(View.VISIBLE);
                        }else{
                            binding.noData.setVisibility(View.GONE);
                            adapter.getItems().addAll(t.getInfo());
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        binding.refreshOutApply.finishRefreshing();
                    }
                });
    }
}

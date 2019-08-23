package com.cloudcreativity.storage.ui.enter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.base.BaseBindingRecyclerViewAdapter;
import com.cloudcreativity.storage.base.BaseDialogImpl;
import com.cloudcreativity.storage.base.BaseModel;
import com.cloudcreativity.storage.databinding.FragmentRequestListBinding;
import com.cloudcreativity.storage.databinding.ItemLayoutBuyOrderEnterBinding;
import com.cloudcreativity.storage.entity.BuyOrder;
import com.cloudcreativity.storage.entity.UserEntity;
import com.cloudcreativity.storage.utils.DefaultObserver;
import com.cloudcreativity.storage.utils.HttpUtils;
import com.cloudcreativity.storage.utils.SPUtils;
import com.cloudcreativity.storage.utils.ToastUtils;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RequestListModel extends BaseModel<Activity, FragmentRequestListBinding> {


    private int pageNum = 1;

    private int size = 20;

    public BaseBindingRecyclerViewAdapter<BuyOrder.Entity, ItemLayoutBuyOrderEnterBinding> adapter;

    RequestListModel(Activity context, FragmentRequestListBinding binding, BaseDialogImpl baseDialog) {
        super(context, binding, baseDialog);
    }

    @Override
    protected void initView() {
        binding.refreshRequestList.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                pageNum = 1;
                loadData(pageNum, size);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {

            }
        });

        binding.rcvRequestList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    protected void initData() {
        adapter = new BaseBindingRecyclerViewAdapter<BuyOrder.Entity, ItemLayoutBuyOrderEnterBinding>(context) {
            @Override
            protected int getLayoutResId(int viewType) {
                return R.layout.item_layout_buy_order_enter;
            }

            @Override
            protected void onBindItem(ItemLayoutBuyOrderEnterBinding binding, final BuyOrder.Entity item, int position) {
                binding.setItem(item);
                binding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, EnterGoodsListActivity.class);
                        intent.putExtra("order", item);
                        context.startActivity(intent);
                    }
                });
            }
        };

        binding.refreshRequestList.startRefresh();

    }

    private void loadData(int page, int size) {
        UserEntity.Entity user = SPUtils.get().getUser();
        int institutionId = 0;
        if (user != null)
            institutionId = user.getInstitutionId();
        HttpUtils.getInstance().getBoughtOrders(page, size, 3, 1, 2, institutionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BuyOrder>(getBaseDialog()) {
                    @Override
                    public void onSuccess(BuyOrder buyOrder) {
                        binding.refreshRequestList.finishRefreshing();
                        adapter.getItems().clear();
                        if (buyOrder.getInfo().getList().isEmpty()) {
                            binding.noData.setVisibility(View.VISIBLE);
                        } else {
                            binding.noData.setVisibility(View.GONE);
                            adapter.getItems().addAll(buyOrder.getInfo().getList());
                        }

                    }

                    @Override
                    public void onFail(ExceptionReason msg) {
                        binding.refreshRequestList.finishRefreshing();
                    }
                });
    }
}

package com.cloudcreativity.storage.ui.buyPrice;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.base.BaseActivity;
import com.cloudcreativity.storage.base.BaseBindingRecyclerViewAdapter;
import com.cloudcreativity.storage.base.BaseDialogImpl;
import com.cloudcreativity.storage.base.BaseModel;
import com.cloudcreativity.storage.databinding.ActivityBuyPriceBinding;
import com.cloudcreativity.storage.databinding.ItemLayoutBuyOrderBinding;
import com.cloudcreativity.storage.entity.BuyOrder;
import com.cloudcreativity.storage.utils.DefaultObserver;
import com.cloudcreativity.storage.utils.HttpUtils;
import com.cloudcreativity.storage.utils.OrderDetailDialogUtils;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BuyPriceModel extends BaseModel<BaseActivity, ActivityBuyPriceBinding> {

    public BaseBindingRecyclerViewAdapter<BuyOrder.Entity, ItemLayoutBuyOrderBinding> adapter;


    private int pageNum = 1;
    private int size = 20;

    BuyPriceModel(BaseActivity context, ActivityBuyPriceBinding binding, BaseDialogImpl baseDialog) {
        super(context, binding, baseDialog);
    }

    @Override
    protected void initView() {
        binding.tlbPrice.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.finish();
            }
        });

        binding.rcvPrice.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        binding.refreshPrice.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                pageNum = 1;
                loadData(pageNum, size);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                loadData(pageNum, size);
            }
        });
    }

    @Override
    protected void initData() {

        adapter = new BaseBindingRecyclerViewAdapter<BuyOrder.Entity, ItemLayoutBuyOrderBinding>(context) {
            @Override
            protected int getLayoutResId(int viewType) {
                return R.layout.item_layout_buy_order;
            }

            @Override
            protected void onBindItem(ItemLayoutBuyOrderBinding binding, final BuyOrder.Entity item, int position) {
                binding.setItem(item);

                binding.tvDetail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        searchDetail(item);
                    }
                });

                binding.tvPrice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startPrice(item);
                    }
                });
            }
        };

        binding.refreshPrice.startRefresh();
    }

    private void searchDetail(BuyOrder.Entity item) {
        new OrderDetailDialogUtils(context, R.style.myProgressDialogStyle, getBaseDialog(), item.getId())
                .show();
    }

    private void startPrice(BuyOrder.Entity item) {
        Intent intent = new Intent(context, PriceActivity.class);
        intent.putExtra("order", item);
        context.startActivity(intent);
    }

    private void loadData(final int page, int size) {
        HttpUtils.getInstance().getBuyOrders(page, size,1,3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BuyOrder>(getBaseDialog()) {
                    @Override
                    public void onSuccess(BuyOrder buyOrder) {
                        binding.refreshPrice.finishRefreshing();
                        adapter.getItems().clear();
                        List<BuyOrder.Entity> list = buyOrder.getInfo().getList();
                        if (list.isEmpty()) {
                            binding.noData.setVisibility(View.VISIBLE);
                        } else {
                            adapter.getItems().addAll(list);
                        }
                    }

                    @Override
                    public void onFail(ExceptionReason msg) {
                        binding.refreshPrice.finishRefreshing();
                    }
                });
    }
}

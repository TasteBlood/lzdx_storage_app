package com.cloudcreativity.storage.utils;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.base.BaseBindingRecyclerViewAdapter;
import com.cloudcreativity.storage.base.BaseDialogImpl;
import com.cloudcreativity.storage.databinding.ItemLayoutDialogOrderDetailBinding;
import com.cloudcreativity.storage.databinding.LayoutDialogOrderDetailBinding;
import com.cloudcreativity.storage.entity.BuyGoods;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class OrderDetailDialogUtils extends Dialog {

    public BaseBindingRecyclerViewAdapter<BuyGoods.Entity, ItemLayoutDialogOrderDetailBinding> adapter;
    private BaseDialogImpl baseDialog;
    private LayoutDialogOrderDetailBinding binding;
    private int oid;
    public OrderDetailDialogUtils(@NonNull Context context, int themeResId,BaseDialogImpl baseDialog,int oid) {
        super(context, themeResId);
        this.baseDialog = baseDialog;
        this.oid = oid;
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_dialog_order_detail,null,false);
        setContentView(binding.getRoot());
        binding.setUtils(this);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        Objects.requireNonNull(getWindow()).getAttributes().height = metrics.heightPixels/3*2;
        Objects.requireNonNull(getWindow()).getAttributes().width = metrics.widthPixels-40;
        setCanceledOnTouchOutside(false);
        binding.rcvOrderDetail.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        adapter = new BaseBindingRecyclerViewAdapter<BuyGoods.Entity, ItemLayoutDialogOrderDetailBinding>(context) {
            @Override
            protected int getLayoutResId(int viewType) {
                return R.layout.item_layout_dialog_order_detail;
            }

            @Override
            protected void onBindItem(ItemLayoutDialogOrderDetailBinding binding, BuyGoods.Entity item, int position) {
                binding.setItem(item);
            }
        };

        binding.refreshOrderDetail.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                loadData(OrderDetailDialogUtils.this.oid);
            }
        });

        binding.refreshOrderDetail.startRefresh();


    }

    private void loadData(int oid){
        HttpUtils.getInstance().getBuyGoods(oid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BuyGoods>(baseDialog,false) {
                    @Override
                    public void onSuccess(BuyGoods buyGoods) {
                        binding.refreshOrderDetail.finishRefreshing();
                        adapter.getItems().clear();
                        adapter.getItems().addAll(buyGoods.getInfo().getList());
                    }

                    @Override
                    public void onFail(ExceptionReason msg) {

                    }
                });
    }

    public void onClose(){
        dismiss();
    }
}

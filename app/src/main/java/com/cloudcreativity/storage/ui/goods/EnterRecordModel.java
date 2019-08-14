package com.cloudcreativity.storage.ui.goods;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.base.BaseActivity;
import com.cloudcreativity.storage.base.BaseBindingRecyclerViewAdapter;
import com.cloudcreativity.storage.base.BaseDialogImpl;
import com.cloudcreativity.storage.base.BaseModel;
import com.cloudcreativity.storage.databinding.ActivityEnterRecordBinding;
import com.cloudcreativity.storage.databinding.ItemLayoutEnterRecordBinding;
import com.cloudcreativity.storage.entity.EnterRecord;
import com.cloudcreativity.storage.entity.StoreGoods;
import com.cloudcreativity.storage.utils.DefaultObserver;
import com.cloudcreativity.storage.utils.HttpUtils;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class EnterRecordModel extends BaseModel<BaseActivity, ActivityEnterRecordBinding> {

    public BaseBindingRecyclerViewAdapter<EnterRecord.Entity, ItemLayoutEnterRecordBinding> adapter;

    public StoreGoods.Entity entity;

    private int pageNum = 1;
    private int size = 10;

    EnterRecordModel(BaseActivity context, ActivityEnterRecordBinding binding, BaseDialogImpl baseDialog, StoreGoods.Entity entity) {
        super(context, binding, baseDialog);
        this.entity = entity;
    }

    @Override

    protected void initView() {
        binding.tlbEnterRecord.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.finish();
            }
        });

        binding.rcvEnterRecord.setLayoutManager(new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL,false));

        binding.refreshEnterRecord.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                pageNum = 1;
                loadData(pageNum,size,entity.getId());
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                loadData(pageNum,size,entity.getId());
            }
        });
    }

    @Override
    protected void initData() {
        adapter = new BaseBindingRecyclerViewAdapter<EnterRecord.Entity, ItemLayoutEnterRecordBinding>(context) {
            @Override
            protected int getLayoutResId(int viewType) {
                return R.layout.item_layout_enter_record;
            }

            @Override
            protected void onBindItem(ItemLayoutEnterRecordBinding binding, EnterRecord.Entity item, int position) {
                binding.setItem(item);
            }
        };

        binding.refreshEnterRecord.startRefresh();
    }

    private void loadData(final int page, int size, int goodsId){
        HttpUtils.getInstance().getEnterRecords(goodsId,page,size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<EnterRecord>(getBaseDialog(),false) {
                    @Override
                    public void onSuccess(EnterRecord enterRecord) {

                        if (page==1){
                            binding.refreshEnterRecord.finishRefreshing();
                        }else{
                            binding.refreshEnterRecord.finishLoadmore();
                        }

                        if(enterRecord.getInfo().getList().isEmpty()){
                            if(page==1){
                                adapter.getItems().clear();
                                binding.noData.setVisibility(View.VISIBLE);
                            }else{
                                binding.noData.setVisibility(View.GONE);
                            }
                        }else{

                            if(page==1){
                                adapter.getItems().clear();
                            }

                            if(enterRecord.getInfo().isLastPage()){
                                binding.refreshEnterRecord.setEnableLoadmore(false);
                            }else{
                                binding.refreshEnterRecord.setEnableLoadmore(true);
                                pageNum ++;
                            }

                            adapter.getItems().addAll(enterRecord.getInfo().getList());

                        }

                    }

                    @Override
                    public void onFail(ExceptionReason msg) {
                        if (page==1){
                            binding.refreshEnterRecord.finishRefreshing();
                        }else{
                            binding.refreshEnterRecord.finishLoadmore();
                        }
                    }
                });
    }
}

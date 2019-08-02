package com.cloudcreativity.storage.ui.buyPrice;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.base.BaseActivity;
import com.cloudcreativity.storage.base.BaseBindingRecyclerViewAdapter;
import com.cloudcreativity.storage.base.BaseDialogImpl;
import com.cloudcreativity.storage.base.BaseModel;
import com.cloudcreativity.storage.databinding.ActivityProviderBinding;
import com.cloudcreativity.storage.databinding.ItemLayoutProviderBinding;
import com.cloudcreativity.storage.entity.Provider;
import com.cloudcreativity.storage.utils.DefaultObserver;
import com.cloudcreativity.storage.utils.HttpUtils;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProviderModel extends BaseModel<BaseActivity, ActivityProviderBinding> {

    private int pageNum = 1;
    private int size = 10;
    public BaseBindingRecyclerViewAdapter<Provider.Entity, ItemLayoutProviderBinding> adapter;
    ProviderModel(BaseActivity context, ActivityProviderBinding binding, BaseDialogImpl baseDialog) {
        super(context, binding, baseDialog);
    }

    @Override
    protected void initView() {
        binding.tlbProvider.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.finish();
            }
        });
        binding.rcvProvider.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        binding.refreshProvider.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                pageNum = 1;
                loadData(pageNum,size);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                loadData(pageNum,size);
            }
        });
    }

    @Override
    protected void initData() {
        adapter = new BaseBindingRecyclerViewAdapter<Provider.Entity, ItemLayoutProviderBinding>(context) {
            @Override
            protected int getLayoutResId(int viewType) {
                return R.layout.item_layout_provider;
            }

            @Override
            protected void onBindItem(ItemLayoutProviderBinding binding, final Provider.Entity item, int position) {
                binding.setItem(item);

                binding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.putExtra("provider",item);
                        ProviderModel.this.context.setResult(Activity.RESULT_OK,intent);
                        ProviderModel.this.context.finish();
                    }
                });
            }
        };
        binding.refreshProvider.startRefresh();
    }

    public void onAddClick(){
        context.startActivity(new Intent(context,AddProviderActivity.class));
    }

    private void loadData(final int page,int size){
        HttpUtils.getInstance().getProviders(page,size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<Provider>(getBaseDialog()) {
                    @Override
                    public void onSuccess(Provider provider) {
                        if (page == 1) {
                            binding.refreshProvider.finishRefreshing();
                            binding.refreshProvider.setEnableLoadmore(true);
                            adapter.getItems().clear();
                            if(provider.getInfo().getList().size()<=0){
                                binding.noData.setVisibility(View.VISIBLE);
                            }else{
                                binding.noData.setVisibility(View.GONE);
                            }
                        } else {
                            binding.refreshProvider.finishLoadmore();
                        }
                        adapter.getItems().addAll(provider.getInfo().getList());
                        if(provider.getInfo().isLastPage()){
                            binding.refreshProvider.setEnableLoadmore(false);
                        }else{
                            binding.refreshProvider.setEnableLoadmore(true);
                            pageNum ++;
                        }
                    }

                    @Override
                    public void onFail(ExceptionReason msg) {
                        if (page == 1) {
                            binding.refreshProvider.finishRefreshing();
                        } else {
                            binding.refreshProvider.finishLoadmore();
                        }
                    }
                });
    }
}

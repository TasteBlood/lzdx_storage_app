package com.cloudcreativity.storage.ui.out;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.base.BaseBindingRecyclerViewAdapter;
import com.cloudcreativity.storage.base.BaseDialogImpl;
import com.cloudcreativity.storage.base.BaseModel;
import com.cloudcreativity.storage.databinding.FragmentOutListBinding;
import com.cloudcreativity.storage.databinding.ItemLayoutOutOrderBinding;
import com.cloudcreativity.storage.entity.OutOrder;
import com.cloudcreativity.storage.entity.UserEntity;
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
 * date-time: 2019/8/7 15:56
 * e-mail: xxw0701@sina.com
 */
public class ListOutModel extends BaseModel<Activity, FragmentOutListBinding>{

    public BaseBindingRecyclerViewAdapter<OutOrder.Entity, ItemLayoutOutOrderBinding> adapter;

    ListOutModel(Activity context, FragmentOutListBinding binding, BaseDialogImpl baseDialog) {
        super(context, binding, baseDialog);
    }

    @Override
    protected void initView() {
        binding.refreshOutList.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                loadData();
            }
        });

        binding.rcvOutList.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
    }

    @Override
    protected void initData() {
        adapter = new BaseBindingRecyclerViewAdapter<OutOrder.Entity, ItemLayoutOutOrderBinding>(context) {
            @Override
            protected int getLayoutResId(int viewType) {
                return R.layout.item_layout_out_order;
            }

            @Override
            protected void onBindItem(ItemLayoutOutOrderBinding binding, final OutOrder.Entity item, int position) {
                binding.setItem(item);
                binding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context,OutGoodsActivity.class);
                        intent.putExtra("order",item);
                        context.startActivity(intent);
                    }
                });
            }
        };

        binding.refreshOutList.startRefresh();
    }

    private void loadData(){
        UserEntity.Entity user = SPUtils.get().getUser();
        HttpUtils.getInstance().getOutList(2,0,0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<OutOrder>(getBaseDialog(),false) {
                    @Override
                    public void onSuccess(OutOrder outOrder) {
                        binding.refreshOutList.finishRefreshing();
                        if(outOrder.getInfo().isEmpty()){
                            adapter.getItems().clear();
                            binding.noData.setVisibility(View.VISIBLE);
                        }else{
                            adapter.getItems().clear();
                            adapter.getItems().addAll(outOrder.getInfo());
                            binding.noData.setVisibility(View.GONE);
                        }
                    }
                });
    }
}

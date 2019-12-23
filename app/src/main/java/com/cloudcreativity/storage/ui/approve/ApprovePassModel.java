package com.cloudcreativity.storage.ui.approve;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.base.BaseBindingRecyclerViewAdapter;
import com.cloudcreativity.storage.base.BaseDialogImpl;
import com.cloudcreativity.storage.base.BaseModel;
import com.cloudcreativity.storage.databinding.FragmentApprovePassBinding;
import com.cloudcreativity.storage.databinding.ItemLayoutApproveBinding;
import com.cloudcreativity.storage.entity.Approve;
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
 * date-time: 2019/12/6 10:01
 * e-mail: xxw0701@sina.com
 */
public class ApprovePassModel extends BaseModel<Activity, FragmentApprovePassBinding> {

    public BaseBindingRecyclerViewAdapter<Approve.Entity, ItemLayoutApproveBinding> adapter;
    private UserEntity.Entity user;
    private ApprovePassFragment fragment;

    private int pageNum = 1;
    private int pageSize = 20;

    ApprovePassModel(final Activity context, final FragmentApprovePassBinding binding, BaseDialogImpl baseDialog,ApprovePassFragment fragment) {
        super(context, binding, baseDialog);
        this.fragment = fragment;
        binding.refreshApprovePass.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                pageNum = 1;
                loadData(pageNum,pageSize);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                loadData(pageNum,pageSize);
            }
        });

        adapter = new BaseBindingRecyclerViewAdapter<Approve.Entity, ItemLayoutApproveBinding>(context) {
            @Override
            protected int getLayoutResId(int viewType) {
                return R.layout.item_layout_approve;
            }

            @Override
            protected void onBindItem(ItemLayoutApproveBinding binding, final Approve.Entity item, int position) {
                binding.setItem(item);
                int state = 1;
                if(item.getAccount2()==user.getAccountId()){
                    //说明当前是中心负责人
                    state = item.getState2();
                }else if(item.getAccount3()==user.getAccountId()){
                    //主管部领导
                    state = item.getState3();
                }else if(item.getAccount4()==user.getAccountId()){
                    //财务审核人
                    state = item.getState4();
                }else if(item.getAccount5()==user.getAccountId()){
                    //部领导
                    state = item.getState5();
                }

                if(state==-1){
                    binding.tvState.setText("被驳回");
                }else if(state==0){
                    binding.tvState.setText("审批通过");
                }else if(state==1){
                    binding.tvState.setText("待审批");
                }

                binding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context,ApproveDetailActivity.class);
                        intent.putExtra("oid",item.getId());
                        context.startActivity(intent);
                    }
                });
            }
        };
        binding.rcvApprovePass.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));

        user = SPUtils.get().getUser();

        binding.refreshApprovePass.postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.refreshApprovePass.startRefresh();
            }
        },200);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        binding.refreshApprovePass.finishRefreshing();
    }

    private void loadData(final int page, int size){
        HttpUtils.getInstance().getApproveList(user.getAccountId(),0,page,size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<Approve>(getBaseDialog(),false) {
                    @Override
                    public void onSuccess(Approve t) {
                        if(t.getInfo()!=null && !t.getInfo().getRecords().isEmpty()){
                            fragment.initialLoadDataSuccess();
                            binding.refreshApprovePass.setEnableLoadmore(true);
                            if(page==1){
                                binding.refreshApprovePass.finishRefreshing();
                                binding.noData.setVisibility(View.GONE);
                                binding.refreshApprovePass.setVisibility(View.VISIBLE);
                                adapter.getItems().clear();
                                if(page<=t.getInfo().getTotalpage())
                                    pageNum ++;
                            }else{
                                binding.refreshApprovePass.finishLoadmore();
                                if(page<=t.getInfo().getTotalpage())
                                    pageNum ++;
                            }
                            adapter.getItems().addAll(t.getInfo().getRecords());

                        }else{
                            if(page==1){
                                binding.refreshApprovePass.finishRefreshing();
                                binding.noData.setVisibility(View.VISIBLE);
                                binding.rcvApprovePass.setVisibility(View.GONE);
                                adapter.getItems().clear();
                            }else{
                                binding.refreshApprovePass.finishLoadmore();
                                binding.refreshApprovePass.setEnableLoadmore(false);
                            }

                        }
                    }

                    @Override
                    public void onFail(ExceptionReason msg) {
                        super.onFail(msg);
                        if(page==1){
                            binding.refreshApprovePass.finishRefreshing();
                        }else{
                            binding.refreshApprovePass.finishLoadmore();
                        }
                    }
                });
    }
}

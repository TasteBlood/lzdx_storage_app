package com.cloudcreativity.storage.ui.enter;

import android.app.Activity;

import com.cloudcreativity.storage.base.BaseDialogImpl;
import com.cloudcreativity.storage.base.BaseModel;
import com.cloudcreativity.storage.databinding.FragmentRequestListBinding;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

public class RequestListModel extends BaseModel<Activity, FragmentRequestListBinding> {


    private int pageNum = 1;

    private int size =10;



    public RequestListModel(Activity context, FragmentRequestListBinding binding, BaseDialogImpl baseDialog) {
        super(context, binding, baseDialog);
    }

    @Override
    protected void initView() {
        binding.refreshRequestList.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {

            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {

            }
        });
    }

    @Override
    protected void initData() {

    }
}

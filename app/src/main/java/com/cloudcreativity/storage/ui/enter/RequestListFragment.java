package com.cloudcreativity.storage.ui.enter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.base.LazyFragment;
import com.cloudcreativity.storage.databinding.FragmentRequestListBinding;
import com.duke.dfileselector.activity.DefaultSelectorActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

public class RequestListFragment extends LazyFragment {

    private FragmentRequestListBinding binding;
    private RequestListModel model;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_request_list, container, false);
        model = new RequestListModel(context, binding, this);
        binding.setModel(model);
        return binding.getRoot();
    }

    @Override
    public void initialLoadData() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if(isRegister){
            context.unregisterReceiver(receiver);
            isRegister = false;
        }
    }

    @Subscribe
    public void onEvent(String msg){
        //没入库成功一个商品，就刷新一次列表，防止入库完了，没有刷新列表
        if("refresh_enter_goods_list".equals(msg)){
            binding.refreshRequestList.startRefresh();
        }
    }

    private boolean isRegister = false;
    private IntentFilter intentFilter = new IntentFilter(DefaultSelectorActivity.FILE_SELECT_ACTION);
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (context == null || intent == null) {
                return;
            }
            if (DefaultSelectorActivity.FILE_SELECT_ACTION.equals(intent.getAction())) {
                if(model!=null){
                    ArrayList<String> strings = DefaultSelectorActivity.getDataFromIntent(intent);
                    model.upload(strings.get(0));
                }
            }
        }
    };
    @Override
    public void onResume() {
        super.onResume();
        if(!isRegister){
            context.registerReceiver(receiver,intentFilter);
            isRegister = true;
        }
    }
}

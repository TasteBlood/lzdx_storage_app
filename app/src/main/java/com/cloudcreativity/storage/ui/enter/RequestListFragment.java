package com.cloudcreativity.storage.ui.enter;

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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class RequestListFragment extends LazyFragment {

    private FragmentRequestListBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_request_list, container, false);
        binding.setModel(new RequestListModel(context, binding,this));
        return binding.getRoot();
    }

    @Override
    public void initialLoadData() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(String msg){
        //没入库成功一个商品，就刷新一次列表，防止入库完了，没有刷新列表
        if("refresh_enter_goods_list".equals(msg)){
            binding.refreshRequestList.startRefresh();
        }
    }
}

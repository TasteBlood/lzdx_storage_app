package com.cloudcreativity.storage.ui.approve;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.base.LazyFragment;
import com.cloudcreativity.storage.databinding.FragmentApprovePassBinding;

/**
 * All Rights Reserved By CloudCreativity Tech.
 *
 * @author : created by Xu Xiwu
 * date-time: 2019/12/13 14:52
 * e-mail: xxw0701@sina.com
 */
public class ApprovePassFragment extends LazyFragment{

    private FragmentApprovePassBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_approve_pass,container,false);
        binding.setModel(new ApprovePassModel(context, binding,this,this));
        return binding.getRoot();
    }

    @Override
    public void initialLoadData() {
        if(binding==null) return;
        binding.refreshApprovePass.startRefresh();
    }
}

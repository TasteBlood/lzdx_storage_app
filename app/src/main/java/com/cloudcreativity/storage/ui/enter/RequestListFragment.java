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

public class RequestListFragment extends LazyFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentRequestListBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_request_list, container, false);
        return binding.getRoot();
    }

    @Override
    public void initialLoadData() {

    }
}

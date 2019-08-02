package com.cloudcreativity.storage.ui.enter;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.base.BaseActivity;
import com.cloudcreativity.storage.databinding.ActivityEnterStoreBinding;

public class EnterStoreActivity extends BaseActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityEnterStoreBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_enter_store);
        binding.setModel(new EnterStoreModel(this,binding,this));
    }
}

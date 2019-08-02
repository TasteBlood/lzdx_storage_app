package com.cloudcreativity.storage.ui.config;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.base.BaseActivity;
import com.cloudcreativity.storage.databinding.ActivityConfigIndexBinding;

public class ConfigIndexActivity extends BaseActivity {

    private ConfigModel model;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityConfigIndexBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_config_index);
        model = new ConfigModel(this, binding, null);
        binding.setModel(model);
    }

    @Override
    protected void onStart() {
        super.onStart();
        model.initData();
    }
}

package com.cloudcreativity.storage.ui.buyPrice;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.base.BaseActivity;
import com.cloudcreativity.storage.databinding.ActivityProviderAddBinding;

public class AddProviderActivity extends BaseActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityProviderAddBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_provider_add);
        binding.setModel(new AddProviderModel(this,binding,this));
    }
}

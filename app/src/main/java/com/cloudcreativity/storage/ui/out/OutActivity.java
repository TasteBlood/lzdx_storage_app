package com.cloudcreativity.storage.ui.out;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.base.BaseActivity;
import com.cloudcreativity.storage.databinding.ActivityOutBinding;

public class OutActivity extends BaseActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityOutBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_out);
        binding.setModel(new OutModel(this,binding,this));
    }
}

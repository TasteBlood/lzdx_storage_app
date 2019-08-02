package com.cloudcreativity.storage.ui.goods;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.base.BaseActivity;
import com.cloudcreativity.storage.databinding.ActivityOutRecordBinding;

public class OutRecordActivity extends BaseActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityOutRecordBinding binding = DataBindingUtil.setContentView(this,
                R.layout.activity_out_record);
        binding.setModel(new OutRecordModel(this,binding,this));
    }
}

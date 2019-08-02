package com.cloudcreativity.storage.ui.goods;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.base.BaseActivity;
import com.cloudcreativity.storage.databinding.ActivityGoodsBinding;

public class GoodsActivity extends BaseActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityGoodsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_goods);
        binding.setModel(new GoodsModel(this,binding,this));
    }
}

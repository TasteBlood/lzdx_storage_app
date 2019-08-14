package com.cloudcreativity.storage.ui.goods;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.base.BaseActivity;
import com.cloudcreativity.storage.databinding.ActivityEnterRecordBinding;
import com.cloudcreativity.storage.entity.StoreGoods;

public class EnterRecordActivity extends BaseActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityEnterRecordBinding binding = DataBindingUtil.setContentView(this,
                R.layout.activity_enter_record);
        StoreGoods.Entity entity = (StoreGoods.Entity) getIntent().getSerializableExtra("goods");
        binding.setModel(new EnterRecordModel(this,binding,this,entity));
    }
}

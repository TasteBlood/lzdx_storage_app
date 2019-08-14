package com.cloudcreativity.storage.ui.out;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.base.BaseActivity;
import com.cloudcreativity.storage.databinding.ActivityOutGoodsBinding;
import com.cloudcreativity.storage.entity.OutOrder;

/**
 * All Rights Reserved By CloudCreativity Tech.
 *
 * @author : created by Xu Xiwu
 * date-time: 2019/8/7 18:19
 * e-mail: xxw0701@sina.com
 */
public class OutGoodsActivity extends BaseActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityOutGoodsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_out_goods);
        OutOrder.Entity entity = (OutOrder.Entity) getIntent().getSerializableExtra("order");
        binding.setModel(new OutGoodsModel(this,binding,this,entity));
    }
}

package com.cloudcreativity.storage.ui.enter;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.base.BaseActivity;
import com.cloudcreativity.storage.databinding.ActivityEnterGoodsBinding;
import com.cloudcreativity.storage.entity.BuyOrder;

/**
 * All Rights Reserved By CloudCreativity Tech.
 *
 * @author : created by Xu Xiwu
 * date-time: 2019/8/5 10:54
 * e-mail: xxw0701@sina.com
 */
public class EnterGoodsListActivity extends BaseActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityEnterGoodsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_enter_goods);
        BuyOrder.Entity entity = (BuyOrder.Entity) getIntent().getSerializableExtra("order");
        binding.setModel(new EnterGoodsModel(this,binding,this,entity));
    }
}

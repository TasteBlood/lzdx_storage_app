package com.cloudcreativity.storage.ui.buyPrice;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.base.BaseActivity;
import com.cloudcreativity.storage.databinding.ActivityBuyPriceBinding;

public class BuyPriceActivity extends BaseActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityBuyPriceBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_buy_price);
        binding.setModel(new BuyPriceModel(this,binding,this));
    }
}

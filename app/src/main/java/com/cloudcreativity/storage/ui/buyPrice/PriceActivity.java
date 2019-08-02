package com.cloudcreativity.storage.ui.buyPrice;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.base.BaseActivity;
import com.cloudcreativity.storage.databinding.ActivityPriceBinding;
import com.cloudcreativity.storage.entity.BuyOrder;
import com.cloudcreativity.storage.entity.Provider;

public class PriceActivity extends BaseActivity {

    private PriceModel model;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityPriceBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_price);
        BuyOrder.Entity order = (BuyOrder.Entity) getIntent().getSerializableExtra("order");
        model = new PriceModel(this, binding, this, order);
        binding.setModel(model);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!=RESULT_OK)
            return;
        if(requestCode!=100)
            return;
        model.onResult((Provider.Entity) data.getSerializableExtra("provider"));
    }
}

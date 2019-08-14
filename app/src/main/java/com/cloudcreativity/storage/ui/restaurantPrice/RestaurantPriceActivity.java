package com.cloudcreativity.storage.ui.restaurantPrice;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.base.BaseActivity;
import com.cloudcreativity.storage.databinding.ActivityRestaurentPriceBinding;
import com.cloudcreativity.storage.entity.Provider;

public class RestaurantPriceActivity extends BaseActivity {

    private RestaurantPriceModel model;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityRestaurentPriceBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_restaurent_price);
        model = new RestaurantPriceModel(this, binding, this);
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

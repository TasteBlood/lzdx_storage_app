package com.cloudcreativity.storage.ui.main;

import android.app.Activity;
import android.content.Intent;

import com.cloudcreativity.storage.ui.buyPrice.BuyPriceActivity;
import com.cloudcreativity.storage.ui.config.ConfigIndexActivity;
import com.cloudcreativity.storage.databinding.ActivityMainBinding;
import com.cloudcreativity.storage.ui.enter.EnterStoreActivity;
import com.cloudcreativity.storage.ui.goods.GoodsActivity;
import com.cloudcreativity.storage.ui.out.OutActivity;
import com.cloudcreativity.storage.ui.restaurantPrice.RestaurantPriceActivity;
import com.cloudcreativity.storage.utils.ToastUtils;
import com.cloudcreativity.storage.zxing.android.CaptureActivity;

public class MainModel {
    private ActivityMainBinding binding;
    private Activity context;

    MainModel(ActivityMainBinding binding, Activity context) {
        this.binding = binding;
        this.context = context;
    }
    //scan click
    public void onScanClick(){
        context.startActivityForResult(new Intent(context, CaptureActivity.class),100);
    }

    //config click
    public void onConfigClick(){
        context.startActivity(new Intent(context, ConfigIndexActivity.class));
    }

    //goods click
    public void onGoodsClick(){
        context.startActivity(new Intent(context, GoodsActivity.class));
    }

    public void onEnterClick(){context.startActivity(new Intent(context, EnterStoreActivity.class));}

    public void onOutClick(){context.startActivity(new Intent(context, OutActivity.class));}

    public void onPriceClick(){
        context.startActivity(new Intent(context, BuyPriceActivity.class));
    }

    public void onRestaurantPriceClick(){
        context.startActivity(new Intent(context, RestaurantPriceActivity.class));
    }

    protected void onResult(String scanCode){
        ToastUtils.showShortToast(context,scanCode);
    }

}

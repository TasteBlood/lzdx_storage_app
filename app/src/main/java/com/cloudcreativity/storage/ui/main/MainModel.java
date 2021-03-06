package com.cloudcreativity.storage.ui.main;

import android.content.Intent;
import android.text.TextUtils;

import com.cloudcreativity.storage.base.BaseActivity;
import com.cloudcreativity.storage.base.BaseDialogImpl;
import com.cloudcreativity.storage.base.BaseModel;
import com.cloudcreativity.storage.base.CommonWebActivity;
import com.cloudcreativity.storage.databinding.ActivityMainBinding;
import com.cloudcreativity.storage.ui.approve.ApproveIndexActivity;
import com.cloudcreativity.storage.ui.buyPrice.BuyPriceActivity;
import com.cloudcreativity.storage.ui.config.ConfigIndexActivity;
import com.cloudcreativity.storage.ui.enter.EnterStoreActivity;
import com.cloudcreativity.storage.ui.goods.GoodsActivity;
import com.cloudcreativity.storage.ui.out.OutActivity;
import com.cloudcreativity.storage.ui.outapply.OutApplyActivity;
import com.cloudcreativity.storage.ui.restaurantPrice.RestaurantPriceActivity;
import com.cloudcreativity.storage.utils.AppConfig;
import com.cloudcreativity.storage.utils.SPUtils;
import com.cloudcreativity.storage.utils.ToastUtils;
import com.cloudcreativity.storage.zxing.android.CaptureActivity;

public class MainModel extends BaseModel<BaseActivity,ActivityMainBinding>{

    MainModel(BaseActivity context, ActivityMainBinding binding, BaseDialogImpl baseDialog) {
        super(context, binding, baseDialog);
        binding.tvName.setText(SPUtils.get().getUser().getPersonName());
    }

    public void onApplyOut(){
        int role = SPUtils.get().getRole();
        if(role!= AppConfig.USER_ROLE.OUT_ADMIN){
            ToastUtils.showShortToast(context,"无权访问");
            return;
        }
        context.startActivity(new Intent(context, OutApplyActivity.class));
    }

    //scan click
    public void onScanClick(){
        context.startActivityForResult(new Intent(context, CaptureActivity.class),100);
    }

    //baoxiao shenpi
    public void onBaoXiaoShenPiClick(){
        int role = SPUtils.get().getRole();
        if(role!=5 && role!=6 && role!=7 && role!=8){
            ToastUtils.showShortToast(context,"无权访问");
            return;
        }
        context.startActivity(new Intent(context, ApproveIndexActivity.class));
    }

    public void onSettingClick(){
        context.startActivity(new Intent(context,SettingActivity.class));
    }


    //config click
    public void onConfigClick(){
        context.startActivity(new Intent(context, ConfigIndexActivity.class));
    }

    //goods click
    public void onGoodsClick(){
        int role = SPUtils.get().getRole();
        if(role!= AppConfig.USER_ROLE.MANAGER){
            ToastUtils.showShortToast(context,"无权访问");
            return;
        }
        context.startActivity(new Intent(context, GoodsActivity.class));
    }

    public void onEnterClick(){
        int role = SPUtils.get().getRole();
        if(role!= AppConfig.USER_ROLE.MANAGER){
            ToastUtils.showShortToast(context,"无权访问");
            return;
        }
        context.startActivity(new Intent(context, EnterStoreActivity.class));
    }

    public void onOutClick(){
        int role = SPUtils.get().getRole();
        if(role!= AppConfig.USER_ROLE.MANAGER){
            ToastUtils.showShortToast(context,"无权访问");
            return;
        }
        context.startActivity(new Intent(context, OutActivity.class));
    }

    public void onPriceClick(){
        int role = SPUtils.get().getRole();
        if(role!= AppConfig.USER_ROLE.PRICE){
            ToastUtils.showShortToast(context,"无权访问");
            return;
        }
        context.startActivity(new Intent(context, BuyPriceActivity.class));
    }


    public void onRestaurantPriceClick(){
        int role = SPUtils.get().getRole();
        if(role!= AppConfig.USER_ROLE.RESTAURANT){
            ToastUtils.showShortToast(context,"无权访问");
            return;
        }
        context.startActivity(new Intent(context, RestaurantPriceActivity.class));
    }

    void onResult(String scanCode){
        //ToastUtils.showShortToast(context,scanCode);
        if(TextUtils.isEmpty(scanCode))
            return;
        if(scanCode.startsWith("http://")){
            CommonWebActivity.startActivity(context,scanCode);
        }
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

}

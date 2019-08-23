package com.cloudcreativity.storage.ui.config;

import android.Manifest;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.base.BaseActivity;
import com.cloudcreativity.storage.databinding.ActivityConfigIndexBinding;
import com.cloudcreativity.storage.utils.ToastUtils;

public class ConfigIndexActivity extends BaseActivity {

    private ConfigModel model;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
        } else {
            //init
            initView();
        }

    }

    private void initView(){
        ActivityConfigIndexBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_config_index);
        model = new ConfigModel(this, binding, null);
        binding.setModel(model);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(model!=null)
            model.initData();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==100){
            if(Manifest.permission.ACCESS_COARSE_LOCATION.equals(permissions[0])
                    && PackageManager.PERMISSION_GRANTED==grantResults[0]){
                initView();
            }else{
                ToastUtils.showShortToast(this,"请打开权限");
                finish();
            }
        }
    }
}

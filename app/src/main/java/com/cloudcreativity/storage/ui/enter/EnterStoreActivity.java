package com.cloudcreativity.storage.ui.enter;

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
import com.cloudcreativity.storage.databinding.ActivityEnterStoreBinding;
import com.cloudcreativity.storage.utils.BalanceUtils;
import com.cloudcreativity.storage.utils.ToastUtils;

import java.io.IOException;

public class EnterStoreActivity extends BaseActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                init();
            }else{
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},
                        100);
            }
        }else{
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},
                    100);
        }
    }

    private void init(){
        ActivityEnterStoreBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_enter_store);
        binding.setModel(new EnterStoreModel(this,binding,this));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(Manifest.permission.ACCESS_COARSE_LOCATION.equals(permissions[0])&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
            if(Manifest.permission.ACCESS_FINE_LOCATION.equals(permissions[1])&&grantResults[1]==PackageManager.PERMISSION_GRANTED){
                init();
            }else{
                finish();
                ToastUtils.showShortToast(this,"无法定位，请打开权限");
            }
        }else{
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            //将蓝牙电子秤关闭
            BalanceUtils.getInstance().release();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

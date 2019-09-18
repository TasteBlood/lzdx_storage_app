package com.cloudcreativity.storage.ui.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.ui.buyPrice.ProviderActivity;
import com.cloudcreativity.storage.ui.loginAndRegister.LoginActivity;
import com.cloudcreativity.storage.utils.SPUtils;
import com.cloudcreativity.storage.utils.ToastUtils;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class IndexActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            init();
        } else {
            ActivityCompat.requestPermissions(this, permissions, 100);
        }
    }

    private void init() {
        setContentView(R.layout.activity_index);

        View index = findViewById(R.id.iv_index);
        Animation animation = new AlphaAnimation(0.0f, 1f);
        animation.setDuration(2000);
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(SPUtils.get().getFirst()){
                    startActivity(new Intent().setClass(IndexActivity.this, GuideActivity.class));
                }else{
                    if (SPUtils.get().isLogin()) {
                        //跳转到主页
                        startActivity(new Intent().setClass(IndexActivity.this, MainActivity.class));
                    } else {
                        //跳转到登录
                        startActivity(new Intent().setClass(IndexActivity.this, LoginActivity.class));
                    }
                }
                onBackPressed();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        index.startAnimation(animation);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode!=100)
            return;
        if(Manifest.permission.CAMERA.equals(permissions[0])&&PackageManager.PERMISSION_GRANTED==grantResults[0]){
            init();
        }else{
            ToastUtils.showShortToast(this,"请打开权限");
            onBackPressed();
        }
    }
}

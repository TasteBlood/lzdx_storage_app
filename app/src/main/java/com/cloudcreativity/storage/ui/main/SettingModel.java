package com.cloudcreativity.storage.ui.main;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.databinding.ObservableField;

import com.cloudcreativity.storage.base.CommonWebActivity;
import com.cloudcreativity.storage.ui.config.BalanceActivity;
import com.cloudcreativity.storage.databinding.ActivitySettingBinding;
import com.cloudcreativity.storage.receiver.MyBusinessReceiver;
import com.cloudcreativity.storage.utils.CacheUtils;
import com.cloudcreativity.storage.utils.UpdateManager;

public class SettingModel {

    public ObservableField<String> cache = new ObservableField<>();
    public ObservableField<String> version = new ObservableField<>();

    private ActivitySettingBinding binding;
    private SettingActivity context;


    SettingModel(ActivitySettingBinding binding, SettingActivity context) {
        this.binding = binding;
        this.context = context;

        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                cache.set(CacheUtils.getTotalCacheSize(SettingModel.this.context));
                try {
                    PackageInfo packageInfo = SettingModel.this.context.getPackageManager()
                            .getPackageInfo(SettingModel.this.context.getPackageName(), 0);
                    version.set("v"+packageInfo.versionName);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                    version.set("v1.0");
                }
            }
        });
    }

    /**
     * 退出登录点击
     */
    public void onExitLoginClick(){
        Intent intent = new Intent();
        intent.setAction(MyBusinessReceiver.ACTION_LOGOUT);
        context.sendBroadcast(intent);
    }

    /**
     * 清空缓存点击
     */
    public void onClearCacheClick(){
        CacheUtils.clearCache(context);
        cache.set("0.0KB");
    }

    public void onVersionClick(){
        UpdateManager.checkVersion(context,context);
    }

    public void onHelpClick(){
        CommonWebActivity.startActivity(context,"用户使用帮助","file:////android_asset/help.html");
    }
}

package com.cloudcreativity.storage.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.base.BaseDialogImpl;
import com.cloudcreativity.storage.entity.AppVersion;

import org.json.JSONException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 这是app的热更新工具
 */
public class UpdateManager{

    public static void checkVersion(Context context, BaseDialogImpl baseDialog){
        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            checkLastVersionFromServer(context,info.versionName,info.versionCode,baseDialog);
        } catch (PackageManager.NameNotFoundException e) {
            ToastUtils.showShortToast(context,"获取版本信息失败");
        }
    }

    /**
     *
     * @param context 上下文
     * @param versionName 版本描述
     * @param versionCode 版本号
     */
    private static void checkLastVersionFromServer(final Context context, String versionName, final int versionCode, BaseDialogImpl baseDialog){

        HttpUtils.getInstance().getLastVersion()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<AppVersion>(baseDialog,false) {
                    @Override
                    public void onSuccess(final AppVersion t) {
                        try {
                            if(t.getInfo().getMark()>versionCode){
                                final Dialog dialog = new Dialog(context, R.style.myDialogStyleAnim);
                                View view = LayoutInflater.from(context).inflate(R.layout.layout_download_apk_dialog, null, false);
                                dialog.setCancelable(false);
                                dialog.setCanceledOnTouchOutside(false);

                                WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
                                dialog.setContentView(view);
                                attributes.width = context.getResources().getDisplayMetrics().widthPixels*4/5;

                                TextView tv_version = view.findViewById(R.id.tv_version);
                                TextView tv_content = view.findViewById(R.id.tv_content);

                                tv_version.setText(t.getInfo().getNum());
                                tv_content.setText(t.getInfo().getContent());

                                view.findViewById(R.id.tv_download).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                        try {
                                            new DownloadApkDialogUtils(context).execute(t.getInfo().getUrl());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            ToastUtils.showShortToast(context,"下载安装包失败");
                                        }
                                    }
                                });
                                dialog.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastUtils.showShortToast(context,"获取版本信息失败");
                        }
                    }

                    @Override
                    public void onFail(ExceptionReason msg) {
                        ToastUtils.showShortToast(context,"获取版本信息失败");
                    }
                });
    }
}

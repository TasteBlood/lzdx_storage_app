package com.cloudcreativity.storage.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.cloudcreativity.storage.base.BaseApp;
import com.cloudcreativity.storage.ui.loginAndRegister.LoginActivity;
import com.cloudcreativity.storage.utils.SPUtils;

/**
 * 自己业务的广播接收者
 */
public class MyBusinessReceiver extends BroadcastReceiver {

    //重新登录
    public static final String ACTION_RE_LOGIN = "storage_action_re_login";

    //关闭应用
    public static final String ACTION_EXIT_APP = "storage_action_exit_application";

    //退出登录
    public static final String ACTION_LOGOUT = "storage_action_logout";

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent==null)
            return;
        if(ACTION_RE_LOGIN.equals(intent.getAction())){
            //首先清空所有的用户数据
            SPUtils spUtils = SPUtils.get();
            spUtils.putBoolean(SPUtils.Config.IS_LOGIN,false);
            spUtils.putString(SPUtils.Config.USER,"{}");
            spUtils.setUID(0);
            spUtils.putString(SPUtils.Config.TOKEN,null);
            spUtils.setRole(0);

            //这一步是关闭程序
            BaseApp.app.destroyActivity();

            //说明就是重新登录的操作
            Intent newIntent = new Intent(context, LoginActivity.class);
            newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(newIntent);
        }else if(ACTION_EXIT_APP.equals(intent.getAction())){
            //说明就是关闭应用的操作
            BaseApp.app.onTerminate();
        }else if(ACTION_LOGOUT.equals(intent.getAction())){
            //退出登录，目前就是直接关闭，后期进行业务扩展
            //首先清空所有的用户数据
            SPUtils spUtils = SPUtils.get();
            spUtils.putBoolean(SPUtils.Config.IS_LOGIN,false);
            spUtils.putString(SPUtils.Config.USER,"{}");
            spUtils.setUID(0);
            spUtils.putString(SPUtils.Config.TOKEN,null);
            spUtils.setRole(0);
            BaseApp.app.destroyActivity();
            Intent newIntent = new Intent(context, LoginActivity.class);
            newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(newIntent);
        }
    }
}

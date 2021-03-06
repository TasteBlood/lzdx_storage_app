package com.cloudcreativity.storage.ui.main;

import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.base.BaseActivity;
import com.cloudcreativity.storage.databinding.ActivityMainBinding;
import com.cloudcreativity.storage.receiver.MyBusinessReceiver;
import com.cloudcreativity.storage.utils.BalanceUtils;
import com.cloudcreativity.storage.utils.PrinterUtils;
import com.cloudcreativity.storage.utils.ToastUtils;
import com.cloudcreativity.storage.utils.UpdateManager;
import com.jaeger.library.StatusBarUtil;

import java.io.IOException;


public class MainActivity extends BaseActivity{

    private ActivityMainBinding binding;
    private MyBusinessReceiver receiver;
    private MainModel mainModal;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        }
        //注册广播
        receiver = new MyBusinessReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(MyBusinessReceiver.ACTION_LOGOUT);
        filter.addAction(MyBusinessReceiver.ACTION_EXIT_APP);
        filter.addAction(MyBusinessReceiver.ACTION_RE_LOGIN);
        registerReceiver(receiver,filter);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainModal = new MainModel(this, binding,this);
        binding.setMainModal(mainModal);

        //StatusBarUtil.setTranslucent(this,200);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁广播
        unregisterReceiver(receiver);

        //关闭所有的连接
        try {
            BalanceUtils.getInstance().release();
            PrinterUtils.getInstance().release();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        UpdateManager.checkVersion(this,this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_CANCELED)
            return;
        if(requestCode!=100)
            return;
        String codedContent = data.getStringExtra("codedContent");
        if(mainModal!=null)
            mainModal.onResult(codedContent);
    }

    //记录用户首次点击返回键的时间
    private long firstTime = 0;

    @Override
    public void onBackPressed() {
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000) {
            ToastUtils.showShortToast(this, "再按一次退出程序");
            firstTime = secondTime;
        } else {
            sendBroadcast(new Intent(MyBusinessReceiver.ACTION_EXIT_APP));
        }
    }

}

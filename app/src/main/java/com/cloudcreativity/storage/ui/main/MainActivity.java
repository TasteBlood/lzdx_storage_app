package com.cloudcreativity.storage.ui.main;

import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.base.BaseActivity;
import com.cloudcreativity.storage.databinding.ActivityMainBinding;
import com.cloudcreativity.storage.receiver.MyBusinessReceiver;
import com.cloudcreativity.storage.utils.BalanceUtils;
import com.cloudcreativity.storage.utils.PrinterUtils;
import com.cloudcreativity.storage.utils.ToastUtils;

import java.io.IOException;


public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;
    private MyBusinessReceiver receiver;
    private MainModel mainModal;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        binding.nvMain.setNavigationItemSelectedListener(this);
        binding.ibClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.dwlMain.openDrawer(Gravity.START,true);
            }
        });
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        binding.dwlMain.closeDrawer(Gravity.START,true);
        if (item.getItemId() == R.id.nav_settings) {
            startActivity(new Intent().setClass(this, SettingActivity.class));
            return true;
        }
        return false;
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

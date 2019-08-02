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
        mainModal = new MainModel(binding, this);
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
}

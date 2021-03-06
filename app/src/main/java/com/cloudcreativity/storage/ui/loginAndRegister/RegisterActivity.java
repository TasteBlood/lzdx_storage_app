package com.cloudcreativity.storage.ui.loginAndRegister;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.base.BaseActivity;
import com.cloudcreativity.storage.databinding.ActivityRegisterBinding;

/**
 * 注册
 */
public class RegisterActivity extends BaseActivity {

    private RegisterModel model;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityRegisterBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        binding.tlbRegister.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.setRegisterModel(model = new RegisterModel(this, binding));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        model.stopTimer();
    }
}

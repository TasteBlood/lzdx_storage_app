package com.cloudcreativity.storage.ui.enter;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.base.BaseActivity;
import com.cloudcreativity.storage.databinding.ActivityContactBinding;

/**
 * All Rights Reserved By CloudCreativity Tech.
 * @author : created by Xu Xiwu
 * date-time: 2019/10/29 9:39
 * e-mail: xxw0701@sina.com
 */
public class ContractActivity extends BaseActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityContactBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_contact);
        binding.setModel(new ContractModel(this,binding,this));
    }
}

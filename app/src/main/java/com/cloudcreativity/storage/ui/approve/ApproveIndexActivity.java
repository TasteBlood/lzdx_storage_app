package com.cloudcreativity.storage.ui.approve;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.base.BaseActivity;
import com.cloudcreativity.storage.databinding.ActivityApproveIndexBinding;

/**
 * All Rights Reserved By CloudCreativity Tech.
 *
 * @author : created by Xu Xiwu
 * date-time: 2019/12/13 14:41
 * e-mail: xxw0701@sina.com
 */
public class ApproveIndexActivity extends BaseActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityApproveIndexBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_approve_index);
        binding.setModel(new ApproveIndexModel(this,binding,this));
    }
}

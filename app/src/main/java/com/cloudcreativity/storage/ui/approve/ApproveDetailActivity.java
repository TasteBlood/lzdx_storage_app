package com.cloudcreativity.storage.ui.approve;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.base.BaseActivity;
import com.cloudcreativity.storage.databinding.ActivityApproveDetailBinding;

/**
 * All Rights Reserved By CloudCreativity Tech.
 *
 * @author : created by Xu Xiwu
 * date-time: 2019/12/6 11:03
 * e-mail: xxw0701@sina.com
 */
public class ApproveDetailActivity extends BaseActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityApproveDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_approve_detail);
        int oid = getIntent().getIntExtra("oid", 0);
        binding.setModel(new ApproveDetailModel(oid,this,binding,this));
    }

}

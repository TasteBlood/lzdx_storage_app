package com.cloudcreativity.storage.ui.outapply;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.base.BaseActivity;
import com.cloudcreativity.storage.databinding.ActivityOutApplyBinding;

/**
 * All Rights Reserved By CloudCreativity Tech.
 *
 * @author : created by Xu Xiwu
 * date-time: 2019/9/25 10:39
 * e-mail: xxw0701@sina.com
 */
public class OutApplyActivity extends BaseActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityOutApplyBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_out_apply);
        binding.setModel(new OutApplyModel(this,binding,this));
        binding.tlbOutApply.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}

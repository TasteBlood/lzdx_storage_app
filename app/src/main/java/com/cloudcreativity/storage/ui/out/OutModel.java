package com.cloudcreativity.storage.ui.out;

import android.view.View;

import com.cloudcreativity.storage.base.BaseActivity;
import com.cloudcreativity.storage.base.BaseDialogImpl;
import com.cloudcreativity.storage.base.BaseModel;
import com.cloudcreativity.storage.databinding.ActivityOutBinding;

public class OutModel extends BaseModel<BaseActivity, ActivityOutBinding>{

    OutModel(BaseActivity context, ActivityOutBinding binding, BaseDialogImpl baseDialog) {
        super(context, binding, baseDialog);

    }

    @Override
    protected void initView() {
        binding.tlbOut.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.finish();
            }
        });
    }

    @Override
    protected void initData() {

    }
}

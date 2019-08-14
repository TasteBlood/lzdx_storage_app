package com.cloudcreativity.storage.ui.enter;

import android.view.View;

import com.cloudcreativity.storage.base.BaseActivity;
import com.cloudcreativity.storage.base.BaseDialogImpl;
import com.cloudcreativity.storage.base.BaseModel;
import com.cloudcreativity.storage.databinding.ActivityEnterStoreBinding;

public class EnterStoreModel extends BaseModel<BaseActivity, ActivityEnterStoreBinding>{

    EnterStoreModel(BaseActivity context, ActivityEnterStoreBinding binding, BaseDialogImpl baseDialog) {
        super(context, binding, baseDialog);
    }

    @Override
    protected void initView() {
        binding.tlbEnter.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.finish();
            }
        });
    }

    @Override
    protected void initData() {

    }
}

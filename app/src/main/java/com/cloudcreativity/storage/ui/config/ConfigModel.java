package com.cloudcreativity.storage.ui.config;

import android.content.Intent;
import android.view.View;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.base.BaseActivity;
import com.cloudcreativity.storage.base.BaseDialogImpl;
import com.cloudcreativity.storage.base.BaseModel;
import com.cloudcreativity.storage.databinding.ActivityConfigIndexBinding;
import com.cloudcreativity.storage.utils.BalanceUtils;

public class ConfigModel extends BaseModel<BaseActivity, ActivityConfigIndexBinding>{

    ConfigModel(BaseActivity context, ActivityConfigIndexBinding binding, BaseDialogImpl baseDialog) {
        super(context, binding,baseDialog);
    }

    @Override
    protected void initView() {
        binding.tlbConfig.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.finish();
            }
        });
    }

    @Override
    protected void initData() {
        if(BalanceUtils.getInstance().getSocket()!=null&&BalanceUtils.getInstance().getSocket().isConnected()){
            binding.tvBalanceStatus.setText("已连接");
        }else{
            binding.tvBalanceStatus.setText("未连接");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_balance:
                context.startActivity(new Intent(context,BalanceActivity.class));
                break;
            case R.id.ll_printer:
                break;
        }
    }
}

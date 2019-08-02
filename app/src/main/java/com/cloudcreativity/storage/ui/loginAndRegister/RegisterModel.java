package com.cloudcreativity.storage.ui.loginAndRegister;

import android.databinding.ObservableField;
import android.os.CountDownTimer;
import android.text.TextUtils;

import com.cloudcreativity.storage.base.BaseDialogImpl;
import com.cloudcreativity.storage.databinding.ActivityRegisterBinding;
import com.cloudcreativity.storage.utils.StrUtils;
import com.cloudcreativity.storage.utils.ToastUtils;

public class RegisterModel {

    public ObservableField<String> phone = new ObservableField<>();
    public ObservableField<String> verifyCode = new ObservableField<>();
    public ObservableField<String> password = new ObservableField<>();
    public ObservableField<String> btn_verify_text = new ObservableField<>();
    public ObservableField<Boolean> is_check_protocol = new ObservableField<>();
    public ObservableField<Boolean> is_enable = new ObservableField<>();

    private CountDownTimer timer;
    private RegisterActivity context;
    private BaseDialogImpl baseDialog;
    private ActivityRegisterBinding binding;

    RegisterModel(RegisterActivity context, ActivityRegisterBinding binding) {
        this.context = context;
        this.baseDialog = context;
        this.binding = binding;

        btn_verify_text.set("发送验证码");
        is_check_protocol.set(false);
        is_enable.set(true);
    }

    /**
     * 发送验证码
     */
    public void onSendVerifyClick(){
        if(!is_enable.get())
            return;
        startTimer();

    }

    /**
     * 查看用户协议
     */
    public void onLookProtocolClick(){

    }

    /**
     * 注册点击
     */
    public void onRegisterClick(){
        if(TextUtils.isEmpty(phone.get())||!StrUtils.isPhone(phone.get())){
            binding.tilPhone.setError("手机号格式不正确");
            return;
        }
        binding.tilPhone.setError(null);
        if(TextUtils.isEmpty(password.get())){
            binding.tilPwd.setError("密码不能为空");
            return;
        }
        binding.tilPwd.setError(null);
        if(TextUtils.isEmpty(verifyCode.get())){
            binding.tilVerifyCode.setError("验证码不能为空");
            return;
        }
        binding.tilVerifyCode.setError(null);
        if(!is_check_protocol.get()){
            ToastUtils.showShortToast(context,"请同意用户使用协议");
            return;
        }
    }

    /**
     * 开始计时
     */
    private void startTimer(){
        if(timer!=null){
            timer.cancel();
            timer = null;
        }
        is_enable.set(false);
        timer = new CountDownTimer(120*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                btn_verify_text.set((millisUntilFinished/1000)+"S后");
            }

            @Override
            public void onFinish() {
                stopTimer();
            }
        };

        timer.start();

    }

    /**
     * 停止计时
     */
    void stopTimer(){
        if(timer!=null){
            timer.cancel();
            timer = null;
        }
        is_enable.set(true);
        btn_verify_text.set("发送验证码");
    }
}

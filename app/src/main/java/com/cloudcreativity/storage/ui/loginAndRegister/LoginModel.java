package com.cloudcreativity.storage.ui.loginAndRegister;

import android.databinding.ObservableField;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.base.BaseDialogImpl;
import com.cloudcreativity.storage.base.BaseResult;
import com.cloudcreativity.storage.databinding.ActivityLoginBinding;
import com.cloudcreativity.storage.utils.DefaultObserver;
import com.cloudcreativity.storage.utils.HttpUtils;
import com.cloudcreativity.storage.utils.ToastUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginModel {
    public ObservableField<String> phone = new ObservableField<>();
    public ObservableField<String> pwd = new ObservableField<>();
    private ActivityLoginBinding binding;
    private LoginActivity context;
    private BaseDialogImpl baseDialog;
    private String[] role;
    private String currentRole;
    LoginModel(ActivityLoginBinding binding, LoginActivity context) {
        this.binding = binding;
        this.context = context;
        this.baseDialog = context;
        role = context.getResources().getStringArray(R.array.role);
        currentRole = role[0];
        binding.spRole.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                currentRole = role[i];
            }
        });
    }

    /**
     * 登录按钮点击
     */
    public void onLoginClick(){
        if(TextUtils.isEmpty(phone.get())){
            ToastUtils.showShortToast(context,"账号不正确");
            return;
        }
        if(TextUtils.isEmpty(pwd.get())){
            ToastUtils.showShortToast(context,"密码不能为空");
            return;
        }
        switch (currentRole){
            case "仓库管理员":
                adminLogin(phone.get(),pwd.get());
                break;
            case "餐饮采价员":
                restaurantLogin(phone.get(),pwd.get());
                break;
            case "采购采价员":
                priceLogin(phone.get(),pwd.get());
                break;
        }
    }

    //采价员登录
    private void priceLogin(String phone, String pwd) {
        HttpUtils.getInstance().priceLogin(phone,pwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BaseResult>(baseDialog,true) {
                    @Override
                    public void onSuccess(BaseResult baseResult) {

                    }

                    @Override
                    public void onFail(ExceptionReason msg) {

                    }
                });
    }

    //餐饮采价员登录
    private void restaurantLogin(String phone, String pwd) {
        HttpUtils.getInstance().restaurantLogin(phone,pwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BaseResult>(baseDialog,true) {
                    @Override
                    public void onSuccess(BaseResult baseResult) {

                    }

                    @Override
                    public void onFail(ExceptionReason msg) {

                    }
                });
    }

    //库管登录
    private void adminLogin(String phone, String pwd) {
        HttpUtils.getInstance().adminLogin(phone,pwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BaseResult>(baseDialog,true) {
                    @Override
                    public void onSuccess(BaseResult baseResult) {

                    }

                    @Override
                    public void onFail(ExceptionReason msg) {

                    }
                });
    }

    /**
     * 帮助点击
     */
    public void onHelpClick(){
        ToastUtils.showShortToast(context,"帮助了");
    }

    /**
     * 退出
     */
    public void onBack(){
        context.finish();
    }
}

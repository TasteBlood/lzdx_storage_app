package com.cloudcreativity.storage.ui.loginAndRegister;

import android.content.Intent;
import android.databinding.ObservableField;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.base.BaseDialogImpl;
import com.cloudcreativity.storage.base.BaseResult;
import com.cloudcreativity.storage.base.CommonWebActivity;
import com.cloudcreativity.storage.databinding.ActivityLoginBinding;
import com.cloudcreativity.storage.entity.UserEntity;
import com.cloudcreativity.storage.ui.main.MainActivity;
import com.cloudcreativity.storage.utils.AppConfig;
import com.cloudcreativity.storage.utils.DefaultObserver;
import com.cloudcreativity.storage.utils.HttpUtils;
import com.cloudcreativity.storage.utils.SPUtils;
import com.cloudcreativity.storage.utils.ToastUtils;
import com.google.gson.Gson;

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
        binding.spRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentRole = role[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
            case "采购询价员":
                priceLogin(phone.get(),pwd.get());
                break;
        }
    }

    //询价员登录
    private void priceLogin(String phone, String pwd) {
        HttpUtils.getInstance().adminLogin(phone,pwd,2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<UserEntity>(baseDialog,true) {
                    @Override
                    public void onSuccess(UserEntity baseResult) {
                        UserEntity.Entity info = baseResult.getInfo();
                        if(info!=null&&baseResult.getInfo().getState()==2){
                            //是
                            SPUtils.get().setLogin(true);
                            SPUtils.get().putString(SPUtils.Config.TOKEN,baseResult.getInfo().getToken());
                            SPUtils.get().setUID(baseResult.getInfo().getId());
                            SPUtils.get().putString(SPUtils.Config.USER,new Gson().toJson(baseResult.getInfo()));
                            SPUtils.get().setRole(AppConfig.USER_ROLE.PRICE);
                            context.startActivity(new Intent(context, MainActivity.class));
                            context.finish();
                        }else{
                            //不是库管
                            ToastUtils.showShortToast(context,"该询价员不存在");
                        }
                    }

                    @Override
                    public void onFail(ExceptionReason msg) {

                    }
                });
    }

    //餐饮采价员登录
    private void restaurantLogin(String phone, String pwd) {
        HttpUtils.getInstance().adminLogin(phone,pwd,0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<UserEntity>(baseDialog,true) {
                    @Override
                    public void onSuccess(UserEntity baseResult) {
                        UserEntity.Entity info = baseResult.getInfo();
                        if(info!=null&&baseResult.getInfo().getState()==0){
                            //是库管
                            SPUtils.get().setLogin(true);
                            SPUtils.get().putString(SPUtils.Config.TOKEN,baseResult.getInfo().getToken());
                            SPUtils.get().setUID(baseResult.getInfo().getId());
                            SPUtils.get().putString(SPUtils.Config.USER,new Gson().toJson(baseResult.getInfo()));
                            SPUtils.get().setRole(AppConfig.USER_ROLE.RESTAURANT);
                            context.startActivity(new Intent(context, MainActivity.class));
                            context.finish();
                        }else{
                            //不是库管
                            ToastUtils.showShortToast(context,"该采价员不存在");
                        }
                    }

                    @Override
                    public void onFail(ExceptionReason msg) {

                    }
                });
    }

    //库管登录
    private void adminLogin(String phone, String pwd) {
        HttpUtils.getInstance().adminLogin(phone,pwd,1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<UserEntity>(baseDialog,true) {
                    @Override
                    public void onSuccess(UserEntity baseResult) {
                        UserEntity.Entity info = baseResult.getInfo();
                        if(info!=null&&baseResult.getInfo().getState()==1&&baseResult.getInfo().getStoreId()!=0){
                            //是库管
                            SPUtils.get().setLogin(true);
                            SPUtils.get().putString(SPUtils.Config.TOKEN,baseResult.getInfo().getToken());
                            SPUtils.get().setUID(baseResult.getInfo().getId());
                            SPUtils.get().putString(SPUtils.Config.USER,new Gson().toJson(baseResult.getInfo()));
                            SPUtils.get().setRole(AppConfig.USER_ROLE.MANAGER);
                            context.startActivity(new Intent(context, MainActivity.class));
                            context.finish();
                        }else{
                            //不是库管
                            ToastUtils.showShortToast(context,"该库管不存在");
                        }
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
        CommonWebActivity.startActivity(context,"用户使用帮助","file:////android_asset/help.html");
    }

    /**
     * 退出
     */
    public void onBack(){
        context.finish();
    }
}

package com.cloudcreativity.storage.ui.loginAndRegister;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.ObservableField;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.base.BaseDialogImpl;
import com.cloudcreativity.storage.base.BaseResult;
import com.cloudcreativity.storage.base.CommonWebActivity;
import com.cloudcreativity.storage.databinding.ActivityLoginBinding;
import com.cloudcreativity.storage.entity.LoginAccount;
import com.cloudcreativity.storage.entity.UserEntity;
import com.cloudcreativity.storage.entity.UserListWrapper;
import com.cloudcreativity.storage.ui.main.MainActivity;
import com.cloudcreativity.storage.utils.AppConfig;
import com.cloudcreativity.storage.utils.DefaultObserver;
import com.cloudcreativity.storage.utils.HttpUtils;
import com.cloudcreativity.storage.utils.LogUtils;
import com.cloudcreativity.storage.utils.SPUtils;
import com.cloudcreativity.storage.utils.ToastUtils;
import com.google.gson.Gson;

import java.util.List;

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
    private List<LoginAccount> accounts;
    LoginModel(final ActivityLoginBinding binding, LoginActivity context) {
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
        accounts = SPUtils.get().getAccounts();
        String[] titles = new String[accounts.size()];
        for(int i=0;i<accounts.size();i++){
            titles[i] = accounts.get(i).getAccountName();
        }
        binding.etPhone.setAdapter(new ArrayAdapter<>(context,android.R.layout.simple_spinner_dropdown_item,titles));
        binding.etPhone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtils.e("xuxiwu","on item selected"+accounts.get(position).getAccountPass());
                pwd.set(accounts.get(position).getAccountPass());
            }
        });
        binding.etPhone.setThreshold(1);
        binding.etPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    binding.etPhone.showDropDown();
                }
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
            case "仓库管理":
                adminLogin(phone.get(),pwd.get());
                break;
            case "餐饮采价":
                restaurantLogin(phone.get(),pwd.get());
                break;
            case "出库审批":
                outLogin(phone.get(),pwd.get());
                break;
            case "采购询价":
                priceLogin(phone.get(),pwd.get());
                break;
            case "中心负责人":
                // state 5
                approveLogin(5,phone.get(),pwd.get());
                break;
            case "主管部领导":
                // state 6
                approveLogin(6,phone.get(),pwd.get());
                break;
            case "财务审核人":
                // state 7
                approveLogin(7,phone.get(),pwd.get());
                break;
            case "部领导":
                // state 8
                approveLogin(8,phone.get(),pwd.get());
                break;
        }
    }

    //询价员登录
    private void priceLogin(final String phone, final String pwd) {
        HttpUtils.getInstance().login(phone,pwd,2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<UserListWrapper>(baseDialog,true) {
                    @Override
                    public void onSuccess(UserListWrapper baseResult) {
                        List<UserEntity.Entity> list = baseResult.getInfo();
                        // state ==0
                        if(list!=null&&!list.isEmpty()&&list.get(0).getState()==2){
                            //是审批人
                            saveAccount(phone,pwd);
                            UserEntity.Entity info = list.get(0);
                            SPUtils.get().setLogin(true);
                            SPUtils.get().putString(SPUtils.Config.TOKEN,info.getToken());
                            SPUtils.get().setUID(info.getId());
                            SPUtils.get().putString(SPUtils.Config.USER,new Gson().toJson(info));
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
    private void restaurantLogin(final String phone, final String pwd) {
        HttpUtils.getInstance().login(phone,pwd,0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<UserListWrapper>(baseDialog,true) {
                    @Override
                    public void onSuccess(UserListWrapper baseResult) {
                        List<UserEntity.Entity> list = baseResult.getInfo();
                        // state ==0
                        if(list!=null&&!list.isEmpty()&&list.get(0).getState()==0){
                            //是审批人
                            saveAccount(phone,pwd);
                            UserEntity.Entity info = list.get(0);
                            SPUtils.get().setLogin(true);
                            SPUtils.get().putString(SPUtils.Config.TOKEN,info.getToken());
                            SPUtils.get().setUID(info.getId());
                            SPUtils.get().putString(SPUtils.Config.USER,new Gson().toJson(info));
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
    private void adminLogin(final String phone, final String pwd) {
        HttpUtils.getInstance().adminLogin(phone,pwd,1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<UserListWrapper>(baseDialog,true) {
                    @Override
                    public void onSuccess(final UserListWrapper t) {
                        if(t!=null&&t.getInfo()!=null&&!t.getInfo().isEmpty()){
                            //展示对话框进入库房选择页面
                            String[] storeNames = new String[t.getInfo().size()];
                            for (int i = 0; i <t.getInfo().size(); i++) {
                                storeNames[i] = t.getInfo().get(i).getStoreName();
                            }
                            new AlertDialog.Builder(context)
                                    .setTitle("选择仓库")
                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .setItems(storeNames, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            saveAccount(phone,pwd);
                                            UserEntity.Entity info = t.getInfo().get(which);
                                            SPUtils.get().setLogin(true);
                                            SPUtils.get().putString(SPUtils.Config.TOKEN,info.getToken());
                                            SPUtils.get().setUID(info.getId());
                                            SPUtils.get().putString(SPUtils.Config.USER,new Gson().toJson(info));
                                            SPUtils.get().setRole(AppConfig.USER_ROLE.MANAGER);
                                            context.startActivity(new Intent(context, MainActivity.class));
                                            context.finish();
                                        }
                                    }).show();

                        }else{
                            ToastUtils.showShortToast(context,"该库管不存在");
                        }
                    }
                });
    }

    //出库审批人
    private void outLogin(final String phone, final String pwd){
        HttpUtils.getInstance().login(phone,pwd,3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<UserListWrapper>(baseDialog,true) {
                    @Override
                    public void onSuccess(UserListWrapper baseResult) {
                        List<UserEntity.Entity> list = baseResult.getInfo();
                        // state ==3
                        if(list!=null&&!list.isEmpty()&&list.get(0).getState()==3){
                            //是审批人
                            saveAccount(phone,pwd);
                            UserEntity.Entity info = list.get(0);
                            SPUtils.get().setLogin(true);
                            SPUtils.get().putString(SPUtils.Config.TOKEN,info.getToken());
                            SPUtils.get().setUID(info.getId());
                            SPUtils.get().putString(SPUtils.Config.USER,new Gson().toJson(info));
                            SPUtils.get().setRole(AppConfig.USER_ROLE.OUT_ADMIN);
                            context.startActivity(new Intent(context, MainActivity.class));
                            context.finish();
                        }else{
                            //不是库管
                            ToastUtils.showShortToast(context,"该审批人不存在");
                        }
                    }

                    @Override
                    public void onFail(ExceptionReason msg) {

                    }
                });
    }

    //报销审批人登录
    private void approveLogin(final int state, final String phone, final String pwd){
        HttpUtils.getInstance().login(phone,pwd,state)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<UserListWrapper>(baseDialog,true) {
                    @Override
                    public void onSuccess(UserListWrapper baseResult) {
                        List<UserEntity.Entity> list = baseResult.getInfo();

                        if(list!=null&&!list.isEmpty() &&
                                (list.get(0).getState()==5||list.get(0).getState()==6||list.get(0).getState()==7||list.get(0).getState()==8)){
                            //是审批人
                            //展示对话框进入库房选择页面
                            String[] storeNames = new String[list.size()];
                            for (int i = 0; i <list.size(); i++) {
                                storeNames[i] = list.get(i).getStoreName();
                            }
                            saveAccount(phone,pwd);
                            UserEntity.Entity info = list.get(0);
                            SPUtils.get().setLogin(true);
                            SPUtils.get().putString(SPUtils.Config.TOKEN,info.getToken());
                            SPUtils.get().setUID(info.getId());
                            SPUtils.get().putString(SPUtils.Config.USER,new Gson().toJson(info));
                            if(state==5){
                                //中心负责人
                                SPUtils.get().setRole(AppConfig.USER_ROLE.SP_ZXFZR);
                            }else if(state==6){
                                //主管部领导
                                SPUtils.get().setRole(AppConfig.USER_ROLE.SP_ZGBLD);
                            }else if(state==7){
                                //财务审核人
                                SPUtils.get().setRole(AppConfig.USER_ROLE.SP_CWSP);
                            }else if(state==8){
                                //部领导
                                SPUtils.get().setRole(AppConfig.USER_ROLE.SP_BLD);
                            }
                            context.startActivity(new Intent(context, MainActivity.class));
                            context.finish();
                        }else{
                            //不是库管
                            ToastUtils.showShortToast(context,"该审批人不存在");
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

    private void saveAccount(String accountName,String accountPass){
        boolean exists = false;
        for(int i=0;i<accounts.size();i++){
            //如果有相同的话，就把用户名和密码改了
            if(accounts.get(i).getAccountName().equals(accountName)){
                accounts.get(i).setAccountName(accountName);
                accounts.get(i).setAccountPass(accountPass);
                exists = true;
            }
        }
        if(!exists){
            LoginAccount account = new LoginAccount();
            account.setAccountPass(accountPass);
            account.setAccountName(accountName);
            accounts.add(account);
        }
        SPUtils.get().setAccounts(accounts);
    }


}

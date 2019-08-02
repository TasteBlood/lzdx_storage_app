package com.cloudcreativity.storage.ui.buyPrice;

import android.databinding.ObservableField;
import android.text.TextUtils;
import android.view.View;

import com.cloudcreativity.storage.base.BaseActivity;
import com.cloudcreativity.storage.base.BaseDialogImpl;
import com.cloudcreativity.storage.base.BaseModel;
import com.cloudcreativity.storage.base.BaseResult;
import com.cloudcreativity.storage.databinding.ActivityProviderAddBinding;
import com.cloudcreativity.storage.utils.DefaultObserver;
import com.cloudcreativity.storage.utils.HttpUtils;
import com.cloudcreativity.storage.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AddProviderModel extends BaseModel<BaseActivity, ActivityProviderAddBinding>{

    public ObservableField<String> shopName = new ObservableField<>();
    public ObservableField<String> realname = new ObservableField<>();
    public ObservableField<String> address = new ObservableField<>();
    public ObservableField<String> mobile = new ObservableField<>();


    AddProviderModel(BaseActivity context, ActivityProviderAddBinding binding, BaseDialogImpl baseDialog) {
        super(context, binding, baseDialog);
    }

    @Override
    protected void initView() {
        binding.tlbAddProvider.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.finish();
            }
        });
    }

    @Override
    protected void initData() {

    }

    public void onSave(){
        if(TextUtils.isEmpty(shopName.get())){
            getBaseDialog().showRequestErrorMessage("商家名称不能为空");
            return;
        }
        if(TextUtils.isEmpty(realname.get())){
            getBaseDialog().showRequestErrorMessage("真实姓名不能为空");
            return;
        }
        if(TextUtils.isEmpty(address.get())){
            getBaseDialog().showRequestErrorMessage("地址不能为空");
            return;
        }
        if(TextUtils.isEmpty(mobile.get())){
            getBaseDialog().showRequestErrorMessage("联系方式不能为空");
            return;
        }

        HttpUtils.getInstance().addProvider(shopName.get(),realname.get(),
                address.get(),mobile.get())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BaseResult>(getBaseDialog(),true) {
                    @Override
                    public void onSuccess(BaseResult baseResult) {
                        EventBus.getDefault().post("refresh_provider_list");
                        context.finish();
                    }

                    @Override
                    public void onFail(ExceptionReason msg) {

                    }
                });
    }
}

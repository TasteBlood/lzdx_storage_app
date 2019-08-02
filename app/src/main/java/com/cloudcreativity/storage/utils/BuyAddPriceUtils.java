package com.cloudcreativity.storage.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.databinding.LayoutDialogBuyAddPriceBinding;
import com.cloudcreativity.storage.entity.Provider;
import com.cloudcreativity.storage.ui.buyPrice.ProviderActivity;

public class BuyAddPriceUtils extends Dialog {

    private Activity activity;
    private OnOkListener onOkListener;
    public ObservableField<Provider.Entity> provider = new ObservableField<>();
    public ObservableField<String> price = new ObservableField<>();

    public BuyAddPriceUtils(@NonNull Context context, int themeResId, Activity activity) {
        super(context, themeResId);
        setCanceledOnTouchOutside(false);
        this.activity = activity;
        LayoutDialogBuyAddPriceBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(context), R.layout.layout_dialog_buy_add_price,null,false);
        binding.setUtils(this);
        setContentView(binding.getRoot());
        assert getWindow()!=null;
        getWindow().getAttributes().width = context.getResources().getDisplayMetrics().widthPixels-40;
    }

    public void setProvider(Provider.Entity provider){
        this.provider.set(provider);
    }

    public void setOnOkListener(OnOkListener onOkListener) {
        this.onOkListener = onOkListener;
    }

    public void onProviderClick(){
        Intent intent = new Intent(activity, ProviderActivity.class);
        activity.startActivityForResult(intent,100);
    }

    public void onCloseClick(){
        dismiss();
    }

    public void onSave(){
        if(this.onOkListener!=null){
            this.onOkListener.onOk(provider.get(),Float.parseFloat(price.get()));
        }
        dismiss();
        provider.set(null);
        price.set("");
    }

    public interface OnOkListener{
        void onOk(Provider.Entity entity,float price);
    }
}

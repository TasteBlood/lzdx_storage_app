package com.cloudcreativity.storage.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.databinding.LayoutDialogRestaurantPriceBinding;
import com.cloudcreativity.storage.entity.Provider;
import com.cloudcreativity.storage.ui.buyPrice.ProviderActivity;

public class RestaurantPriceUtils extends Dialog {

    private Activity activity;
    private OnOkListener onOkListener;
    public ObservableField<Provider.Entity> provider = new ObservableField<>();
    public ObservableField<String> price = new ObservableField<>();
    private ObservableField<String> address = new ObservableField<>();
    public ObservableField<String> remarks = new ObservableField<>();

    public RestaurantPriceUtils(@NonNull Context context, int themeResId, Activity activity) {
        super(context, themeResId);
        setCanceledOnTouchOutside(false);
        this.activity = activity;
        final LayoutDialogRestaurantPriceBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(context), R.layout.layout_dialog_restaurant_price,null,false);
        binding.setUtils(this);

        binding.btnSave.setEnabled(false);
        LocationUtils instance = LocationUtils.getInstance();
        instance.setOnAddressListener(new LocationUtils.OnAddressListener() {
            @Override
            public void onSuccess(String address) {
                binding.btnSave.setEnabled(true);
                binding.tvLocation.setText(address);
                RestaurantPriceUtils.this.address.set(address);
                binding.pbLocation.setVisibility(View.GONE);
            }
        });
        instance.startLocation();

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
        if(provider.get()==null){
            ToastUtils.showShortToast(getContext(),"供应商为空");
            return;
        }
        if(TextUtils.isEmpty(price.get())){
            return;
        }
        if(this.onOkListener!=null){
            this.onOkListener.onOk(provider.get(),Float.parseFloat(price.get()),address.get(),remarks.get());
        }
        dismiss();
        provider.set(null);
        price.set("");
        address.set("");
        remarks.set("");
    }

    public interface OnOkListener{
        void onOk(Provider.Entity entity, float price,String address,String remarks);
    }
}

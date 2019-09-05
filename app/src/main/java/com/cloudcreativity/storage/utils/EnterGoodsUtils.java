package com.cloudcreativity.storage.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.databinding.LayoutDialogEnterGoodsBinding;
import com.cloudcreativity.storage.entity.EnterGoods;

/**
 * All Rights Reserved By CloudCreativity Tech.
 * @author : created by Xu Xiwu
 * date-time: 2019/8/5 14:37
 * e-mail: xxw0701@sina.com
 */
public class EnterGoodsUtils extends Dialog implements DialogInterface.OnDismissListener {

    public ObservableField<EnterGoods.Entity> entity = new ObservableField<>();
    public ObservableField<String> position = new ObservableField<>();
    public ObservableField<String> weight = new ObservableField<>();
    public ObservableField<String> newPrice = new ObservableField<>();

    private OnOkListener onOkListener;
    private String address;
    private final LocationUtils instance;

    public void setOnOkListener(OnOkListener onOkListener) {
        this.onOkListener = onOkListener;
    }

    public EnterGoodsUtils(@NonNull Context context, int themeResId, EnterGoods.Entity entity) {
        super(context, themeResId);
        setOnDismissListener(this);
        this.entity.set(entity);
        weight.set(String.valueOf(entity.getNumber()));
        final LayoutDialogEnterGoodsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.layout_dialog_enter_goods, null, false);
        instance = LocationUtils.getInstance();
        binding.btnEnter.setEnabled(false);
        instance.setOnAddressListener(new LocationUtils.OnAddressListener() {
            @Override
            public void onSuccess(String address) {
                EnterGoodsUtils.this.address = address;
                binding.tvLocation.setText(address);
                binding.pbLocation.setVisibility(View.GONE);
                binding.btnEnter.setEnabled(true);
            }
        });
        instance.startLocation();
        binding.setUtils(this);
        setContentView(binding.getRoot());
        setCanceledOnTouchOutside(true);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        assert getWindow()!=null;
        getWindow().getAttributes().width = metrics.widthPixels-40;
    }

    public void getWeight(){
        try {
            BalanceUtils.getInstance().readData(getContext(), new BalanceUtils.OnDataListener() {
                @Override
                public void onData(String data) {
                    assert entity.get()!= null;
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            ToastUtils.showShortToast(getContext(),"获取重量失败，请检查电子秤连接正常");
        }
    }

    public void onOk(){
        dismiss();
        if(onOkListener!=null){
            float price = TextUtils.isEmpty(newPrice.get())?0:Float.parseFloat(newPrice.get());
            onOkListener.onOk(Integer.parseInt(weight.get()),address,position.get(),Float.valueOf(price*100).intValue());
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if(instance!=null)
            instance.stopLocation();
    }

    public interface OnOkListener{
        void onOk(int number,String address,String position,int newPrice);
    }

}

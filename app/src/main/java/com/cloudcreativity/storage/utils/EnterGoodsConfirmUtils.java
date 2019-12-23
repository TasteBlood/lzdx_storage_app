package com.cloudcreativity.storage.utils;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.view.LayoutInflater;
import android.view.Window;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.databinding.LayoutDialogEnterConfirmBinding;
import com.cloudcreativity.storage.entity.EnterGoods;

/**
 * All Rights Reserved By CloudCreativity Tech.
 *
 * @author : created by Xu Xiwu
 * date-time: 2019/11/20 10:38
 * e-mail: xxw0701@sina.com
 */
public class EnterGoodsConfirmUtils extends Dialog {
    public EnterGoods.Entity item;
    public ObservableField<Double> number = new ObservableField<>();
    public ObservableField<String> address = new ObservableField<>();
    public ObservableField<String> position = new ObservableField<>();
    private ObservableField<Integer> newPrice = new ObservableField<>();
    public ObservableField<String> productDate = new ObservableField<>();
    public ObservableField<Integer> period = new ObservableField<>();

    private OnOkListener onOkListener;

    public void setOnOkListener(OnOkListener onOkListener) {
        this.onOkListener = onOkListener;
    }

    public EnterGoodsConfirmUtils(Context context,EnterGoods.Entity item, double number, String address, String position,
                                  int newPrice, String productDate, int period) {
        super(context, R.style.myProgressDialogStyle);
        this.item = item;
        this.number.set(number);
        this.address.set(address);
        this.position.set(position);
        this.newPrice.set(newPrice);
        this.productDate.set(productDate);
        this.period.set(period);

        LayoutDialogEnterConfirmBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.layout_dialog_enter_confirm,null,false);
        setContentView(binding.getRoot());
        int widthPixels = context.getResources().getDisplayMetrics().widthPixels;
        Window window = getWindow();
        assert window != null;
        window.getAttributes().width = widthPixels-40;
        binding.setUtils(this);
    }

    public String formatNewPrice(){
        if(this.newPrice.get()!=null && this.newPrice.get()!=0){
            return String.valueOf(this.newPrice.get()/100f);
        }
        return "0";
    }

    public void onOk(){
        if(this.onOkListener!=null){
            this.onOkListener.onOk();
        }
        dismiss();
    }

    public void onCancel(){
        dismiss();
    }


    public interface OnOkListener{
        void onOk();
    }
}

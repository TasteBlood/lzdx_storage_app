package com.cloudcreativity.storage.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.databinding.LayoutDialogOutGoodsBinding;
import com.cloudcreativity.storage.entity.OutOrder;

import java.util.List;

/**
 * All Rights Reserved By CloudCreativity Tech.
 * @author : created by Xu Xiwu
 * date-time: 2019/8/5 14:37
 * e-mail: xxw0701@sina.com
 */
public class OutGoodsUtils extends Dialog implements DialogInterface.OnDismissListener {

    public ObservableField<OutOrder.OutGoods> entity = new ObservableField<>();

    private OnOkListener onOkListener;
    private String address;
    private int personId;
    private final LocationUtils instance;

    public ObservableField<String> user = new ObservableField<>();

    public void setOnOkListener(OnOkListener onOkListener) {
        this.onOkListener = onOkListener;
    }

    public OutGoodsUtils(@NonNull Context context, int themeResId, OutOrder.OutGoods entity, final List<OutOrder.OutAccount> accounts,String user) {
        super(context, themeResId);

        setOnDismissListener(this);

        this.entity.set(entity);

        this.user.set(user);

        final LayoutDialogOutGoodsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.layout_dialog_out_goods, null, false);
        binding.btnOut.setEnabled(false);

        instance = LocationUtils.getInstance();
        instance.setOnAddressListener(new LocationUtils.OnAddressListener() {
            @Override
            public void onSuccess(String address) {
                OutGoodsUtils.this.address = address;
                binding.pbLocation.setVisibility(View.GONE);
                binding.tvLocation.setText(address);
                binding.btnOut.setEnabled(true);
            }
        });
        instance.startLocation();

        binding.setUtils(this);
        setContentView(binding.getRoot());
        setCanceledOnTouchOutside(true);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        assert getWindow()!=null;
        getWindow().getAttributes().width = metrics.widthPixels-40;
        if(accounts!=null&&!accounts.isEmpty()){
            String[] titles = new String[accounts.size()];
            for(int i=0;i<accounts.size();i++){
                titles[i] = accounts.get(i).getAccountName();
            }
            personId = accounts.get(0).getAccountId();
            binding.spPerson.setAdapter(new ArrayAdapter<>(context,android.R.layout.simple_dropdown_item_1line,android.R.id.text1,titles));
            binding.spPerson.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    personId = accounts.get(position).getAccountId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }else{
            ToastUtils.showShortToast(context,"物品领用人为空，无法领取");
        }

    }

    public void onOk(){
        dismiss();
        if(onOkListener!=null){
            onOkListener.onOk(address,personId,user.get());
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if(instance!=null){
            instance.stopLocation();
        }
    }

    public interface OnOkListener{
        void onOk(String address,int personId,String user);
    }

}

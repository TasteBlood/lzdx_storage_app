package com.cloudcreativity.storage.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.databinding.LayoutQrCodeBinding;

public class QrCodeDialogUtils extends Dialog{

    private LayoutQrCodeBinding binding;
    private Context context;
    public ObservableField<String> qrCodeUrl = new ObservableField<>();
    public QrCodeDialogUtils(@NonNull Context context, int themeResId,String url) {
        super(context, themeResId);
        this.qrCodeUrl.set(url);
        this.context = context;
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_qr_code,null,false);
        //DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        setContentView(binding.getRoot());
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        binding.setUtils(this);
    }

    @BindingAdapter("qrCodeUrl")
    public static void showQrCode(ImageView imageView,String url){
        GlideUtils.load(imageView.getContext(),url,imageView);
    }

    public void onPrintClick(){
        ToastUtils.showShortToast(context,"打印了");
    }
}

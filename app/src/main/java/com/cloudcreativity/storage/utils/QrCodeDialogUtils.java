package com.cloudcreativity.storage.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.databinding.LayoutQrCodeBinding;
import com.cloudcreativity.storage.entity.StoreGoods;
import com.cloudcreativity.storage.ui.config.ConfigIndexActivity;
import com.dothantech.printer.IDzPrinter;

public class QrCodeDialogUtils extends Dialog{

    private LayoutQrCodeBinding binding;
    private Context context;
    private StoreGoods.Entity item;
    public QrCodeDialogUtils(@NonNull final Context context, int themeResId, final StoreGoods.Entity item) {
        super(context, themeResId);
        this.context = context;
        this.item = item;
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_qr_code,null,false);
        new Thread(){
            @Override
            public void run() {
                final Bitmap qrImage = QRCodeUtils.createQRImage(item.getBarCode(), 400, 400);
                new Handler(context.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if(qrImage==null){
                            ToastUtils.showShortToast(context,"二维码生成失败");
                            dismiss();
                        }else{
                            binding.ivQrCode.setImageBitmap(qrImage);
                        }
                    }
                });
            }
        }.start();
        //DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        setContentView(binding.getRoot());
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        binding.setUtils(this);
    }

    public void onPrintClick(){
        if(IDzPrinter.PrinterState.Disconnected.equals(PrinterUtils.getInstance().getLPAPI().getPrinterState())){
            new AlertDialog.Builder(getContext())
                    .setTitle("提示")
                    .setMessage("蓝牙打印机未连接，是否前去连接")
                    .setPositiveButton("确定", new OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getContext().startActivity(new Intent(getContext(), ConfigIndexActivity.class));
                        }
                    }).setNegativeButton("取消", new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
        }else{
            if(PrinterUtils.getInstance().printDocument(item.getGoodsName(),item.getProviderName(),StrUtils.get2BitDecimal(item.getPrice()/100f),
            item.getBarCode())){
                ToastUtils.showShortToast(getContext(),"打印失败");
            }else{
                dismiss();
            }
        }
    }
}

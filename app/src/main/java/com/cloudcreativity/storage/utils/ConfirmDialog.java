package com.cloudcreativity.storage.utils;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.Window;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.databinding.LayoutDialogConfirmBinding;

/**
 * All Rights Reserved By CloudCreativity Tech.
 *
 * @author : created by Xu Xiwu
 * date-time: 2019/9/25 17:40
 * e-mail: xxw0701@sina.com
 */
public class ConfirmDialog extends Dialog {

    public ObservableField<String> content = new ObservableField<>();

    private OnConfirmListener onConfirmListener;

    public void setOnConfirmListener(OnConfirmListener onConfirmListener) {
        this.onConfirmListener = onConfirmListener;
    }

    public ConfirmDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        LayoutDialogConfirmBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_dialog_confirm,null,false);
        binding.setUtils(this);
        setContentView(binding.getRoot());
        Window window = getWindow();
        assert window != null;
        window.getAttributes().width = context.getResources().getDisplayMetrics().widthPixels-20;

    }

    public void onOk(){
        if(onConfirmListener!=null){
            onConfirmListener.onConfirm(content.get());
        }
        dismiss();
    }

    public interface OnConfirmListener{
         void onConfirm(String msg);
    }
}

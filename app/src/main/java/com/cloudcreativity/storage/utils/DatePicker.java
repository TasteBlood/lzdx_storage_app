package com.cloudcreativity.storage.utils;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.CalendarView;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.databinding.LayoutDialogDatePickerBinding;

import java.util.Calendar;
import java.util.Locale;

/**
 * All Rights Reserved By CloudCreativity Tech.
 *
 * @author : created by Xu Xiwu
 * date-time: 2019/9/26 10:37
 * e-mail: xxw0701@sina.com
 */
public class DatePicker extends Dialog {

    private OnDateChangeListener onDateChangeListener;

    public void setOnDateChangeLisntener(OnDateChangeListener onDateChangeLisntener) {
        this.onDateChangeListener = onDateChangeLisntener;
    }

    public DatePicker(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        LayoutDialogDatePickerBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_dialog_date_picker,null,false);
        binding.setUtils(this);
        setContentView(binding.getRoot());
        assert getWindow() != null;
        getWindow().getAttributes().width = WindowManager.LayoutParams.WRAP_CONTENT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.dpDialog.setOnDateChangedListener(new android.widget.DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    if(onDateChangeListener!=null){
                        onDateChangeListener.onChange(year,monthOfYear,dayOfMonth);
                    }

                    dismiss();
                }
            });
        }else{
            Calendar instance = Calendar.getInstance(Locale.CHINA);
            binding.dpDialog.init(instance.get(Calendar.YEAR), instance.get(Calendar.MONTH),
                    instance.get(Calendar.DAY_OF_MONTH), new android.widget.DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    if(onDateChangeListener!=null){
                        onDateChangeListener.onChange(year,monthOfYear,dayOfMonth);
                    }
                    dismiss();
                }
            });
        }
    }

    public interface OnDateChangeListener{
        void onChange(int year,int month,int day);
    }

}

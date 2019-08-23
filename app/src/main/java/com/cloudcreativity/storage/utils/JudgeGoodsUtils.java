package com.cloudcreativity.storage.utils;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.widget.RadioGroup;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.databinding.LayoutDialogJudgeGoodsBinding;

/**
 * All Rights Reserved By CloudCreativity Tech.
 *
 * @author : created by Xu Xiwu
 * date-time: 2019/8/22 9:37
 * e-mail: xxw0701@sina.com
 */
public class JudgeGoodsUtils extends Dialog {

    public ObservableField<String> number = new ObservableField<>();
    public ObservableField<String> remarks = new ObservableField<>();
    private OnOkListener onOkListener;

    private int state;

    public void setOnOkListener(OnOkListener onOkListener) {
        this.onOkListener = onOkListener;
    }

    public JudgeGoodsUtils(@NonNull Context context, int themeResId) {
        super(context, themeResId);

        initView(context);

        state = 1;
    }

    private void initView(Context context){
        LayoutDialogJudgeGoodsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_dialog_judge_goods,null,false);
        setContentView(binding.getRoot());
        assert getWindow()!= null;
        getWindow().getAttributes().width = context.getResources().getDisplayMetrics().widthPixels - 40;
        binding.setUtils(this);
        binding.rgJudge.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.rb_loss){
                    state = 1;
                }else if(checkedId==R.id.rb_win){
                    state = 2;
                }
            }
        });

    }

    public void onOk(){
        try{
            float v = Float.parseFloat(number.get());
            if(onOkListener!=null){
                onOkListener.onOk(state,v,remarks.get());
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        dismiss();
    }

    public interface OnOkListener{
        void onOk(int state,float number,String remarks);
    }
}

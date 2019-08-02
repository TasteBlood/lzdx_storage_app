package com.cloudcreativity.storage.base;

import android.app.Activity;
import android.databinding.ViewDataBinding;
import android.view.View;

/**
 *
 * @param <M> extends AppCompatActivity
 * @param <B> extends ViewDataBinding
 */
public abstract class BaseModel<M extends Activity,B extends ViewDataBinding>{
    protected M context;
    protected B binding;
    private BaseDialogImpl baseDialog;
    public BaseModel(M context, B binding,BaseDialogImpl baseDialog) {
        this.context = context;
        this.binding = binding;
        this.baseDialog = baseDialog;
        this.initView();
        this.initData();
    }

    public BaseDialogImpl getBaseDialog() {
        return baseDialog;
    }

    protected abstract void initView();
    protected abstract void initData();
    public void onBack(){}
    public void onClick(View v){}

}

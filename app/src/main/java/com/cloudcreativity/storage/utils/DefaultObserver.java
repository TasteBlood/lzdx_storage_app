package com.cloudcreativity.storage.utils;

import android.text.TextUtils;

import com.cloudcreativity.storage.base.BaseDialogImpl;
import com.cloudcreativity.storage.base.BaseResult;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

public abstract class DefaultObserver<T extends BaseResult> implements Observer<T> {
    private BaseDialogImpl impl;
    protected DefaultObserver(BaseDialogImpl impl){
        this.impl = impl;
    }
    protected DefaultObserver(BaseDialogImpl impl ,boolean isShowProgress){
        this(impl);
        if(isShowProgress)
            impl.showProgress("请稍后");
    }

    @Override
    public void onSubscribe(Disposable d) {
        impl.addRxDestroy(d);
    }

    @Override
    public void onComplete() {
        impl.dismissProgress();
    }

    @Override
    public void onError(Throwable e) {
        LogUtils.e(this.getClass().getName(), TextUtils.isEmpty(e.getMessage())?"出错啦":e.getMessage());
        impl.dismissProgress();
        if(e instanceof HttpException){
            impl.showRequestErrorMessage("网络异常");
            onFail(ExceptionReason.BAD_NETWORK);
        }else if(e instanceof ConnectException||e instanceof UnknownHostException){
            impl.showRequestErrorMessage("网络异常");
            onFail(ExceptionReason.CONNECT_ERROR);
        }else if(e instanceof InterruptedException){
            impl.showRequestErrorMessage("网络连接超时");
            onFail(ExceptionReason.CONNECT_TIMEOUT);
        }else if(e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException){
            onFail(ExceptionReason.PARSE_ERROR);
        }else{
            onFail(ExceptionReason.UNKNOWN_ERROR);
        }
    }
    @Override
    public void onNext(T t) {
        impl.dismissProgress();
        if(t==null){
            onFail(ExceptionReason.PARSE_ERROR);
            impl.showRequestErrorMessage("解析失败");
        }else{
            if(t.getStatus()==1){
                onSuccess(t);
            }else if(t.getStatus()==500){
                //清空用户信息
                SPUtils spUtils = SPUtils.get();
                spUtils.putBoolean(SPUtils.Config.IS_LOGIN,false);
                spUtils.putString(SPUtils.Config.USER,"{}");
                spUtils.setUID(0);
                spUtils.putString(SPUtils.Config.TOKEN,null);
                spUtils.setRole(0);
                impl.showUserAuthOutDialog();
            }else{
                impl.showRequestErrorMessage("系统异常");
                onFail(ExceptionReason.PARAMS_ERROR);
            }
        }
    }
    public abstract void onSuccess(T t);

    /**
     * @param msg 原因
     */
    public  void onFail(ExceptionReason msg){

    }

    /**
     * 请求网络失败原因
     */
    public enum ExceptionReason {
        /**
         * 解析数据失败
         */
        PARSE_ERROR,
        /**
         * 网络问题
         */
        BAD_NETWORK,
        /**
         * 连接错误
         */
        CONNECT_ERROR,
        /**
         * 连接超时
         */
        CONNECT_TIMEOUT,
        /**
         * 未知错误
         */
        UNKNOWN_ERROR,
        /**
         * 参数错误
         */
        PARAMS_ERROR
    }
}

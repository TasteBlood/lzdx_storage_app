package com.cloudcreativity.storage.utils;

import android.text.TextUtils;

import com.cloudcreativity.storage.base.BaseDialogImpl;
import com.cloudcreativity.storage.base.ParameterizedTypeImpl;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

public abstract class DefaultObserver<M> implements Observer<String> {
    private BaseDialogImpl impl;

    protected DefaultObserver(BaseDialogImpl impl) {
        this.impl = impl;
    }

    protected DefaultObserver(BaseDialogImpl impl, boolean isShowProgress) {
        this(impl);
        if (isShowProgress)
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
        LogUtils.e(this.getClass().getName(), TextUtils.isEmpty(e.getMessage()) ? "出错啦" : e.getMessage());
        impl.dismissProgress();
        if (e instanceof HttpException) {
            impl.showRequestErrorMessage("网络异常");
            onFail(ExceptionReason.BAD_NETWORK);
        } else if (e instanceof ConnectException || e instanceof NoRouteToHostException || e instanceof UnknownHostException || e instanceof SocketTimeoutException) {
            impl.showRequestErrorMessage("网络异常");
            onFail(ExceptionReason.CONNECT_ERROR);
        } else if (e instanceof InterruptedException) {
            impl.showRequestErrorMessage("网络中断");
            onFail(ExceptionReason.CONNECT_TIMEOUT);
        } else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) {
            onFail(ExceptionReason.PARSE_ERROR);
        } else {
            onFail(ExceptionReason.UNKNOWN_ERROR);
        }
    }

    @Override
    public void onNext(String t) {
        impl.dismissProgress();
        if (TextUtils.isEmpty(t)) {
            onFail(ExceptionReason.PARSE_ERROR);
            impl.showRequestErrorMessage("解析失败");
        } else {
            try {
                JSONObject obj = new JSONObject(t);
                if (obj.getInt("status") == 1) {
                    Class<M> mClass = (Class<M>) ((ParameterizedType)
                            getClass().getGenericSuperclass()).getActualTypeArguments()[0]; // 根据当前类获取泛型的Type
                    Type ty = new ParameterizedTypeImpl(mClass, new Class[]{mClass}); // 传泛型的Type和我们想要的外层类的Type来组装我们想要的类型
                    M data = new Gson().fromJson(t,ty);
                    onSuccess(data);
                } else if (obj.getInt("status") == 500) {
                    SPUtils spUtils = SPUtils.get();
                    spUtils.putBoolean(SPUtils.Config.IS_LOGIN, false);
                    spUtils.putString(SPUtils.Config.USER, "{}");
                    spUtils.setUID(0);
                    spUtils.putString(SPUtils.Config.TOKEN, null);
                    spUtils.setRole(0);
                    impl.showUserAuthOutDialog();
                } else {
                    impl.showRequestErrorMessage(obj.getString("info"));
                }
            } catch (Exception e) {
                e.printStackTrace();
                impl.showRequestErrorMessage("数据解析失败");
                onFail(ExceptionReason.PARSE_ERROR);
            }
        }
    }

    public abstract void onSuccess(M t);

    /**
     * @param msg 原因
     */
    public void onFail(ExceptionReason msg) {

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

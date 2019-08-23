package com.cloudcreativity.storage.utils;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.cloudcreativity.storage.entity.UserEntity;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 网络请求的日志打印工具
 */
public class LoggingInterceptor implements Interceptor {
    private String TAG = this.getClass().getName();
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        long startTime = System.currentTimeMillis();
        String method=request.method();
        if("POST".equals(method)){
            StringBuilder sb = new StringBuilder();
            if (request.body() instanceof FormBody) {
                //在这里进行参数的重新组装
                FormBody.Builder builder = new FormBody.Builder();
                FormBody body = (FormBody) request.body();
                for (int i = 0; i < body.size(); i++) {
                    sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",");
                    //重新添加参数
                    builder.addEncoded(body.encodedName(i), body.encodedValue(i));
                }
                //添加公共参数
                UserEntity.Entity user = SPUtils.get().getUser();
                if(user!=null&& !TextUtils.isEmpty(user.getToken())){
                    builder.addEncoded("token",user.getToken())
                            .addEncoded("uid",String.valueOf(user.getId()));
                    sb.append("token" + "=" + user.getToken() + ",");
                    sb.append("uid" + "=" +user.getId() + ",");
                }
                body = builder.build();
                sb.delete(sb.length() - 1, sb.length());
                LogUtils.e(TAG, "| RequestParams:{"+sb.toString()+"}");
                request = request.newBuilder().post(body).build();
            }
        }else if("GET".equals(method)){
            //LogUtils.e("xuxiwu_debug_test",SPUtils.get().getUser().toString());
            UserEntity.Entity user = SPUtils.get().getUser();
            if(user!=null&& !TextUtils.isEmpty(user.getToken())) {
                HttpUrl httpUrl = request.url().newBuilder()
                        .addQueryParameter("token", user.getToken())
                        .addQueryParameter("uid", String.valueOf(user.getId()))
//                        .addQueryParameter("cityId", String.valueOf(SPUtils.get().getUser().getCityId()))
//                        .addQueryParameter("areaId", SPUtils.get().getUser().getAreaId())
                        .build();
                request = request.newBuilder().url(httpUrl).build();
            }
        }
        LogUtils.e(TAG,"\n");
        LogUtils.e(TAG,"----------Start----------------");
        LogUtils.e(TAG, "| "+request.toString());

        Response response = chain.proceed(request);

        long endTime = System.currentTimeMillis();
        long duration=endTime-startTime;
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        LogUtils.e(TAG, "| Response:" + content);
        LogUtils.e(TAG,"----------End:"+duration+"毫秒----------");
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    }
}

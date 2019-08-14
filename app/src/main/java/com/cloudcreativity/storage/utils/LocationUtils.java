package com.cloudcreativity.storage.utils;

import android.content.Context;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.cloudcreativity.storage.base.BaseApp;

/**
 * All Rights Reserved By CloudCreativity Tech.
 *
 * @author : created by Xu Xiwu
 * date-time: 2019/8/5 15:48
 * e-mail: xxw0701@sina.com
 */
public class LocationUtils extends BDAbstractLocationListener {
    private static LocationUtils utils;
    private LocationClient locationClient;
    private OnAddressListener onAddressListener;

    void setOnAddressListener(OnAddressListener onAddressListener) {
        this.onAddressListener = onAddressListener;
    }

    private LocationUtils(Context context){
        locationClient = new LocationClient(context);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        option.setLocationNotify(false);
        option.setIsNeedAddress(true);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        locationClient.setLocOption(option);
        locationClient.registerLocationListener(this);

    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        //ToastUtils.showShortToast(BaseApp.app,bdLocation.getAddrStr());
        if(onAddressListener!=null)
            onAddressListener.onSuccess(bdLocation.getAddrStr());
        stopLocation();
    }

    public static LocationUtils getInstance(){
        return utils==null?utils = new LocationUtils(BaseApp.app):utils;
    }

    void startLocation(){
        locationClient.start();
    }

    void stopLocation(){
        if(locationClient!=null){
            locationClient.stop();
        }
    }

    public void release(){
        if(locationClient!=null){
            locationClient.stop();
        }
        utils = null;
    }

    public interface OnAddressListener{
        void onSuccess(String address);
    }

}

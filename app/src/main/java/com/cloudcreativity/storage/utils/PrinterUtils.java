package com.cloudcreativity.storage.utils;

import android.text.TextUtils;

import com.dothantech.lpapi.LPAPI;
import com.dothantech.printer.IDzPrinter;

import java.util.List;

/**
 * All Rights Reserved By CloudCreativity Tech.
 *
 * @author : created by Xu Xiwu
 * date-time: 2019/8/6 11:18
 * e-mail: xxw0701@sina.com
 */
public class PrinterUtils {

    private static PrinterUtils printerUtils;
    private LPAPI api;
    // 上次连接成功的设备对象
    private IDzPrinter.PrinterAddress mPrinterAddress = null;

    private onConnectListener onConnectListener;

    public void setOnConnectListenr(PrinterUtils.onConnectListener onConnectListener) {
        this.onConnectListener = onConnectListener;
    }

    public LPAPI getLPAPI(){
        return this.api;
    }

    public List<IDzPrinter.PrinterAddress> getAllPrinters(){
        return api.getAllPrinterAddresses(null);
    }

    //打印机操作相关回调函数
    private LPAPI.Callback callback = new LPAPI.Callback() {
        @Override
        public void onProgressInfo(IDzPrinter.ProgressInfo progressInfo, Object o) {

        }

        @Override
        public void onStateChange(IDzPrinter.PrinterAddress printerAddress, IDzPrinter.PrinterState printerState) {
            switch (printerState){
                case Connected:case Connected2:
                    //连接成功，提示消息
                    mPrinterAddress = printerAddress;
                    if(onConnectListener!=null)
                        onConnectListener.onConnectSuccess(mPrinterAddress);
                    break;
                case Disconnected:
                    //断开连接
                    mPrinterAddress = null;
                    if(onConnectListener!=null)
                        onConnectListener.onDisconnect();
                    break;
            }
        }

        @Override
        public void onPrintProgress(IDzPrinter.PrinterAddress printerAddress, Object o, IDzPrinter.PrintProgress printProgress, Object o1) {

        }

        @Override
        public void onPrinterDiscovery(IDzPrinter.PrinterAddress printerAddress, IDzPrinter.PrinterInfo printerInfo) {

        }
    };

    private PrinterUtils() {
        this.api = LPAPI.Factory.createInstance(callback);
    }

    public static synchronized PrinterUtils getInstance() {
        return printerUtils == null ? printerUtils = new PrinterUtils() : printerUtils;
    }

    public void connect(IDzPrinter.PrinterAddress address){
        if(this.api.openPrinterByAddress(address)){
            //打印机正在连接提示消息
            if(onConnectListener!=null)
                onConnectListener.onConnecting();
        }else{
            if(onConnectListener!=null)
                onConnectListener.onConnectError();
        }
    }

    public void disconnect(){
        this.api.closePrinter();
    }

    public void release(){
        disconnect();
        this.api.quit();
        this.mPrinterAddress = null;
    }

    public interface onConnectListener{
        void onConnecting();
        void onConnectSuccess(IDzPrinter.PrinterAddress address);
        void onConnectError();
        void onDisconnect();
    }

    public boolean printDocument(String goodsName,String providerName,float price,String qrCodeUrl){
        if(api==null) return false;
        api.startJob(40,60,0);
        api.drawText("兰大后勤保障部",5,1,30,10,4);
        api.drawText("名称:"+goodsName,0.5,10,35,10,3);
        api.drawText("供应商:"+(TextUtils.isEmpty(providerName)?"不详":providerName),0.5,16,35,10,3);
        api.drawText("单价:￥"+price,0.5,22,35,10,3);
        api.draw2DQRCode(qrCodeUrl,8,30,20);
        return !api.commitJob();
    }

}

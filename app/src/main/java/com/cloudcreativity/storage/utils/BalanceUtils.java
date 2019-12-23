package com.cloudcreativity.storage.utils;

import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Handler;

import java.io.IOException;
import java.io.InputStream;

public class BalanceUtils {

    public final static String MY_UUID = "00001101-0000-1000-8000-00805F9B34FB";

    private BalanceUtils(){

    }
    private BluetoothSocket socket;
    private static BalanceUtils utils;
    private OnDataListener onDataListener;
    private Handler handler;
    private ReadTask task;
    public synchronized static BalanceUtils getInstance(){
        return utils==null?utils = new BalanceUtils():utils;
    }

    public BluetoothSocket getSocket() {
        return socket;
    }

    public void setSocket(BluetoothSocket socket) {
        this.socket = socket;
        if(task!=null)
            task.setStart(false);
    }

    public void readData(Context context,OnDataListener listener) throws IOException {
        if(socket==null)
            throw new NullPointerException("BluetoothSocket is not allowed here!!!");
        this.onDataListener = listener;
        handler = new Handler(context.getMainLooper());
        if(task!=null)
            task.setStart(false);
        task = null;
        task = new ReadTask(true,socket.getInputStream());
        new Thread(task).start();
    }

    public void stop(){
        if(task!=null)
            task.setStart(false);
    }

    public void release() throws IOException {
        if(task!=null)
            task.setStart(false);
        if(socket!=null)
            socket.close();
        socket = null;
        utils = null;
    }

    public interface OnDataListener{
        void onData(String data);
    }

    class ReadTask implements Runnable{

        private boolean isStart;
        private InputStream is;
        private byte[] buffer = new byte[1024];
        private int index = 0;
        private String final_data="";
        //是否是第一次读取数据
        private boolean isFirst = true;
        ReadTask(boolean isStart,InputStream is){
            this.isStart = isStart;
            this.is = is;
        }

        public boolean isStart() {
            return isStart;
        }

        void setStart(boolean start) {
            isStart = start;
        }

        @Override
        public void run() {
            final_data = "";
            while (true){
                if(!isStart){
                    break;
                }
                try {
                    int len = is.read(buffer);
                    String data = new String(buffer,0,len);
                    LogUtils.e("xuxiwu，原始数据",data);
                    if(isFirst){
                        data = "";
                        isFirst = false;
                    }
                    final_data += data.replace("\\s","");
                    final_data = final_data.trim().replaceAll("\\r\\n", "")
                            .replaceAll("\\+0.00kg", "");
                    LogUtils.e("xuxiwu",final_data);
                    if (final_data.length() > 70) {
                        final String[] date = final_data.split("=");
                        for (index = 0; index < date.length; index++) {
                            int count = 0;
                            for (int y = 0; y < date.length; y++) {
                                if (date[index].equals(date[y])) {
                                    count++;
                                }
                            }
                            if (count > 5) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(onDataListener!=null)
                                            onDataListener.onData(new StringBuilder(date[index]).reverse().toString());
                                        final_data = "";
                                        isFirst = true;
                                    }
                                });
                                isStart = false;
                                return;
                            }
                        }
                    }
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

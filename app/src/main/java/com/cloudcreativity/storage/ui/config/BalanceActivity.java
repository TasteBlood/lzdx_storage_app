package com.cloudcreativity.storage.ui.config;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.base.BaseActivity;
import com.cloudcreativity.storage.base.BaseBindingRecyclerViewAdapter;
import com.cloudcreativity.storage.databinding.ItemLayoutBluetoothBinding;
import com.cloudcreativity.storage.utils.BalanceUtils;
import com.cloudcreativity.storage.utils.SPUtils;
import com.cloudcreativity.storage.utils.ToastUtils;
import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.Set;
import java.util.UUID;

/**
 * 电子秤配置页面
 */
public class BalanceActivity extends BaseActivity {

    private BluetoothAdapter bluetoothAdapter;
    private BaseBindingRecyclerViewAdapter<BluetoothDevice, ItemLayoutBluetoothBinding> adapter;
    private TextView tv_connect;
    private View layout_connect;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    //初始化view
    private void initView() {
        setContentView(R.layout.activity_balance);
        tv_connect = findViewById(R.id.tv_connect);
        layout_connect = findViewById(R.id.layout_connect);
        final TwinklingRefreshLayout refresh = findViewById(R.id.refresh_balance);
        refresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                if(bluetoothAdapter.isEnabled()){
                    Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();
                    adapter.getItems().clear();
                    adapter.getItems().addAll(bondedDevices);
                    refresh.finishRefreshing();
                }
            }
        });
        Toolbar tlb_bar = findViewById(R.id.tlb_balance);
        tlb_bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        hideLayout();
        initBluetooth();
    }

    private void showLayout(String name){
        layout_connect.setVisibility(View.VISIBLE);
        tv_connect.setText(name);
    }
    private void hideLayout(){
        layout_connect.setVisibility(View.GONE);
        tv_connect.setText("");
    }

    //init bluetooth
    private void initBluetooth(){
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(!bluetoothAdapter.isEnabled()){
            bluetoothAdapter.enable();
        }
        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();
        //ToastUtils.showShortToast(this,"大小"+bondedDevices.size());
        adapter = new BaseBindingRecyclerViewAdapter<BluetoothDevice, ItemLayoutBluetoothBinding>(this) {
            @Override
            protected int getLayoutResId(int viewType) {
                return R.layout.item_layout_bluetooth;
            }

            @Override
            protected void onBindItem(ItemLayoutBluetoothBinding binding, final BluetoothDevice item, int position) {
                binding.setItem(item);
                if(item.getBondState()==BluetoothDevice.BOND_BONDED){
                    binding.tvState.setText("已配对");
                }else if(item.getBondState()==BluetoothDevice.BOND_NONE){
                    binding.tvState.setText("未配对");
                }

                binding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //开始连接
                        startConnect(item);
                    }
                });
            }
        };
        RecyclerView rcv_balance = findViewById(R.id.rcv_balance);
        rcv_balance.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rcv_balance.setAdapter(adapter);
        adapter.getItems().addAll(bondedDevices);

        //判断是否连接，回显信息;
        BluetoothDevice device = new Gson().fromJson(SPUtils.get().getString("connect_device", ""),
                BluetoothDevice.class);
        if(BalanceUtils.getInstance().getSocket()!=null&&BalanceUtils.getInstance().getSocket().isConnected()){
            if(device!=null){
                //显示
                showLayout(device.getName());
            }
        }
    }

    private Thread connectThread;
    private void startConnect(final BluetoothDevice device){
        if(connectThread!=null)
            connectThread.interrupt();
        showProgress("连接中");
        connectThread = new Thread(){
            @Override
            public void run() {
                try{
                    //showProgress("连接中");
                    if(bluetoothAdapter==null)
                        return;
                    if(bluetoothAdapter.isEnabled()&&bluetoothAdapter.isDiscovering()){
                        bluetoothAdapter.cancelDiscovery();
                    }
                    //如果有设备连接，先将设备断开连接
                    if(BalanceUtils.getInstance().getSocket()!=null&&BalanceUtils.getInstance().getSocket().isConnected()){
                        BalanceUtils.getInstance().getSocket().close();
                    }
                    BluetoothSocket socket = device.createRfcommSocketToServiceRecord(UUID.fromString(BalanceUtils.MY_UUID));
                    //showProgress("连接中");
                    if(!socket.isConnected()){
                        socket.connect();
                    }
                    BalanceUtils.getInstance().setSocket(socket);
                    SPUtils.get().putString("connect_device",new Gson().toJson(device));
                    //dismissProgress();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showLayout(device.getName());
                            dismissProgress();
                            //showRequestErrorMessage("连接成功");
                        }
                    });
                }catch (Exception e){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dismissProgress();
                            showRequestErrorMessage("连接失败");
                            hideLayout();
                        }
                    });
                    e.printStackTrace();
                    //showRequestErrorMessage("连接失败");
                }
            }
        };
        connectThread.start();
    }
    public void onStopClick(View view){
        try{
            if(BalanceUtils.getInstance().getSocket()!=null&&BalanceUtils.getInstance().getSocket().isConnected()){
                BalanceUtils.getInstance().release();
            }
            //ToastUtils.showShortToast(this,"连接已关闭");
            SPUtils.get().putString("connect_device","");
            hideLayout();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onReadClick(View v){
        try{
            BalanceUtils.getInstance().readData(this, new BalanceUtils.OnDataListener() {
                @Override
                public void onData(String data) {
                    ToastUtils.showShortToast(BalanceActivity.this,String.valueOf(Float.parseFloat(data)));
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

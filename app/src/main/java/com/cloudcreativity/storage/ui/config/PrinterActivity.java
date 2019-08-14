package com.cloudcreativity.storage.ui.config;

import android.bluetooth.BluetoothAdapter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.base.BaseActivity;
import com.cloudcreativity.storage.base.BaseBindingRecyclerViewAdapter;
import com.cloudcreativity.storage.databinding.ActivityPrinterBinding;
import com.cloudcreativity.storage.databinding.ItemLayoutPrinterBinding;
import com.cloudcreativity.storage.utils.PrinterUtils;
import com.dothantech.printer.IDzPrinter;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

/**
 * All Rights Reserved By CloudCreativity Tech.
 *
 * @author : created by Xu Xiwu
 * date-time: 2019/8/6 11:42
 * e-mail: xxw0701@sina.com
 */
public class PrinterActivity extends BaseActivity {

    private ActivityPrinterBinding binding;
    private BaseBindingRecyclerViewAdapter<IDzPrinter.PrinterAddress, ItemLayoutPrinterBinding> adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(!bluetoothAdapter.isEnabled()){
            bluetoothAdapter.enable();
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_printer);
        binding.tlbPrinter.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.btnDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //断开连接
                PrinterUtils.getInstance().disconnect();
                binding.llPanel.setVisibility(View.GONE);
            }
        });

        binding.btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打印测试页
                if(!IDzPrinter.PrinterState.Disconnected.equals(PrinterUtils.getInstance().getLPAPI().getPrinterState())){
                    if(!PrinterUtils.getInstance().printQRCode("http://www.baidu.com")){
                        showRequestErrorMessage("打印失败");
                    }
                }else{
                    showRequestErrorMessage("打印机未连接");
                }
            }
        });

        adapter = new BaseBindingRecyclerViewAdapter<IDzPrinter.PrinterAddress, ItemLayoutPrinterBinding>(this) {
            @Override
            protected int getLayoutResId(int viewType) {
                return R.layout.item_layout_printer;
            }

            @Override
            protected void onBindItem(ItemLayoutPrinterBinding binding, final IDzPrinter.PrinterAddress item, int position) {
                binding.setItem(item);
                binding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PrinterUtils.getInstance().connect(item);
                    }
                });
            }
        };

        adapter.getItems().addAll(PrinterUtils.getInstance().getAllPrinters());

        binding.refreshPrinter.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                adapter.getItems().clear();
                adapter.getItems().addAll(PrinterUtils.getInstance().getAllPrinters());
                binding.refreshPrinter.finishRefreshing();
            }
        });

        binding.rcvPrinter.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        binding.rcvPrinter.setAdapter(adapter);


        PrinterUtils.getInstance().setOnConnectListenr(new PrinterUtils.onConnectListener() {
            @Override
            public void onConnecting() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showProgress("连接中");
                    }
                });
            }

            @Override
            public void onConnectSuccess(final IDzPrinter.PrinterAddress address) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissProgress();
                        binding.tvConnect.setText(address.shownName);
                        binding.llPanel.setVisibility(View.VISIBLE);
                    }
                });
            }

            @Override
            public void onConnectError() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissProgress();
                        showRequestErrorMessage("连接失败");
                    }
                });
            }

            @Override
            public void onDisconnect() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissProgress();
                        binding.tvConnect.setText("");
                        binding.llPanel.setVisibility(View.GONE);
                    }
                });
            }
        });

        if(!IDzPrinter.PrinterState.Disconnected.equals(PrinterUtils.getInstance().getLPAPI().getPrinterState())){
            binding.llPanel.setVisibility(View.VISIBLE);
            binding.tvConnect.setText(PrinterUtils.getInstance().getLPAPI().getPrinterInfo().deviceName);
        }
    }
}

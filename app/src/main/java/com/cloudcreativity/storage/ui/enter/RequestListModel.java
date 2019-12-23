package com.cloudcreativity.storage.ui.enter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.base.BaseBindingRecyclerViewAdapter;
import com.cloudcreativity.storage.base.BaseDialogImpl;
import com.cloudcreativity.storage.base.BaseModel;
import com.cloudcreativity.storage.base.BaseResult;
import com.cloudcreativity.storage.databinding.FragmentRequestListBinding;
import com.cloudcreativity.storage.databinding.ItemLayoutBuyOrderEnterBinding;
import com.cloudcreativity.storage.entity.BaseStringResult;
import com.cloudcreativity.storage.entity.BuyOrder;
import com.cloudcreativity.storage.entity.UserEntity;
import com.cloudcreativity.storage.utils.DefaultObserver;
import com.cloudcreativity.storage.utils.HttpUtils;
import com.cloudcreativity.storage.utils.LogUtils;
import com.cloudcreativity.storage.utils.SPUtils;
import com.cloudcreativity.storage.utils.ToastUtils;
import com.duke.dfileselector.activity.DefaultSelectorActivity;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.io.File;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RequestListModel extends BaseModel<Activity, FragmentRequestListBinding> {

    private int pageNum = 1;

    private int size = 20;

    private int oid;

    public BaseBindingRecyclerViewAdapter<BuyOrder.Entity, ItemLayoutBuyOrderEnterBinding> adapter;

    RequestListModel(Activity context, FragmentRequestListBinding binding, BaseDialogImpl baseDialog) {
        super(context, binding, baseDialog);
    }

    @Override
    protected void initView() {
        binding.refreshRequestList.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                pageNum = 1;
                loadData(pageNum, size);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {

            }
        });

        binding.rcvRequestList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    protected void initData() {
        adapter = new BaseBindingRecyclerViewAdapter<BuyOrder.Entity, ItemLayoutBuyOrderEnterBinding>(context) {
            @Override
            protected int getLayoutResId(int viewType) {
                return R.layout.item_layout_buy_order_enter;
            }

            @Override
            protected void onBindItem(ItemLayoutBuyOrderEnterBinding binding, final BuyOrder.Entity item, int position) {
                binding.setItem(item);
                binding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(TextUtils.isEmpty(item.getFileUrl())){
                            new AlertDialog.Builder(context)
                                    .setTitle("提示")
                                    .setMessage("你确定本次采购不添加合同？")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(context, EnterGoodsListActivity.class);
                                            intent.putExtra("order", item);
                                            context.startActivity(intent);
                                        }
                                    })
                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .show();
                        }else{
                            Intent intent = new Intent(context, EnterGoodsListActivity.class);
                            intent.putExtra("order", item);
                            context.startActivity(intent);
                        }
                    }
                });

                binding.tvContract.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        oid = item.getId();
                        DefaultSelectorActivity.startActivity(context);
                    }
                });
            }
        };

        binding.refreshRequestList.startRefresh();

    }

    private void loadData(int page, int size) {
        UserEntity.Entity user = SPUtils.get().getUser();
        HttpUtils.getInstance().getBoughtOrders(page, size, 3, 1, 2,2, user.getStoreId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BuyOrder>(getBaseDialog()) {
                    @Override
                    public void onSuccess(BuyOrder buyOrder) {
                        binding.refreshRequestList.finishRefreshing();
                        adapter.getItems().clear();
                        if (buyOrder.getInfo().getList().isEmpty()) {
                            binding.noData.setVisibility(View.VISIBLE);
                        } else {
                            binding.noData.setVisibility(View.GONE);
                            adapter.getItems().addAll(buyOrder.getInfo().getList());
                        }

                    }

                    @Override
                    public void onFail(ExceptionReason msg) {
                        binding.refreshRequestList.finishRefreshing();
                    }
                });
    }

    void upload(String path){
        //LogUtils.e("xuxiwu",path);
        if(TextUtils.isEmpty(path)) return;
        if(!path.endsWith(".doc") && !path.endsWith(".pdf") && !path.endsWith(".docx")){
            ToastUtils.showShortToast(context,"文件格式仅能为doc、pdf、docx");
            return;
        }
        File file = new File(path);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        HttpUtils.getInstance().uploadFile(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BaseStringResult>(getBaseDialog(),true) {
                    @Override
                    public void onSuccess(BaseStringResult t) {
                        //ToastUtils.showShortToast(context,t.getInfo());
                        HttpUtils.getInstance().uploadContract(oid,t.getInfo())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new DefaultObserver<BaseStringResult>(getBaseDialog(),true) {
                                    @Override
                                    public void onSuccess(BaseStringResult t) {
                                        ToastUtils.showShortToast(context,"操作成功");
                                    }
                                });
                    }

                    @Override
                    public void onFail(ExceptionReason msg) {
                        ToastUtils.showShortToast(context,"文件上传失败");
                    }
                });



    }
}

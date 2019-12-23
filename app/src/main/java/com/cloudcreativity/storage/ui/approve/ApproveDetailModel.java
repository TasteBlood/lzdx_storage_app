package com.cloudcreativity.storage.ui.approve;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.databinding.ObservableField;
import android.graphics.Rect;
import android.view.View;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.base.BaseActivity;
import com.cloudcreativity.storage.base.BaseDialogImpl;
import com.cloudcreativity.storage.base.BaseModel;
import com.cloudcreativity.storage.base.BaseResult;
import com.cloudcreativity.storage.base.ImageLoader;
import com.cloudcreativity.storage.base.ImageLookActivity;
import com.cloudcreativity.storage.databinding.ActivityApproveDetailBinding;
import com.cloudcreativity.storage.entity.ApproveWrapper;
import com.cloudcreativity.storage.utils.ConfirmDialog;
import com.cloudcreativity.storage.utils.DefaultObserver;
import com.cloudcreativity.storage.utils.HttpUtils;
import com.cloudcreativity.storage.utils.SPUtils;
import com.cloudcreativity.storage.utils.ToastUtils;
import com.previewlibrary.GPreviewActivity;
import com.previewlibrary.GPreviewBuilder;
import com.previewlibrary.ZoomMediaLoader;
import com.previewlibrary.enitity.ThumbViewInfo;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * All Rights Reserved By CloudCreativity Tech.
 *
 * @author : created by Xu Xiwu
 * date-time: 2019/12/6 11:03
 * e-mail: xxw0701@sina.com
 */
public class ApproveDetailModel extends BaseModel<BaseActivity, ActivityApproveDetailBinding> {

    private int oid;
    public ObservableField<ApproveWrapper.Wrapper> entity = new ObservableField<>();
    ApproveDetailModel(int oid,final BaseActivity context, ActivityApproveDetailBinding binding, BaseDialogImpl baseDialog) {
        super(context, binding, baseDialog);
        // 初始化 预览

        ZoomMediaLoader.getInstance().init(new ImageLoader());


        this.oid = oid;
        binding.tlbApproveDetail.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                context.finish();
            }

        });
        HttpUtils.getInstance().getApproveDetail(oid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<ApproveWrapper>(getBaseDialog(),true) {
                    @Override
                    public void onSuccess(ApproveWrapper t) {
                        entity.set(t.getInfo());
                        initState(t.getInfo());
                    }
                });
    }

    private void initState(ApproveWrapper.Wrapper info) {

        binding.btnNo.setVisibility(View.GONE);
        binding.btnYes.setVisibility(View.GONE);

        int accountId = SPUtils.get().getUser().getAccountId();

        //中心负责人
        if(info.getFunds().getState2()==1){
            binding.tvState2.setText("(待审核)");
            binding.tvReason2.setText("");

            if(info.getFunds().getState1()==0 && info.getFunds().getAccount_id2()==accountId){
                binding.btnYes.setVisibility(View.VISIBLE);
                binding.btnNo.setVisibility(View.VISIBLE);
            }

        }else if(info.getFunds().getState2()==0){
            binding.tvState2.setText("(通过)");
            binding.tvReason2.setText("");
        }else if(info.getFunds().getState2()==-1){
            binding.tvState2.setText("(被驳回)");
            binding.tvReason2.setText(info.getFunds().getReason2());

            if(info.getFunds().getAccount_id2()==accountId){
                binding.btnYes.setVisibility(View.VISIBLE);
                binding.btnNo.setVisibility(View.VISIBLE);
            }
        }




        //主管领导
        if(info.getFunds().getState3()==1){
            binding.tvState3.setText("(待审核)");
            binding.tvReason3.setText("");

            if(info.getFunds().getState2()==0 && info.getFunds().getAccount_id3()==accountId){
                binding.btnYes.setVisibility(View.VISIBLE);
                binding.btnNo.setVisibility(View.VISIBLE);
            }

        }else if(info.getFunds().getState3()==0){
            binding.tvState3.setText("(通过)");
            binding.tvReason3.setText("");
        }else if(info.getFunds().getState3()==-1){
            binding.tvState3.setText("(被驳回)");
            binding.tvReason3.setText(info.getFunds().getReason3());

            if(info.getFunds().getAccount_id3()==accountId){
                binding.btnYes.setVisibility(View.VISIBLE);
                binding.btnNo.setVisibility(View.VISIBLE);
            }
        }




        //财务审核
        if(info.getFunds().getState4()==1){
            binding.tvState4.setText("(待审核)");
            binding.tvReason4.setText("");

            if(info.getFunds().getState3()==0 && info.getFunds().getAccount_id4()==accountId){
                binding.btnYes.setVisibility(View.VISIBLE);
                binding.btnNo.setVisibility(View.VISIBLE);
            }
        }else if(info.getFunds().getState4()==0){
            binding.tvState4.setText("(通过)");
            binding.tvReason4.setText("");
        }else if(info.getFunds().getState4()==-1){
            binding.tvState4.setText("(被驳回)");
            binding.tvReason4.setText(info.getFunds().getReason4());

            if(info.getFunds().getAccount_id4()==accountId){
                binding.btnYes.setVisibility(View.VISIBLE);
                binding.btnNo.setVisibility(View.VISIBLE);
            }
        }




        //部领导
        if(info.getFunds().getState5()==1){
            binding.tvState5.setText("(待审核)");
            binding.tvReason5.setText("");

            if(info.getFunds().getState4()==0 && info.getFunds().getAccount_id5()==accountId){
                binding.btnYes.setVisibility(View.VISIBLE);
                binding.btnNo.setVisibility(View.VISIBLE);
            }

        }else if(info.getFunds().getState5()==0){
            binding.tvState5.setText("(通过)");
            binding.tvReason5.setText("");
        }else if(info.getFunds().getState5()==-1){
            binding.tvState5.setText("(被驳回)");
            binding.tvReason5.setText(info.getFunds().getReason2());

            if(info.getFunds().getAccount_id5()==accountId){
                binding.btnYes.setVisibility(View.VISIBLE);
                binding.btnNo.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    public void onOk(){
        final int accountId = SPUtils.get().getUser().getAccountId();
        new AlertDialog.Builder(context).setTitle("提示")
                .setMessage("确定审批通过吗?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(entity.get().getFunds().getAccount_id2()==accountId){
                            HttpUtils.getInstance().handState2(0,entity.get().getOrderAttach().getId(),"")
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new DefaultObserver<BaseResult>(getBaseDialog(),true) {
                                        @Override
                                        public void onSuccess(BaseResult t) {
                                            context.finish();
                                            EventBus.getDefault().post("refresh_approve");
                                        }
                                    });
                        }else if(entity.get().getFunds().getAccount_id3()==accountId){
                            HttpUtils.getInstance().handState3(0,entity.get().getOrderAttach().getId(),"")
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new DefaultObserver<BaseResult>(getBaseDialog(),true) {
                                        @Override
                                        public void onSuccess(BaseResult t) {
                                            context.finish();
                                            EventBus.getDefault().post("refresh_approve");
                                        }
                                    });
                        }else if(entity.get().getFunds().getAccount_id4()==accountId){
                            HttpUtils.getInstance().handState4(0,entity.get().getOrderAttach().getId(),"")
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new DefaultObserver<BaseResult>(getBaseDialog(),true) {
                                        @Override
                                        public void onSuccess(BaseResult t) {
                                            context.finish();
                                            EventBus.getDefault().post("refresh_approve");
                                        }
                                    });
                        }else if(entity.get().getFunds().getAccount_id5()==accountId){
                            HttpUtils.getInstance().handState5(0,entity.get().getOrderAttach().getId(),"")
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new DefaultObserver<BaseResult>(getBaseDialog(),true) {
                                        @Override
                                        public void onSuccess(BaseResult t) {
                                            context.finish();
                                            EventBus.getDefault().post("refresh_approve");
                                        }
                                    });
                        }
                    }
                }).show();
    }

    public void onCancel(){
        ConfirmDialog dialog = new ConfirmDialog(context, R.style.myProgressDialogStyle);
        dialog.setOnConfirmListener(new ConfirmDialog.OnConfirmListener() {
            @Override
            public void onConfirm(String msg) {
                int accountId = SPUtils.get().getUser().getAccountId();
                if(entity.get().getFunds().getAccount_id2()==accountId){
                    HttpUtils.getInstance().handState2(-1,entity.get().getOrderAttach().getId(),msg)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new DefaultObserver<BaseResult>(getBaseDialog(),true) {
                                @Override
                                public void onSuccess(BaseResult t) {
                                    context.finish();
                                    EventBus.getDefault().post("refresh_approve");
                                }
                            });
                }else if(entity.get().getFunds().getAccount_id3()==accountId){
                    HttpUtils.getInstance().handState3(-1,entity.get().getOrderAttach().getId(),msg)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new DefaultObserver<BaseResult>(getBaseDialog(),true) {
                                @Override
                                public void onSuccess(BaseResult t) {
                                    context.finish();
                                    EventBus.getDefault().post("refresh_approve");
                                }
                            });
                }else if(entity.get().getFunds().getAccount_id4()==accountId){
                    HttpUtils.getInstance().handState4(-1,entity.get().getOrderAttach().getId(),msg)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new DefaultObserver<BaseResult>(getBaseDialog(),true) {
                                @Override
                                public void onSuccess(BaseResult t) {
                                    context.finish();
                                    EventBus.getDefault().post("refresh_approve");
                                }
                            });
                }else if(entity.get().getFunds().getAccount_id5()==accountId){
                    HttpUtils.getInstance().handState5(-1,entity.get().getOrderAttach().getId(),msg)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new DefaultObserver<BaseResult>(getBaseDialog(),true) {
                                @Override
                                public void onSuccess(BaseResult t) {
                                    context.finish();
                                    EventBus.getDefault().post("refresh_approve");
                                }
                            });
                }
            }
        });

        dialog.show();


    }

    public void openDoc(){
        if(entity.get()!=null){
            if(entity.get().getOrderAttach().getOrderNumber()>0){
                //打开预览
                String[] strings = entity.get().getOrderAttach().getUrl().split(",");
                ArrayList<ThumbViewInfo> thumbs = new ArrayList<>();
                ThumbViewInfo info;
                thumbs.clear();
                for(int i=0;i<strings.length;i++){
                    Rect bounds = new Rect();
                    info = new ThumbViewInfo(strings[i]);
                    info.setBounds(bounds);
                    thumbs.add(info);
                }

                GPreviewBuilder.from(context)
                        .to(ImageLookActivity.class)
                        .setData(thumbs)
                        .setCurrentIndex(0)
                        .setSingleFling(true)
                        .setType(GPreviewBuilder.IndicatorType.Number)
                        .start();
            }else{
                ToastUtils.showShortToast(context,"无附件");
            }
        }
    }
}

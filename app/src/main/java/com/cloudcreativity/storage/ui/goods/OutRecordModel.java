package com.cloudcreativity.storage.ui.goods;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.base.BaseActivity;
import com.cloudcreativity.storage.base.BaseBindingRecyclerViewAdapter;
import com.cloudcreativity.storage.base.BaseDialogImpl;
import com.cloudcreativity.storage.base.BaseModel;
import com.cloudcreativity.storage.databinding.ActivityOutRecordBinding;
import com.cloudcreativity.storage.databinding.ItemLayoutOutRecordBinding;
import com.cloudcreativity.storage.entity.OutRecord;

public class OutRecordModel extends BaseModel<BaseActivity, ActivityOutRecordBinding> {

    public BaseBindingRecyclerViewAdapter<OutRecord.Entity, ItemLayoutOutRecordBinding> adapter;

    OutRecordModel(BaseActivity context, ActivityOutRecordBinding binding, BaseDialogImpl baseDialog) {
        super(context, binding, baseDialog);
    }

    @Override
    protected void initView() {
        binding.tlbOutRecord.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.finish();
            }
        });
        binding.rcvOutRecord.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
    }

    @Override
    protected void initData() {
        adapter = new BaseBindingRecyclerViewAdapter<OutRecord.Entity, ItemLayoutOutRecordBinding>(context) {
            @Override
            protected int getLayoutResId(int viewType) {
                return R.layout.item_layout_out_record;
            }

            @Override
            protected void onBindItem(ItemLayoutOutRecordBinding binding, OutRecord.Entity item, int position) {
                binding.setItem(item);
            }
        };

        for(int i=0;i<20;i++){
            adapter.getItems().add(new OutRecord.Entity());
        }
    }
}

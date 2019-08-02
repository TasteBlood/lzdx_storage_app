package com.cloudcreativity.storage.ui.goods;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.base.BaseActivity;
import com.cloudcreativity.storage.base.BaseBindingRecyclerViewAdapter;
import com.cloudcreativity.storage.base.BaseDialogImpl;
import com.cloudcreativity.storage.base.BaseModel;
import com.cloudcreativity.storage.databinding.ActivityEnterRecordBinding;
import com.cloudcreativity.storage.databinding.ItemLayoutEnterRecordBinding;
import com.cloudcreativity.storage.entity.EnterRecord;

public class EnterRecordModel extends BaseModel<BaseActivity, ActivityEnterRecordBinding> {

    public BaseBindingRecyclerViewAdapter<EnterRecord.Entity, ItemLayoutEnterRecordBinding> adapter;

    EnterRecordModel(BaseActivity context, ActivityEnterRecordBinding binding, BaseDialogImpl baseDialog) {
        super(context, binding, baseDialog);
    }

    @Override
    protected void initView() {
        binding.tlbEnterRecord.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.finish();
            }
        });

        binding.rcvEnterRecord.setLayoutManager(new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL,false));
    }

    @Override
    protected void initData() {
        adapter = new BaseBindingRecyclerViewAdapter<EnterRecord.Entity, ItemLayoutEnterRecordBinding>(context) {
            @Override
            protected int getLayoutResId(int viewType) {
                return R.layout.item_layout_enter_record;
            }

            @Override
            protected void onBindItem(ItemLayoutEnterRecordBinding binding, EnterRecord.Entity item, int position) {
                binding.setItem(item);
            }
        };

        for(int i=0;i<20;i++){
            adapter.getItems().add(new EnterRecord.Entity());
        }
    }
}

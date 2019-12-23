package com.cloudcreativity.storage.utils;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.base.BaseBindingRecyclerViewAdapter;
import com.cloudcreativity.storage.databinding.ItemLayoutDialogApplyOutGoodsBinding;
import com.cloudcreativity.storage.databinding.LayoutDialogApplyOutGoodsBinding;
import com.cloudcreativity.storage.entity.StoreGoods;

import java.util.List;

/**
 * All Rights Reserved By CloudCreativity Tech.
 *
 * @author : created by Xu Xiwu
 * date-time: 2019/9/23 14:09
 * e-mail: xxw0701@sina.com
 */
public class ApplyOutGoodsUtils extends Dialog {

    private LayoutDialogApplyOutGoodsBinding binding;
    public BaseBindingRecyclerViewAdapter<StoreGoods.Entity, ItemLayoutDialogApplyOutGoodsBinding> adapter;
    public ObservableField<String> memo = new ObservableField<>();
    private OnSubmitListener onSubmitListener;


    public ApplyOutGoodsUtils(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_dialog_apply_out_goods,null,false);
        binding.setUtils(this);
        setContentView(binding.getRoot());
        Window window = getWindow();
        assert window != null;
        window.setGravity(Gravity.BOTTOM);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        window.getAttributes().width  = displayMetrics.widthPixels;
        window.getAttributes().height = displayMetrics.heightPixels/3*2;

        adapter = new BaseBindingRecyclerViewAdapter<StoreGoods.Entity, ItemLayoutDialogApplyOutGoodsBinding>(context) {
            @Override
            protected int getLayoutResId(int viewType) {
                return R.layout.item_layout_dialog_apply_out_goods;
            }

            @Override
            protected void onBindItem(ItemLayoutDialogApplyOutGoodsBinding binding, final StoreGoods.Entity item, final int position) {
                binding.setItem(item);

                binding.tvMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        item.setNumber(item.getNumber()-1);
                        if(item.getNumber()<=0){
                            adapter.getItems().remove(position);
                            adapter.notifyDataSetChanged();
                        }else{
                            adapter.notifyDataSetChanged();
                        }
                    }
                });

                binding.tvPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        item.setNumber(item.getNumber()+1);
                        if(item.getNumber()>=item.getStock()){
                            item.setNumber(item.getStock());
                        }
                        adapter.notifyDataSetChanged();
                    }
                });

                binding.tvStock.setText("库存:"+item.getStock());
            }
        };

        binding.rcvApplyOut.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
    }

    public void setOnSubmitListener(OnSubmitListener onSubmitListener) {
        this.onSubmitListener = onSubmitListener;
    }

    public void onApply(){
        if(adapter.getItems()!=null&&adapter.getItems().size()>0){
            for(int i=0;i<adapter.getItems().size();i++){
                if(adapter.getItems().get(i).getNumber()<=0){
                    ToastUtils.showShortToast(getContext(),"数量不可为0");
                    return;
                }
            }
            if(onSubmitListener!=null){
                onSubmitListener.onSubmit(memo.get(),adapter.getItems());
            }
        }
        dismiss();
    }

    public void clear(){
        adapter.getItems().clear();
    }

    public void onMemoClick(){
        if(binding.llMemo.getVisibility()==View.VISIBLE){
            binding.llMemo.setVisibility(View.GONE);
        }else{
            binding.llMemo.setVisibility(View.VISIBLE);
        }

    }

    public void pushGoods(StoreGoods.Entity goods){
        for(StoreGoods.Entity entity:this.adapter.getItems()){
            if(entity.getId()==goods.getId()){
                //相同商品，修改数量就行
                if(entity.getNumber()+1>=goods.getStock())
                    entity.setNumber(goods.getStock());
                else entity.setNumber(entity.getNumber()+1);
                this.adapter.notifyDataSetChanged();
                return;
            }
        }
        goods.setNumber(0);
        this.adapter.getItems().add(goods);
    }

    public interface OnSubmitListener{
        public void onSubmit(String memo, List<StoreGoods.Entity> goods);
    }
}

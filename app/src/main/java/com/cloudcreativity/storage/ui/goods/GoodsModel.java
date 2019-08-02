package com.cloudcreativity.storage.ui.goods;

import android.content.Intent;
import android.databinding.ObservableField;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.base.BaseActivity;
import com.cloudcreativity.storage.base.BaseBindingRecyclerViewAdapter;
import com.cloudcreativity.storage.base.BaseDialogImpl;
import com.cloudcreativity.storage.base.BaseModel;
import com.cloudcreativity.storage.databinding.ActivityGoodsBinding;
import com.cloudcreativity.storage.databinding.ItemLayoutGoodsBinding;
import com.cloudcreativity.storage.entity.Category;
import com.cloudcreativity.storage.entity.Goods;
import com.cloudcreativity.storage.utils.CategoryWindowUtils;
import com.cloudcreativity.storage.utils.QrCodeDialogUtils;
import com.cloudcreativity.storage.utils.ToastUtils;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;

import java.util.ArrayList;
import java.util.List;

public class GoodsModel extends BaseModel<BaseActivity, ActivityGoodsBinding> {

    public BaseBindingRecyclerViewAdapter<Goods.Entity, ItemLayoutGoodsBinding> adapter;
    private CategoryWindowUtils popupWindow;
    public ObservableField<String> selectCategory;
    GoodsModel(BaseActivity context, ActivityGoodsBinding binding, BaseDialogImpl baseDialog) {
        super(context, binding, baseDialog);
    }

    @Override
    protected void initView() {
        selectCategory = new ObservableField<>();
        selectCategory.set("类别筛选");
        binding.tlbGoods.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.finish();
            }
        });

        binding.rcvGoods.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        binding.refreshGoods.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onFinishRefresh() {

            }

            @Override
            public void onFinishLoadMore() {

            }
        });
    }

    @Override
    protected void initData() {
        adapter = new BaseBindingRecyclerViewAdapter<Goods.Entity, ItemLayoutGoodsBinding>(context) {
            @Override
            protected int getLayoutResId(int viewType) {
                return R.layout.item_layout_goods;
            }

            @Override
            protected void onBindItem(ItemLayoutGoodsBinding binding, Goods.Entity item, int position) {
                binding.setItem(item);

                binding.tvEnter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        context.startActivity(new Intent(context,EnterRecordActivity.class));
                    }
                });

                binding.tvOut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        context.startActivity(new Intent(context,OutRecordActivity.class));
                    }
                });

                binding.tvQrCode.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new QrCodeDialogUtils(context,
                                R.style.myProgressDialogStyle,
                                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1564567206061&di=29c0854ea69e0c3cc615d14e3f42182b&imgtype=0&src=http%3A%2F%2Fmmbiz.qpic.cn%2Fmmbiz_png%2FF0ia7S03R9rBxzF51Uj2rBxS5czVyTRYia2Y018uIsmqsfzAmNJz5w1Z7flQsWjZVdf9z0zoIFOic8j9uR5hxgNQw%2F640%3Fwx_fmt%3Dpng").show();
                    }
                });

                binding.tvCheck.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ToastUtils.showShortToast(context,"盘点了");
                    }
                });
            }
        };
        List<Goods.Entity> entities = new ArrayList<>();
        for(int i=0;i<=10;i++){
            entities.add(new Goods.Entity());
        }
        adapter.getItems().addAll(entities);
    }

    public void onCategoryClick(){
        if(popupWindow!=null&&popupWindow.isShowing()){
            popupWindow.dismiss();
            return;
        }
        if(popupWindow==null){
            popupWindow = new CategoryWindowUtils(context,getBaseDialog());
            popupWindow.setOnChooseListener(new CategoryWindowUtils.OnChooseListener() {
                @Override
                public void onChoose(Category.Entity one, Category.Entity two) {
                    if(one!=null){
                        selectCategory.set(one.getName());
                    }
                    if(two!=null){
                        selectCategory.set(selectCategory.get()+"/"+two.getName());
                    }
                }

                @Override
                public void onClear() {
                    selectCategory.set("类别筛选");
                }
            });
        }

        popupWindow.show();
    }
}

package com.cloudcreativity.storage.ui.buyPrice;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ArrayAdapter;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.base.BaseActivity;
import com.cloudcreativity.storage.base.BaseBindingRecyclerViewAdapter;
import com.cloudcreativity.storage.base.BaseDialogImpl;
import com.cloudcreativity.storage.base.BaseModel;
import com.cloudcreativity.storage.base.BaseResult;
import com.cloudcreativity.storage.databinding.ActivityPriceBinding;
import com.cloudcreativity.storage.databinding.ItemLayoutPriceOrderItemBinding;
import com.cloudcreativity.storage.entity.BuyGoods;
import com.cloudcreativity.storage.entity.BuyOrder;
import com.cloudcreativity.storage.entity.PriceGoods;
import com.cloudcreativity.storage.entity.Provider;
import com.cloudcreativity.storage.utils.BuyAddPriceUtils;
import com.cloudcreativity.storage.utils.DefaultObserver;
import com.cloudcreativity.storage.utils.HttpUtils;
import com.cloudcreativity.storage.utils.StrUtils;
import com.cloudcreativity.storage.utils.ToastUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PriceModel extends BaseModel<BaseActivity, ActivityPriceBinding> {

    //采购单对象
    public BuyOrder.Entity entity;

    public BaseBindingRecyclerViewAdapter<BuyGoods.Entity, ItemLayoutPriceOrderItemBinding> allAdapter;

    private BuyAddPriceUtils priceUtils;

    PriceModel(BaseActivity context, ActivityPriceBinding binding, BaseDialogImpl baseDialog, BuyOrder.Entity entity) {
        super(context, binding, baseDialog);
        this.entity = entity;
        loadAllGoods();
        priceUtils = new BuyAddPriceUtils(PriceModel.this.context,
                R.style.myProgressDialogStyle,
                PriceModel.this.context);
    }

    @Override
    protected void initView() {
        binding.tlbPrice.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.finish();
            }
        });

        binding.rcvPriceAll.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    protected void initData() {
        allAdapter = new BaseBindingRecyclerViewAdapter<BuyGoods.Entity, ItemLayoutPriceOrderItemBinding>(context) {
            @Override
            protected int getLayoutResId(int viewType) {
                return R.layout.item_layout_price_order_item;
            }

            @Override
            protected void onBindItem(ItemLayoutPriceOrderItemBinding binding, final BuyGoods.Entity item, int position) {
                binding.setItem(item);
                binding.tvPriceNow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        priceUtils.setOnOkListener(new BuyAddPriceUtils.OnOkListener() {
                            @Override
                            public void onOk(Provider.Entity entity, float price) {
                                //处理采价
                                pushPrice(entity,price, item);
                            }
                        });
                        priceUtils.show();
                    }
                });
                binding.tvPriceList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showList(item.getId());
                    }
                });
            }
        };
    }

    //查询采价列表
    private void showList(int id) {
        HttpUtils.getInstance().getPriceList(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<PriceGoods>(getBaseDialog(), true) {
                    @Override
                    public void onSuccess(PriceGoods buyGoods) {
                        if (buyGoods.getInfo().isEmpty()) {
                            ToastUtils.showShortToast(context, "暂无采价信息");
                        } else {
                            String[] list = new String[buyGoods.getInfo().size()];
                            for(int i=0;i<list.length;i++){
                                PriceGoods.Entity entity = buyGoods.getInfo().get(i);
                                String buffer = "供应商:" + entity.getShopName() + "\n" +
                                        "价 格:" + "¥" + StrUtils.get2BitDecimal(entity.getPrice() / 100f);
                                list[i] = buffer;
                            }
                            new AlertDialog.Builder(context)
                                    .setAdapter(new ArrayAdapter<>(context,android.R.layout.simple_list_item_1,list),null)
                                    .setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    })
                                    .show();
                        }
                    }

                    @Override
                    public void onFail(ExceptionReason msg) {

                    }
                });
    }

    void onResult(Provider.Entity entity) {
        this.priceUtils.setProvider(entity);
    }

    private void pushPrice(Provider.Entity provider, float price, BuyGoods.Entity goods) {
        HttpUtils.getInstance().addBuyPriceRecord(goods.getId(),
                provider.getId(),
                Float.valueOf(price * 100).intValue(),
                "",
                entity.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BaseResult>(getBaseDialog(), true) {
                    @Override
                    public void onSuccess(BaseResult baseResult) {
                        //ToastUtils.showShortToast(context, "添加询价成功");
                    }

                    @Override
                    public void onFail(ExceptionReason msg) {

                    }
                });
    }

    private void loadAllGoods() {
        HttpUtils.getInstance().getBuyGoods(entity.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BuyGoods>(getBaseDialog(), true) {
                    @Override
                    public void onSuccess(BuyGoods buyGoods) {
                        allAdapter.getItems().addAll(buyGoods.getInfo().getList());
                    }

                    @Override
                    public void onFail(ExceptionReason msg) {

                    }
                });
    }
}

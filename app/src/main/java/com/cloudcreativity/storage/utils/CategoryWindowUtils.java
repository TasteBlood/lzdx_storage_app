package com.cloudcreativity.storage.utils;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cloudcreativity.storage.R;
import com.cloudcreativity.storage.base.BaseDialogImpl;
import com.cloudcreativity.storage.databinding.LayoutCategoryPopupBinding;
import com.cloudcreativity.storage.entity.Category;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CategoryWindowUtils extends Dialog implements View.OnClickListener {
    private BaseDialogImpl baseDialog;
    private Context context;
    private LayoutCategoryPopupBinding binding;
    private View tv_last_one;
    private View tv_last_two;

    private Category.Entity categoryOne;
    private Category.Entity categoryTwo;

    private OnChooseListener onChooseListener;

    public void setOnChooseListener(OnChooseListener onChooseListener) {
        this.onChooseListener = onChooseListener;
    }

    public CategoryWindowUtils(Context context, BaseDialogImpl baseDialog) {
        super(context);
        this.baseDialog = baseDialog;
        this.context = context;
        initView();
        //loadData();
    }

    private void initView(){
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_category_popup, null, false);
        setContentView(binding.getRoot());
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        setCanceledOnTouchOutside(false);
        setCancelable(true);
        getWindow().getAttributes().width = displayMetrics.widthPixels;
        getWindow().getAttributes().height = displayMetrics.heightPixels/2;
        binding.tvCancel.setOnClickListener(this);
        binding.tvClear.setOnClickListener(this);
        binding.tvOk.setOnClickListener(this);

        loadData();
    }


    private void loadData(){
        HttpUtils.getInstance().getCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<Category>(baseDialog) {
                    @Override
                    public void onSuccess(Category category) {
                        //categories = category.getInfo();
                        final List<Category.Entity> entities = category.getInfo();
                        final String[] one = new String[entities.size()];
                        for(int i=0;i<one.length;i++){
                            one[i] = entities.get(i).getName();
                        }

                        binding.lvFirst.setAdapter(new ArrayAdapter<>(context,R.layout.item_layout_category,
                                R.id.text1,
                                one));

                        binding.lvFirst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                                //首先将字体变为选中状态
                                categoryOne = entities.get(i);
                                categoryTwo = null;
                                if(tv_last_one==view)
                                    return;
                                if(tv_last_one!=null){
                                    TextView text2 = tv_last_one.findViewById(R.id.text1);
                                    text2.setTextColor(context.getResources().getColor(R.color.gray_515151));
                                }
                                TextView textView = view.findViewById(R.id.text1);
                                textView.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                                tv_last_one = view;

                                final String[] two = new String[entities.get(i).getCategory().size()];
                                for(int j=0;j<two.length;j++){
                                    two[j] = entities.get(i).getCategory().get(j).getName();
                                }
                                binding.lvSecond.setAdapter(new ArrayAdapter<>(context,R.layout.item_layout_category,
                                        R.id.text1,
                                        two));
                                binding.lvSecond.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                                        // 首先将字体变为选中状态
                                        categoryTwo = entities.get(i).getCategory().get(pos);
                                        if(tv_last_two==view)
                                            return;
                                        if(tv_last_two!=null){
                                            TextView text2 = tv_last_two.findViewById(R.id.text1);
                                            text2.setTextColor(context.getResources().getColor(R.color.gray_515151));
                                        }
                                        TextView textView = view.findViewById(R.id.text1);
                                        textView.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                                        tv_last_two = view;
                                    }
                                });
                            }
                        });
                    }

                    @Override
                    public void onFail(ExceptionReason msg) {

                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_clear:
                if(tv_last_one!=null){
                    TextView text1 = tv_last_one.findViewById(R.id.text1);
                    text1.setTextColor(context.getResources().getColor(R.color.gray_515151));
                    tv_last_one = null;
                }
                if(tv_last_two!=null){
                    TextView text2 = tv_last_two.findViewById(R.id.text1);
                    text2.setTextColor(context.getResources().getColor(R.color.gray_515151));
                    tv_last_two = null;
                }
                categoryOne = null;
                categoryTwo = null;
                binding.lvSecond.setAdapter(null);
                dismiss();
                if(onChooseListener!=null){
                    onChooseListener.onClear();
                }
                break;
            case R.id.tv_ok:
                dismiss();
                if(onChooseListener!=null){
                    onChooseListener.onChoose(categoryOne,categoryTwo);
                }
                break;
        }
    }

    public interface OnChooseListener{
        public void onChoose(Category.Entity one,Category.Entity two);
        public void onClear();
    }
}

package com.cloudcreativity.storage.ui.enter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.cloudcreativity.storage.base.BaseActivity;
import com.cloudcreativity.storage.base.BaseDialogImpl;
import com.cloudcreativity.storage.base.BaseModel;
import com.cloudcreativity.storage.databinding.ActivityEnterStoreBinding;

import java.util.ArrayList;
import java.util.List;

public class EnterStoreModel extends BaseModel<BaseActivity, ActivityEnterStoreBinding>{

    private String[] titles = {"采购单入库","普通入库"};
    private List<Fragment> fragments;
    public FragmentPagerAdapter adapter;
    EnterStoreModel(BaseActivity context, ActivityEnterStoreBinding binding, BaseDialogImpl baseDialog) {
        super(context, binding, baseDialog);
        fragments = new ArrayList<>();
        fragments.add(new RequestListFragment());
        fragments.add(new CommonEnterFragment());
    }

    @Override
    protected void initView() {
        binding.tlbEnter.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.finish();
            }
        });

        binding.tabEnter.setupWithViewPager(binding.vpEnter);
    }

    @Override
    protected void initData() {

        adapter = new FragmentPagerAdapter(context.getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        };
    }
}

package com.cloudcreativity.storage.ui.out;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import com.cloudcreativity.storage.base.BaseActivity;
import com.cloudcreativity.storage.base.BaseDialogImpl;
import com.cloudcreativity.storage.base.BaseModel;
import com.cloudcreativity.storage.databinding.ActivityOutBinding;

import java.util.ArrayList;
import java.util.List;

public class OutModel extends BaseModel<BaseActivity, ActivityOutBinding>{

    private String[] titles = {"申请单出库","普通出库"};
    private List<Fragment> fragments;
    public FragmentPagerAdapter adapter;

    OutModel(BaseActivity context, ActivityOutBinding binding, BaseDialogImpl baseDialog) {
        super(context, binding, baseDialog);
        fragments = new ArrayList<>();
        fragments.add(new ListOutFragment());
        fragments.add(new CommonOutFragment());
    }

    @Override
    protected void initView() {
        binding.tlbOut.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.finish();
            }
        });
        binding.tabOut.setupWithViewPager(binding.vpOut);
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

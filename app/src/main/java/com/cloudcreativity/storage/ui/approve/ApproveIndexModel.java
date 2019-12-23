package com.cloudcreativity.storage.ui.approve;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cloudcreativity.storage.base.BaseActivity;
import com.cloudcreativity.storage.base.BaseDialogImpl;
import com.cloudcreativity.storage.base.BaseModel;
import com.cloudcreativity.storage.databinding.ActivityApproveIndexBinding;

/**
 * All Rights Reserved By CloudCreativity Tech.
 *
 * @author : created by Xu Xiwu
 * date-time: 2019/12/13 14:49
 * e-mail: xxw0701@sina.com
 */
public class ApproveIndexModel extends BaseModel<BaseActivity, ActivityApproveIndexBinding> {

    ApproveIndexModel(BaseActivity context, ActivityApproveIndexBinding binding, BaseDialogImpl baseDialog) {
        super(context, binding, baseDialog);

        binding.tabApprove.setupWithViewPager(binding.vpApprove);
        String[] titles = {"待审批","审批通过"};
        Fragment[] fragments = {new ApproveUnpassFragment(),new ApprovePassFragment()};
        binding.vpApprove.setOffscreenPageLimit(2);
        binding.vpApprove.setAdapter(new MyPageAdapter(context.getSupportFragmentManager(),titles,fragments));
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    public class MyPageAdapter extends FragmentPagerAdapter{

        private String[] titles;
        private Fragment[] fragments;

        MyPageAdapter(FragmentManager fm, String[] titles, Fragment[] fragments) {
            super(fm);
            this.titles = titles;
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}

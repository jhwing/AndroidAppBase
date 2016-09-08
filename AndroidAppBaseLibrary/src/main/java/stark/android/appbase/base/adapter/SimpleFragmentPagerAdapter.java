package stark.android.appbase.base.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jihongwen on 16/7/28.
 */

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<FragmentTab> fragmentTabs;

    public SimpleFragmentPagerAdapter(FragmentManager fm, List<FragmentTab> fragmentTabs) {
        super(fm);
        this.fragmentTabs = fragmentTabs;
    }

    public SimpleFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        this.fragmentTabs = new ArrayList<>();
    }

    public void setFragmentTabs(List<FragmentTab> fragmentTabs) {
        this.fragmentTabs = fragmentTabs;
        notifyDataSetChanged();
    }

    public List<FragmentTab> getFragmentTabs() {
        return fragmentTabs;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentTabs.get(position).fragment;
    }

    @Override
    public int getCount() {
        return fragmentTabs.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTabs.get(position).tab;
    }

    public static class FragmentTab {
        public String tab;
        public Fragment fragment;

        public FragmentTab(String tab, Fragment fragment) {
            this.tab = tab;
            this.fragment = fragment;
        }
    }
}

package stark.android.appbase.demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import stark.android.appbase.base.BaseToolbarActivity;
import stark.android.appbase.utils.FragmentHelper;
import stark.android.appbase.widget.NavigationLayout;

public class MainActivity extends BaseToolbarActivity implements NavigationLayout.OnNavigationTabClickListener {

    NavigationLayout navigationLayout;

    FragmentHelper fragmentHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar("App Base Easy");
        navigationLayout = findViewById(R.id.navigationLayout);
        navigationLayout.setOnNavigationTabClickListener(this);
        fragmentHelper = new FragmentHelper(new MyFragmentFactory());
        fragmentHelper.changeFragment(HomeFragment.TAG, getSupportFragmentManager());
        navigationLayout.setSelectedView(R.id.tabHome);
    }

    @Override
    public void onTabClick(View v) {
        switch (v.getId()) {
            case R.id.tabHome:
                fragmentHelper.changeFragment(HomeFragment.TAG, getSupportFragmentManager());
                break;
            case R.id.tabMore:
                fragmentHelper.changeFragment(MoreFragment.TAG, getSupportFragmentManager());
                break;
        }
    }

    class MyFragmentFactory implements FragmentHelper.FragmentFactory {

        @Override
        public int fragmentContainer() {
            return R.id.fragment_container;
        }

        @Override
        public Fragment newFragmentByTag(String tag) {
            if (HomeFragment.TAG.equals(tag)) {
                return new HomeFragment();
            } else if (MoreFragment.TAG.equals(tag)) {
                return new MoreFragment();
            }
            return null;
        }
    }
}

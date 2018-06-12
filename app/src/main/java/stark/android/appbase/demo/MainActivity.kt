package stark.android.appbase.demo

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View

import stark.android.appbase.activity.BaseToolbarActivity
import stark.android.appbase.demo.home.HomeFragment
import stark.android.appbase.demo.more.MoreFragment
import stark.android.appbase.fragment.FragmentHelper
import stark.android.appbase.widget.NavigationLayout

class MainActivity : BaseToolbarActivity(), NavigationLayout.OnNavigationTabClickListener {

    lateinit var navigationLayout: NavigationLayout

    lateinit var fragmentHelper: FragmentHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setToolbar("App Base Easy")
        navigationLayout = findViewById(R.id.navigationLayout)
        navigationLayout.setOnNavigationTabClickListener(this)
        fragmentHelper = FragmentHelper(MyFragmentFactory())
        fragmentHelper.resume(savedInstanceState)
        fragmentHelper.changeFragment(HomeFragment.TAG, supportFragmentManager)
        navigationLayout.setSelectedView(R.id.tabHome)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        fragmentHelper.saveState(outState)
    }

    override fun onTabClick(v: View) {
        when (v.id) {
            R.id.tabHome -> fragmentHelper.changeFragment(HomeFragment.TAG, supportFragmentManager)
            R.id.tabMore -> fragmentHelper.changeFragment(MoreFragment.TAG, supportFragmentManager)
        }
    }

    internal inner class MyFragmentFactory : FragmentHelper.FragmentFactory {

        override fun fragmentContainer(): Int {
            return R.id.fragment_container
        }

        override fun newFragmentByTag(tag: String): Fragment? {
            if (HomeFragment.TAG == tag) {
                return HomeFragment()
            } else if (MoreFragment.TAG == tag) {
                return MoreFragment()
            }
            return null
        }
    }
}

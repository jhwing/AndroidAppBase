package stark.android.appbase.viewpager.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import java.util.ArrayList

/**
 * Created by jihongwen on 16/7/28.
 */

class SimpleFragmentPagerAdapter : FragmentPagerAdapter {

    private var fragmentTabs: List<FragmentTab>? = null

    constructor(fm: FragmentManager, fragmentTabs: List<FragmentTab>) : super(fm) {
        this.fragmentTabs = fragmentTabs
    }

    constructor(fm: FragmentManager) : super(fm) {
        this.fragmentTabs = ArrayList()
    }

    fun setFragmentTabs(fragmentTabs: List<FragmentTab>) {
        this.fragmentTabs = fragmentTabs
        notifyDataSetChanged()
    }

    fun getFragmentTabs(): List<FragmentTab>? {
        return fragmentTabs
    }

    override fun getItem(position: Int): Fragment {
        return fragmentTabs!![position].fragment
    }

    override fun getCount(): Int {
        return fragmentTabs!!.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return fragmentTabs!![position].tab
    }

    class FragmentTab(var tab: String, var fragment: Fragment)
}

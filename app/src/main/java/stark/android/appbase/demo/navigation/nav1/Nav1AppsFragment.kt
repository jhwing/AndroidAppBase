package stark.android.appbase.demo.navigation.nav1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import stark.android.appbase.demo.R
import stark.android.appbase.fragment.BaseFragment

class Nav1AppsFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_nav1_apps, container, false)
    }
}
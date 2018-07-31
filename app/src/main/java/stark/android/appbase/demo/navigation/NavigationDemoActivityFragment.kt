package stark.android.appbase.demo.navigation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_navigation_demo.*
import stark.android.appbase.demo.R
import stark.android.appbase.demo.navigation.drag.DragDemoActivity
import stark.android.appbase.demo.navigation.nav1.Nav1Activity
import stark.android.appbase.fragment.BaseFragment

class NavigationDemoActivityFragment : BaseFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_navigation_demo, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationDemo.setOnClickListener {
            startActivity(Intent(activity, Nav1Activity::class.java))
        }
        viewDragDemo.setOnClickListener {
            startActivity(Intent(activity, DragDemoActivity::class.java))
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}
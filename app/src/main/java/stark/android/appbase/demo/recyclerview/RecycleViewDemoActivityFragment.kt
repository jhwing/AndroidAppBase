package stark.android.appbase.demo.recyclerview

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_recycler_demo.*
import stark.android.appbase.demo.R
import stark.android.appbase.demo.recyclerview.demo.MyGridGroupViewActivity
import stark.android.appbase.demo.recyclerview.demo.RecycleGridLayoutManagerDemoActivity
import stark.android.appbase.demo.recyclerview.demo.RecycleLayoutManagerDemoActivity
import stark.android.appbase.fragment.BaseFragment

class RecycleViewDemoActivityFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recycler_demo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutManagerDemo.setOnClickListener {
            startActivity(Intent(context, RecycleLayoutManagerDemoActivity::class.java))
        }
        gridlayoutManagerDemo.setOnClickListener {
            startActivity(Intent(context, RecycleGridLayoutManagerDemoActivity::class.java))
        }
        myGridGroupDemo.setOnClickListener {
            startActivity(Intent(context, MyGridGroupViewActivity::class.java))
        }
    }
}
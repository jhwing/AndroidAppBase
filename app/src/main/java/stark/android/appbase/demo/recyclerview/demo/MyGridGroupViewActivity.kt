package stark.android.appbase.demo.recyclerview.demo

import android.os.Bundle
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_grid_group_demo.*
import kotlinx.android.synthetic.main.activity_my_grid_group_demo.*
import org.jetbrains.anko.toast
import stark.android.appbase.activity.BaseToolbarActivity
import stark.android.appbase.activity.setToolbar
import stark.android.appbase.demo.R
import stark.android.appbase.widget.MyGridGroupView

class MyGridGroupViewActivity : BaseToolbarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_grid_group_demo)
        setToolbar("my grid group Demo", true)
        val adapter = GridGroupViewAdapter(this)
        adapter.items = ArrayList<String>().apply {
            for (i in 0..12) {
                add("item:$i")
            }
        }
        myGridGroupDemo.setOnItemClickListener(View.OnClickListener { v ->
            val itemText = v.findViewById<TextView>(R.id.itemText)
            toast("click " + itemText.text)
        })
        myGridGroupDemo.setOnPageChangeListener(object : MyGridGroupView.OnPageChangeListener {
            override fun onPageChange(page: Int) {
                pageIndicatorView.setSelectedPage(page)
            }
        })
        pageIndicatorView.setIndicator(2)

        myGridGroupDemo.setAdapter(adapter)
    }
}
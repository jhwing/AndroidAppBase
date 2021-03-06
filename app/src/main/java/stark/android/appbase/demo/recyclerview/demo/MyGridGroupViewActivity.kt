package stark.android.appbase.demo.recyclerview.demo

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_grid_group_demo.*
import kotlinx.android.synthetic.main.activity_my_grid_group_demo.*
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
        myGridGroupDemo.setOnItemClickListener(object : MyGridGroupView.OnItemClickListener {
            override fun onItemClick(view: View, index: Int) {
                val itemText = view.findViewById<TextView>(R.id.itemText)
                Toast.makeText(this@MyGridGroupViewActivity, "click " + itemText.text, Toast.LENGTH_SHORT).show()
            }
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
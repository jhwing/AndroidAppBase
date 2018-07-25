package stark.android.appbase.demo.recyclerview.demo

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_recycler_layout_manager_demo.*
import stark.android.appbase.activity.BaseToolbarActivity
import stark.android.appbase.activity.setToolbar
import stark.android.appbase.demo.R

class RecycleLayoutManagerDemoActivity : BaseToolbarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_layout_manager_demo)
        setToolbar("LayoutManager Demo", true)
        recyclerView.layoutManager = MyLayoutManager(this)

        recyclerView.adapter = RecyclerLayoutManagerDemoAdapter(this).apply {
            setItems(ArrayList<String>().apply {
                for (i in 0..15) {
                    add("$i item")
                }
            })
        }
    }
}
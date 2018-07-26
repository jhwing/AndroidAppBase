package stark.android.appbase.demo.recyclerview.demo

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import stark.android.appbase.demo.R
import stark.android.appbase.recycleview.BaseRecycleViewAdapter
import stark.android.appbase.recycleview.BaseRecycleViewHolder

class RecyclerLayoutManagerDemoAdapter(var context: Context) : BaseRecycleViewAdapter<String>(context) {


    override fun onCreateViewHolderDelegate(parent: ViewGroup, viewType: Int): BaseRecycleViewHolder<String> {
        return RecyclerLayoutManagerDemoViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view_recycler_layout_manager_demo, parent, false))
    }

    class RecyclerLayoutManagerDemoViewHolder(itemView: View) : BaseRecycleViewHolder<String>(itemView) {
        var itemText: TextView = itemView.findViewById(R.id.itemText)
        override fun bindTo(t: String) {
            Log.d("jihongwen", "RecyclerLayoutManagerDemoViewHolder bindTo t: $t")
            itemText.text = t
        }
    }
}
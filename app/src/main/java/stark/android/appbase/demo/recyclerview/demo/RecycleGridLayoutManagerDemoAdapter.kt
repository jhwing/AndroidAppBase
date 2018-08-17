package stark.android.appbase.demo.recyclerview.demo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import stark.android.appbase.demo.R
import stark.android.appbase.recycleview.BaseRecycleViewAdapter
import stark.android.appbase.recycleview.BaseRecycleViewHolder
import java.util.*

class RecycleGridLayoutManagerDemoAdapter(var context: Context) : BaseRecycleViewAdapter<String>(context), ItemTouchHelperAdapter {
    override fun onItemDissmiss(position: Int) {
        //移除数据
        getItems()?.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        //交换位置
        Collections.swap(getItems(), fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onCreateViewHolderDelegate(parent: ViewGroup, viewType: Int): BaseRecycleViewHolder<String> {
        return RecycleGridLayoutManagerDemoViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view_recycler_grid_layout_manager_demo, parent, false))
    }

    class RecycleGridLayoutManagerDemoViewHolder(view: View) : BaseRecycleViewHolder<String>(view) {
        var itemText: TextView = itemView.findViewById(R.id.itemText)
        override fun bindTo(t: String) {
            itemText.text = t
        }
    }
}
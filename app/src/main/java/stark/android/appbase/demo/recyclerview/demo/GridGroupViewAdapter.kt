package stark.android.appbase.demo.recyclerview.demo

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import stark.android.appbase.demo.R

class GridGroupViewAdapter(var context: Context) : BaseAdapter() {

    val layoutInflater = LayoutInflater.from(context)

    var items: MutableList<String>? = null


    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var itemView: View? = layoutInflater.inflate(R.layout.item_view_grid_group_demo, null)
        val itemText: TextView? = itemView?.findViewById(R.id.itemText)
        itemText?.text = "item:$position"
        return itemView
    }

    override fun getItem(position: Int): Any {
        return items!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return items?.size ?: 0
    }
}
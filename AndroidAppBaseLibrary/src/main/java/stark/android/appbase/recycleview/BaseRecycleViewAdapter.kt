package stark.android.appbase.recycleview

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

abstract class BaseRecycleViewAdapter<T>(context: Context) : RecyclerView.Adapter<BaseRecycleViewHolder<T>>() {

    internal var items: List<T>? = null

    var mInflater: LayoutInflater? = null

    init {
        mInflater = LayoutInflater.from(context)
    }

    fun setItems(items: List<T>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun addItems(items: MutableList<T>) {
        items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecycleViewHolder<T> {
        return onCreateViewHolderDelegate(parent, viewType)
    }

    abstract fun onCreateViewHolderDelegate(parent: ViewGroup, viewType: Int): BaseRecycleViewHolder<T>

    override fun onBindViewHolder(holder: BaseRecycleViewHolder<T>, position: Int) {
        holder.bindTo(items!![position])
    }

    override fun getItemCount(): Int {
        return if (items == null) 0 else items!!.size
    }
}

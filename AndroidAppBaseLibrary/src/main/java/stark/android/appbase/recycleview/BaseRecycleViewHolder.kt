package stark.android.appbase.recycleview

import android.support.v7.widget.RecyclerView
import android.view.View

abstract class BaseRecycleViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bindTo(t: T)

}

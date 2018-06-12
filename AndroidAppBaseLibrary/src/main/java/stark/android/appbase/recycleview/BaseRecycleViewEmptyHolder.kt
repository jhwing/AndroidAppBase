package stark.android.appbase.recycleview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import stark.android.appbase.R


class BaseRecycleViewEmptyHolder<T>(itemView: View) : BaseRecycleViewHolder<T>(itemView) {

    override fun bindTo(t: T) {}

    companion object {

        fun <T> create(layoutInflater: LayoutInflater, parent: ViewGroup): BaseRecycleViewEmptyHolder<T> {
            return BaseRecycleViewEmptyHolder(layoutInflater.inflate(R.layout.base_recycle_view_empty_item, parent, false))
        }
    }
}

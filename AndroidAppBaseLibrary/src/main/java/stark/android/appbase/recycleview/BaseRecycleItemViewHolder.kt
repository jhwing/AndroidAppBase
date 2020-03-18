package stark.android.appbase.recycleview

import android.view.View

abstract class BaseRecycleItemViewHolder<T>(itemView: View) :
    BaseRecycleViewHolder<BaseRecycleItem>(itemView) {
    override fun bindTo(t: BaseRecycleItem) {
        bindToItem(t.data as T)
    }

    abstract fun bindToItem(item: T)
}
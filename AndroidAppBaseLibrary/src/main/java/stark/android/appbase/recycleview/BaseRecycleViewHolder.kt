package stark.android.appbase.recycleview

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecycleViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bindTo(t: T)

}

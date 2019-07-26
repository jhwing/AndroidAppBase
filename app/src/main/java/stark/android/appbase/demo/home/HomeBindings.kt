package stark.android.appbase.demo.home

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

object HomeBindings {

    @BindingAdapter("items")
    @JvmStatic
    fun setItems(recyclerView: RecyclerView, items: MutableList<HomeItem>) {
        with(recyclerView.adapter as HomeListViewAdapter) {
            setItems(items)
        }
    }
}
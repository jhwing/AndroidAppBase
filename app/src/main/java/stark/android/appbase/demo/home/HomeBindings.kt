package stark.android.appbase.demo.home

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView

object HomeBindings {

    @BindingAdapter("items")
    @JvmStatic
    fun setItems(recyclerView: RecyclerView, items: List<HomeItem>) {
        with(recyclerView.adapter as HomeListViewAdapter) {
            setItems(items)
        }
    }
}
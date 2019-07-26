package stark.android.appbase.demo.home

import android.content.Context
import androidx.databinding.DataBindingUtil
import android.view.ViewGroup
import stark.android.appbase.demo.R
import stark.android.appbase.demo.databinding.ItemViewHomeBinding
import stark.android.appbase.recycleview.BaseRecycleViewAdapter
import stark.android.appbase.recycleview.BaseRecycleViewHolder

class HomeListViewAdapter(context: Context?) : BaseRecycleViewAdapter<HomeItem>(context) {

    var listener: HomeListItemListener? = null

    override fun onCreateViewHolderDelegate(parent: ViewGroup, viewType: Int): BaseRecycleViewHolder<HomeItem> {
        val binding: ItemViewHomeBinding = DataBindingUtil.inflate(mInflater, R.layout.item_view_home, parent, false)
        binding.listener = listener
        return HomeListViewHolder(binding)
    }

    class HomeListViewHolder(var mBinding: ItemViewHomeBinding) : BaseRecycleViewHolder<HomeItem>(mBinding.root) {
        override fun bindTo(t: HomeItem) {
            mBinding.homeItem = t
        }
    }
}
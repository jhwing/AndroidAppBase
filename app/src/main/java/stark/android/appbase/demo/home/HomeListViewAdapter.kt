package stark.android.appbase.demo.home

import android.content.Context
import android.databinding.DataBindingUtil
import android.view.ViewGroup
import stark.android.appbase.demo.R
import stark.android.appbase.demo.databinding.HomeItemViewBinding
import stark.android.appbase.recycleview.BaseRecycleViewAdapter
import stark.android.appbase.recycleview.BaseRecycleViewHolder

class HomeListViewAdapter(context: Context) : BaseRecycleViewAdapter<HomeItem>(context) {

    var listener: HomeListItemListener? = null

    override fun onCreateViewHolderDelegate(parent: ViewGroup, viewType: Int): BaseRecycleViewHolder<HomeItem> {
        val binding: HomeItemViewBinding = DataBindingUtil.inflate(mInflater, R.layout.home_item_view, parent, false)
        binding.listener = listener
        return HomeListViewHolder(binding)
    }

    class HomeListViewHolder(var mBinding: HomeItemViewBinding) : BaseRecycleViewHolder<HomeItem>(mBinding.root) {
        override fun bindTo(t: HomeItem) {
            mBinding.homeItem = t
        }
    }
}
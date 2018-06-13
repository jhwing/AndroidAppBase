package stark.android.appbase.demo.home

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration
import stark.android.appbase.demo.R
import stark.android.appbase.demo.databinding.FragmentHomeBinding
import stark.android.appbase.fragment.BaseFragment
import stark.android.appbase.fragment.obtainViewModel

class HomeFragment : BaseFragment(), HomeListItemListener {
    override fun onItemClick(homeItem: HomeItem) {
        if (homeItem.activity != "") {
            val intent = Intent()
            intent.setClassName(context, homeItem.activity)
            startActivity(intent)
        }
    }

    lateinit var mBinding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        mBinding.viewModel = obtainViewModel(HomeViewModel::class.java)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.homeListView.layoutManager = LinearLayoutManager(context)
        mBinding.homeListView.adapter = HomeListViewAdapter(context).apply { listener = this@HomeFragment }
        mBinding.homeListView.addItemDecoration(HorizontalDividerItemDecoration.Builder(context)
                .color(ContextCompat.getColor(context!!, R.color.sk_base_divider_color))
                .showLastDivider()
                .build())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mBinding.viewModel?.load()
    }

    companion object {

        val TAG = "HomeFragment"
    }
}

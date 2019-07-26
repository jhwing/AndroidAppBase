package stark.android.appbase.demo.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
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
        } else {
            Toast.makeText(context, "activity is empty! don't know where to go", Toast.LENGTH_SHORT).show()
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
//        mBinding.homeListView.addItemDecoration(HorizontalDividerItemDecoration.Builder(context)
//                .color(ContextCompat.getColor(context!!, R.color.sk_base_divider_color))
//                .showLastDivider()
//                .build())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mBinding.viewModel?.load()
    }

    companion object {

        val TAG = "HomeFragment"
    }
}

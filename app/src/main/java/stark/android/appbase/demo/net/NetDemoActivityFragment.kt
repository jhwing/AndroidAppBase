package stark.android.appbase.demo.net

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import stark.android.appbase.demo.R
import stark.android.appbase.demo.databinding.FragmentNetDemoBinding
import stark.android.appbase.fragment.BaseFragment

class NetDemoActivityFragment : BaseFragment() {

    lateinit var mBinding: FragmentNetDemoBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_net_demo, container, false)
        mBinding.viewModel = ViewModelProviders.of(this)[NetDemoActivityViewModel::class.java]
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mBinding.viewModel?.load()
    }
}
package stark.android.appbase.demo.home

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import stark.android.appbase.demo.R
import stark.android.appbase.demo.databinding.FragmentHomeBinding
import stark.android.appbase.fragment.BaseFragment
import stark.android.appbase.fragment.obtainViewModel

class HomeFragment : BaseFragment() {

    lateinit var mBinding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        mBinding.viewModel = obtainViewModel(HomeViewModel::class.java)
        return mBinding.root
    }

    companion object {

        val TAG = "HomeFragment"
    }
}

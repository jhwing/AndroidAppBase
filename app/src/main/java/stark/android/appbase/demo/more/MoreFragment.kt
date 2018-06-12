package stark.android.appbase.demo.more

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import stark.android.appbase.demo.R
import stark.android.appbase.demo.databinding.FragmentMoreBinding
import stark.android.appbase.fragment.BaseFragment

class MoreFragment : BaseFragment() {

    lateinit var mBinding: FragmentMoreBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_more, container, false)
        return mBinding.root
    }

    companion object {

        val TAG = "MoreFragment"
    }
}

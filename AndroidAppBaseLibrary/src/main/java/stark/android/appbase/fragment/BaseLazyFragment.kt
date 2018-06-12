package stark.android.appbase.fragment

import android.os.Bundle
import android.view.View

/**
 * Created by jihongwen on 16/8/27.
 */

abstract class BaseLazyFragment : BaseFragment() {

    private var isViewCreated: Boolean = false

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (userVisibleHint && isViewCreated) {
            lazyLoad()
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        isViewCreated = true
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        lazyLoad()
    }

    protected abstract fun lazyLoad()

}

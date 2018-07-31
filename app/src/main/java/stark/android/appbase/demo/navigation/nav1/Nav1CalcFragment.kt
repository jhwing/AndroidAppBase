package stark.android.appbase.demo.navigation.nav1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_navigation_calc.*
import stark.android.appbase.demo.R
import stark.android.appbase.fragment.BaseFragment

class Nav1CalcFragment : BaseFragment() {

    companion object {

        fun create(name: String): Nav1CalcFragment {
            val bundle = Bundle()
            bundle.putString("name", name)
            return Nav1CalcFragment().apply { arguments = bundle }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_navigation_calc, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentName.text = arguments?.getString("name")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}
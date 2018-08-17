package stark.android.appbase.demo.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_dialog_demo.*
import stark.android.appbase.demo.R
import stark.android.appbase.fragment.BaseFragment

class DialogDemoActivityFragment : BaseFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dialog_demo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomDialog.setOnClickListener {
            DialogDemo().show(childFragmentManager, "demo")
        }
    }
}
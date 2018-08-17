package stark.android.appbase.demo.dialog

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import stark.android.appbase.demo.R
import stark.android.appbase.dialog.BaseDialogFragment

class DialogDemo : BaseDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mWidth = resources.displayMetrics.widthPixels - resources.getDimensionPixelSize(R.dimen.bottom_margin) * 2
        mGravity = Gravity.BOTTOM
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_demo, container, false)
    }

}
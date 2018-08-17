package stark.android.appbase.demo.viewdraghelper

import android.os.Bundle
import stark.android.appbase.activity.BaseToolbarActivity
import stark.android.appbase.activity.setBaseContentView
import stark.android.appbase.activity.setToolbar

class ViewDragHelperDemoActivity : BaseToolbarActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBaseContentView()
        setToolbar("ViewDragHelper demo", true)
    }
}
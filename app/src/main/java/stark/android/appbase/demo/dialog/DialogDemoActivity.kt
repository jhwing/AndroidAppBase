package stark.android.appbase.demo.dialog

import android.os.Bundle
import stark.android.appbase.activity.BaseToolbarActivity
import stark.android.appbase.activity.setBaseContentView
import stark.android.appbase.activity.setToolbar

class DialogDemoActivity : BaseToolbarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBaseContentView()
        setToolbar("navigation demo", true)
        startFragment(DialogDemoActivityFragment(), "demo")
    }

}
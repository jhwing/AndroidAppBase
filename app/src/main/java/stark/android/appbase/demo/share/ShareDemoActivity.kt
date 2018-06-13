package stark.android.appbase.demo.share

import android.os.Bundle
import stark.android.appbase.activity.BaseToolbarActivity
import stark.android.appbase.activity.setBaseContentView
import stark.android.appbase.activity.setToolbar

class ShareDemoActivity : BaseToolbarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBaseContentView()
        setToolbar("share demo", true)
    }
}
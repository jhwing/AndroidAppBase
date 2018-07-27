package stark.android.appbase.demo.net

import android.os.Bundle
import stark.android.appbase.activity.BaseToolbarActivity
import stark.android.appbase.activity.setBaseContentView
import stark.android.appbase.activity.setToolbar

class NetDemoActivity : BaseToolbarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBaseContentView()
        setToolbar("net demo", true)
        startFragment(NetDemoActivityFragment(), "net_fragment")
    }
}
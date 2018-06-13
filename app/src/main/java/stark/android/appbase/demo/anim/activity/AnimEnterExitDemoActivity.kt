package stark.android.appbase.demo.anim.activity

import android.os.Bundle
import stark.android.appbase.activity.BaseToolbarActivity
import stark.android.appbase.activity.setBaseContentView
import stark.android.appbase.activity.setToolbar

class AnimEnterExitDemoActivity : BaseToolbarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBaseContentView()
        setToolbar("activity anim demo", true)
        startFragment(AnimEnterExitDemoActivityFragment(), "anim_demo")
    }
}
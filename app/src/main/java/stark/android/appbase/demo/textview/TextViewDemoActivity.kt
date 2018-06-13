package stark.android.appbase.demo.textview

import android.os.Bundle
import stark.android.appbase.activity.BaseToolbarActivity
import stark.android.appbase.activity.setBaseContentView
import stark.android.appbase.activity.setToolbar

class TextViewDemoActivity : BaseToolbarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBaseContentView()
        setToolbar("textview demo", true)
    }
}
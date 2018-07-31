package stark.android.appbase.demo.navigation.drag

import android.os.Bundle
import stark.android.appbase.activity.BaseToolbarActivity
import stark.android.appbase.demo.R

class DragDemoActivity : BaseToolbarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drag_demo)
    }
}
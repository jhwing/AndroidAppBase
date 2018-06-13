package stark.android.appbase.activity

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.Toolbar
import stark.android.appbase.R

fun <T : ViewModel> BaseActivity.obtainViewModel(modelClass: Class<T>) = ViewModelProviders.of(this).get(modelClass)


fun BaseToolbarActivity.setBaseContentView() {
    setContentView(R.layout.base_toolbar_container_activity)
}

fun BaseToolbarActivity.setToolbar(title: CharSequence, isSub: Boolean = false) {
    toolbar = findViewById(R.id.toolbar)
    middleTitle = findViewById(R.id.middleTitle)
    middleTitle?.text = title
    setToolbarAsUpEnable(toolbar, isSub)
}

fun BaseToolbarActivity.setToolbarAsUpEnable(toolbar: Toolbar?, isSub: Boolean = false, title: String = "") {
    isSubActivity = isSub
    toolbar?.title = title
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(isSub)
}
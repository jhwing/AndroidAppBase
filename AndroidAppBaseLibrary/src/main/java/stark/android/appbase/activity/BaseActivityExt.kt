package stark.android.appbase.activity

import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import stark.android.appbase.R

fun <T : ViewModel> BaseActivity.obtainViewModel(modelClass: Class<T>) =
    ViewModelProviders.of(this).get(modelClass)


fun BaseToolbarActivity.setBaseContentView() {
    setContentView(R.layout.base_toolbar_container_activity)
}

fun BaseToolbarActivity.setToolbar(title: CharSequence, isSub: Boolean = false) {
    toolbar = findViewById(R.id.toolbar)
    middleTitle = findViewById(R.id.middleTitle)
    middleTitle?.text = title
    if (middleTitle != null) {
        setToolbarAsUpEnable(toolbar, isSub)
    } else {
        setToolbarAsUpEnable(toolbar, isSub, title)
    }

}

fun BaseToolbarActivity.setToolbarAsUpEnable(
    toolbar: Toolbar?,
    isSub: Boolean = false,
    title: CharSequence = ""
) {
    isSubActivity = isSub
    toolbar?.title = title
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(isSub)
}
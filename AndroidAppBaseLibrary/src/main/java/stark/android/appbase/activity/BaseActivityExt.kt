package stark.android.appbase.activity

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.Toolbar

fun <T : ViewModel> BaseActivity.obtainViewModel(modelClass: Class<T>) = ViewModelProviders.of(this).get(modelClass)

fun BaseActivity.setToolbarAsUpEnable(toolbar: Toolbar?, isSub: Boolean = false, title: String = "") {
    isSubActivity = isSub
    toolbar?.title = title
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(isSub)
}
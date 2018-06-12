package stark.android.appbase.activity

import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.TextView
import stark.android.appbase.R

/**
 * Created by jihongwen on 16/8/4.
 */

open class BaseToolbarActivity : BaseActivity() {

    internal var toolbar: Toolbar? = null

    internal var middleTitle: TextView? = null

    fun setToolbar(title: CharSequence, isSub: Boolean = false) {
        toolbar = findViewById(R.id.toolbar)
        middleTitle = findViewById(R.id.middleTitle)
        middleTitle?.text = title
        setToolbarAsUpEnable(toolbar, isSub)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                if (isSubActivity)
                    onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

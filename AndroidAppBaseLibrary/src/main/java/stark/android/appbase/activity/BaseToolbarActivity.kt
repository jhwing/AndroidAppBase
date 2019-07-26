package stark.android.appbase.activity

import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

/**
 * Created by jihongwen on 16/8/4.
 */

open class BaseToolbarActivity : BaseActivity() {

    internal var isSubActivity = false

    internal var toolbar: Toolbar? = null

    internal var middleTitle: TextView? = null

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

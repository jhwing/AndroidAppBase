package stark.android.appbase.activity

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

/**
 * Created by jihongwen on 16/9/8.
 */

open class BaseActivity : AppCompatActivity() {

    internal var isSubActivity = false

    fun startFragment(fragment: Fragment, resId: Int, tag: String) {
        supportFragmentManager.beginTransaction()
                .add(resId, fragment, tag).commit()
    }

    fun findFragment(tag: String): Fragment {
        return supportFragmentManager.findFragmentByTag(tag)
    }
}

package stark.android.appbase.activity

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import stark.android.appbase.R

/**
 * Created by jihongwen on 16/9/8.
 */

open class BaseActivity : AppCompatActivity() {

    fun startFragment(fragment: Fragment, tag: String) {
        startFragment(fragment, R.id.fragment_container, tag)
    }

    fun startFragment(fragment: Fragment, resId: Int, tag: String) {
        supportFragmentManager.beginTransaction()
                .add(resId, fragment, tag).commit()
    }

    fun findFragment(tag: String): Fragment {
        return supportFragmentManager.findFragmentByTag(tag)
    }
}

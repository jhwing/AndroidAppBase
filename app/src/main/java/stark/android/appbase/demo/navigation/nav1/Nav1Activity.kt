package stark.android.appbase.demo.navigation.nav1

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_nav1_demo.*
import stark.android.appbase.activity.BaseToolbarActivity
import stark.android.appbase.activity.setToolbar
import stark.android.appbase.demo.R

/**
 * 底部弹窗 grid 导航
 */
class Nav1Activity : BaseToolbarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav1_demo)
        setToolbar("nav1 Demo", true)

        moreBtn.setOnClickListener {
            // 更多设置底部弹窗
        }

        appsBtn.setOnClickListener {
            // 功能切换底部弹窗
            showAppsView()
        }

        closeBtn.setOnClickListener {
            hideAppsView()
        }
    }

    private fun showAppsView() {
        nav1AppsView.show()
    }

    private fun hideAppsView() {
        nav1AppsView.hide()
    }

    fun replaceFragment() {
        startFragment(Nav1AppsFragment(), "apps")
    }
}
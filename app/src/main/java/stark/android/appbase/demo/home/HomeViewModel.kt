package stark.android.appbase.demo.home

import android.app.Application
import android.databinding.ObservableArrayList
import stark.android.appbase.arch.BaseViewModel
import stark.android.appbase.demo.anim.activity.AnimEnterExitDemoActivity
import stark.android.appbase.demo.db.DbDemoActivity
import stark.android.appbase.demo.dialog.DialogDemoActivity
import stark.android.appbase.demo.imagecache.ImageCacheDemoActivity
import stark.android.appbase.demo.navigation.NavigationDemoActivity
import stark.android.appbase.demo.net.NetDemoActivity
import stark.android.appbase.demo.recyclerview.RecycleViewDemoActivity
import stark.android.appbase.demo.share.ShareDemoActivity
import stark.android.appbase.demo.utils.UtilsDemoActivity
import stark.android.appbase.demo.viewdraghelper.ViewDragHelperDemoActivity
import stark.android.appbase.demo.viewpager.ViewPagerDemoActivity
import stark.android.appbase.demo.webview.WebViewDemoActivity

class HomeViewModel(application: Application) : BaseViewModel(application) {

    var items = ObservableArrayList<HomeItem>()

    override fun load() {

        items.add(HomeItem(
                "activity 动画切换demo",
                "左右切换，上下切换，共享元素",
                AnimEnterExitDemoActivity::class.java.name))

        items.add(HomeItem(
                "recycleview demo",
                "LayoutManager adapter refresh anim",
                RecycleViewDemoActivity::class.java.name))

        items.add(HomeItem(
                "viewpager demo",
                "coming soon",
                ViewPagerDemoActivity::class.java.name))

        items.add(HomeItem(
                "webview demo",
                "coming soon",
                WebViewDemoActivity::class.java.name))

        items.add(HomeItem(
                "imagecache demo",
                "coming soon",
                ImageCacheDemoActivity::class.java.name))

        items.add(HomeItem(
                "net demo",
                "coming soon",
                NetDemoActivity::class.java.name))

        items.add(HomeItem(
                "db demo",
                "coming soon",
                DbDemoActivity::class.java.name))

        items.add(HomeItem(
                "utils demo",
                "coming soon",
                UtilsDemoActivity::class.java.name))

        items.add(HomeItem(
                "share demo",
                "coming soon",
                ShareDemoActivity::class.java.name))

        items.add(HomeItem(
                "navigation demo",
                "coming soon",
                NavigationDemoActivity::class.java.name))

        items.add(HomeItem(
                "view drag helper demo",
                "coming soon",
                ViewDragHelperDemoActivity::class.java.name))

        items.add(HomeItem(
                "dialog demo",
                "bottom dialog",
                DialogDemoActivity::class.java.name))
    }
}
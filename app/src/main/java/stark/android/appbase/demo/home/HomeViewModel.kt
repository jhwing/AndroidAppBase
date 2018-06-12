package stark.android.appbase.demo.home

import android.app.Application
import android.databinding.ObservableArrayList
import stark.android.appbase.arch.BaseViewModel
import stark.android.appbase.demo.R

class HomeViewModel(application: Application) : BaseViewModel(application) {

    var items = ObservableArrayList<HomeItem>()

    fun load() {
        getResources().getStringArray(R.array.app_demo_home_list).forEach {
            items.add(HomeItem(it))
        }
    }
}
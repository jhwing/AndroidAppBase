package stark.android.appbase.demo.home

import android.app.Application
import android.databinding.ObservableField
import stark.android.appbase.arch.BaseViewModel
import stark.android.appbase.demo.R

class HomeViewModel(application: Application) : BaseViewModel(application) {

    var appHomeIntro = ObservableField<String>(getString(R.string.app_home_intro))
}
package stark.android.appbase.demo.recyclerview

import android.app.Application
import androidx.databinding.ObservableArrayList
import stark.android.appbase.arch.BaseViewModel

class RecycleViewDemoActivityViewModel(application: Application) : BaseViewModel(application) {

    var items = ObservableArrayList<RecycleDemoItem>()

    fun load() {

    }


}
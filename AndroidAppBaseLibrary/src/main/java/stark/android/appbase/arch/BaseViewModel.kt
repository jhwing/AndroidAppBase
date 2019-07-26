package stark.android.appbase.arch

import android.app.Application
import android.content.res.Resources
import androidx.lifecycle.AndroidViewModel

abstract class BaseViewModel(application: Application) : AndroidViewModel(application), IBaseLoadData {

    fun getString(resId: Int): String {
        return getResources().getString(resId)
    }

    open fun getResources(): Resources {
        return getApplication<Application>().resources
    }
}
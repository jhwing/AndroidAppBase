package stark.android.appbase.arch

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.content.res.Resources

open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    fun getString(resId: Int): String {
        return getResources().getString(resId)
    }

    open fun getResources(): Resources {
        return getApplication<Application>().resources
    }
}
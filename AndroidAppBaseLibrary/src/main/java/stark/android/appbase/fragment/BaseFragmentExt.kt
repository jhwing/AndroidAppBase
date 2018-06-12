package stark.android.appbase.fragment

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders

fun <T : ViewModel> BaseFragment.obtainViewModel(modelClass: Class<T>) = ViewModelProviders.of(this).get(modelClass)
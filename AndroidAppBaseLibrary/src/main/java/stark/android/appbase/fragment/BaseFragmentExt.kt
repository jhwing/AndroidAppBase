package stark.android.appbase.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders


fun <T : ViewModel> BaseFragment.obtainViewModel(modelClass: Class<T>) = ViewModelProviders.of(this).get(modelClass)
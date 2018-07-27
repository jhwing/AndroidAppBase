package stark.android.appbase.demo.net

import android.app.Application
import android.databinding.ObservableField
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stark.android.appbase.arch.BaseViewModel
import stark.android.appbase.demo.net.api.GankToday
import stark.android.appbase.demo.net.api.NetService
import timber.log.Timber

class NetDemoActivityViewModel(application: Application) : BaseViewModel(application) {

    var beanString = ObservableField<String>()

    override fun load() {
        NetService.instance.today().enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val body = response.body()?.string()
                    val bean = Gson().fromJson<GankToday>(body, GankToday::class.java)
                    beanString.set(bean.toString())
                    Timber.d("body:$body")
                }
            }
        })
    }
}
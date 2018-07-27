package stark.android.appbase.demo.net.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface GankApi {

    companion object {

        const val HOST = "http://gank.io/api/"

    }

    @GET("today")
    fun today(): Call<ResponseBody>
}
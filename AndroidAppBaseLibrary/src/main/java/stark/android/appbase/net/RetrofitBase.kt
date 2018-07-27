package stark.android.appbase.net

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import stark.android.appbase.BuildConfig

abstract class RetrofitBase<T>(var mContext: Context?) {

    val commonMap = HashMap<String, String>()

    fun getBaseBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
    }

    fun getOkHttpBaseBuilder(): OkHttpClient.Builder {
        val builder = OkHttpClient.Builder()
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        builder.addInterceptor(httpLoggingInterceptor)
        return builder
    }

    abstract fun getNetApi(): T

    abstract fun getUserAgent(): String

    abstract fun getHost(): String
}
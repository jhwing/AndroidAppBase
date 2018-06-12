package stark.android.appbase.net

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by jihongwen on 16/9/8.
 */

object RetrofitFactory {

    fun <T> initApi(clazz: Class<T>): T {
        val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        return retrofit.create(clazz)
    }
}

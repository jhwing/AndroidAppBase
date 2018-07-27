package stark.android.appbase.demo.net.api

import android.content.Context
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.converter.gson.GsonConverterFactory
import stark.android.appbase.net.RetrofitBase
import stark.android.appbase.net.interceptor.CommonParamsInterceptor
import stark.android.appbase.net.interceptor.UserAgentInterceptor
import stark.android.appbase.utils.NetUtil
import stark.android.appbase.utils.Utils

class NetService(context: Context?) : RetrofitBase<GankApi>(context), GankApi {

    private var api: GankApi = getNetApi()

    companion object {
        val instance by lazy { NetService(Utils.getApp()) }
    }

    override fun today(): Call<ResponseBody> {
        return api.today()
    }

    override fun getNetApi(): GankApi {
        val builder = getOkHttpBaseBuilder()
        builder.addInterceptor(UserAgentInterceptor(NetUtil.getApiUserAgent(mContext, getUserAgent())))
        builder.addInterceptor(CommonParamsInterceptor(commonMap))
        //builder.addInterceptor(SignInterceptor())

        val retrofit = getBaseBuilder()
                .baseUrl(getHost())
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build()

        return retrofit.create<GankApi>(GankApi::class.java)
    }

    override fun getUserAgent(): String {
        return ""
    }

    override fun getHost(): String {
        return GankApi.HOST
    }
}
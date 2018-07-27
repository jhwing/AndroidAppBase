package stark.android.appbase.net.interceptor

import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.*

class CommonParamsInterceptor(var commonMap: Map<String, String>) : Interceptor {

    @Throws(IOException::class)
    private fun addGetParams(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val hashMap = HashMap<String, String?>()
        val url = request.url()
        for (s in url.queryParameterNames()) {
            hashMap[s] = url.queryParameter(s)
        }
        val host = url.newBuilder().scheme(url.scheme()).host(url.host())
        //        for (final Map.Entry<String, String> entry : hashMap.entrySet()) {
        //            host.addQueryParameter(entry.getKey(), entry.getValue());
        //        }
        for ((key, value) in this.commonMap) {
            host.addQueryParameter(key, value)
        }
        return chain.proceed(request.newBuilder().method(request.method(), request.body()).url(host.build()).build())
    }

    @Throws(IOException::class)
    private fun addPostParams(chain: Interceptor.Chain, formBody: FormBody): Response {
        val request = chain.request()
        val hashMap = HashMap<String, String>()
        for (i in 0 until formBody.size()) {
            hashMap[formBody.encodedName(i)] = formBody.encodedValue(i)
        }
        val builder = FormBody.Builder()
        for ((key, value) in hashMap) {
            builder.addEncoded(key, value)
        }
        for ((key, value) in this.commonMap) {
            builder.addEncoded(key, value)
        }
        return chain.proceed(request.newBuilder().method(request.method(), builder.build()).url(request.url()).build())
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val body = chain.request().body()
        return if (body != null && body is FormBody) {
            this.addPostParams(chain, (body as FormBody?)!!)
        } else this.addGetParams(chain)
    }
}

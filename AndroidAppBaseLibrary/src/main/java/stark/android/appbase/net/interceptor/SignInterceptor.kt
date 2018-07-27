package stark.android.appbase.net.interceptor

import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Response
import stark.android.appbase.net.SignUtil
import java.io.IOException
import java.net.URLDecoder
import java.net.URLEncoder
import java.util.*

class SignInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val hashMap = HashMap<String, String>()
        val body = request.body()
        if (body != null && body is FormBody) {
            val formBody = body as FormBody?
            for (i in 0 until formBody!!.size()) {
                hashMap[formBody.encodedName(i)] = formBody.encodedValue(i)
            }
            if (hashMap.containsKey("sign")) {
                return chain.proceed(request)
            }
            hashMap["sign"] = SignUtil.sign(hashMap)
            val builder = FormBody.Builder()
            for ((key, value) in hashMap) {
                builder.addEncoded(key, value)
            }
            return chain.proceed(request.newBuilder().method(request.method(), builder.build()).url(request.url()).build())
        }

        val url = request.url()
        for (s in url.queryParameterNames()) {
            val queryParameter = url.queryParameter(s)
            if (queryParameter != null) {
                hashMap[s] = URLEncoder.encode(queryParameter, "utf-8").replace("+", "%20")
            }
        }
        if (hashMap.containsKey("sign")) {
            return chain.proceed(request)
        }
        hashMap["sign"] = SignUtil.sign(hashMap)
        val host = url.newBuilder().scheme(url.scheme()).host(url.host())
        for ((key, value) in hashMap) {
            host.setQueryParameter(key, URLDecoder.decode(value, "utf-8"))
        }
        return chain.proceed(request.newBuilder().method(request.method(), request.body()).url(host.build()).build())
    }
}

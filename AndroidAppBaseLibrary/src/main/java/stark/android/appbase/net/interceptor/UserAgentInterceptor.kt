package stark.android.appbase.net.interceptor

import java.io.IOException

import okhttp3.Interceptor
import okhttp3.Response

class UserAgentInterceptor(private val userAgent: String) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        chain.request().url().toString()
        return chain.proceed(chain.request().newBuilder()
                .removeHeader("User-Agent")
                .addHeader("User-Agent", this.userAgent)
                .build())
    }

    companion object {
        private val USER_AGENT_HEADER_NAME = "User-Agent"
    }
}

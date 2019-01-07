package stark.android.appbase.demo

import android.app.Application
import android.util.Log
import com.facebook.drawee.backends.pipeline.Fresco
import timber.log.Timber

class App : Application() {

    private val prefixTag = "AppBaseDemo"

    override fun onCreate() {
        super.onCreate()

        Fresco.initialize(this)
        Timber.plant(object : Timber.Tree() {
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                val realTag = "$prefixTag:$tag"
                Log.println(priority, realTag, message)
            }
        })
    }
}
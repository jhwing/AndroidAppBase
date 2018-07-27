package stark.android.appbase

interface IAppConfig {

    fun channel(): String

    fun languages(): String

    fun model(): String

    fun packageName(): String

    fun verSdk(): String

    fun versionCode(): String

    fun versionName(): String

    fun agent(): String
    
    fun appName(): String
}
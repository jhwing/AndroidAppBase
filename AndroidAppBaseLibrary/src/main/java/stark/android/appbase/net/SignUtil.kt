package stark.android.appbase.net

import stark.android.appbase.BuildConfig
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.security.MessageDigest
import java.util.*
import kotlin.experimental.and

object SignUtil {
    val SIGN = BuildConfig.SIGN_KEY

    fun sign(args: Map<String, String>): String {
        val keyList = ArrayList(args.keys)
        keyList.sort()
        val sortStr = StringBuilder()
        for (key in keyList) {
            try {
                val value = if (args[key] == null) "" else args[key]
                sortStr.append(key).append("=" + URLEncoder.encode(value, "utf-8").replace("%252F", "/").replace("+", "%20") + "&")
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            }

        }
        sortStr.append(SIGN)
        return sign(sortStr)
    }

    fun signWithKey(sb: StringBuilder): String {
        return sign(sb.append(SIGN))
    }

    fun sign(sortStr: StringBuilder): String {
        try {
            val md5 = MessageDigest.getInstance("MD5")
            md5.update(sortStr.toString().toByteArray())
            val bytes = md5.digest()
            var result = ""
            for (b in bytes) {
                var temp = Integer.toHexString(b.and(0xff.toByte()).toInt())
                if (temp.length == 1) {
                    temp = "0$temp"
                }
                result += temp
            }
            return result.substring(2, 8)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }
}

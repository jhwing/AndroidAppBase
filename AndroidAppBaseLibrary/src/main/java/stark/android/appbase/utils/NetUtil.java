package stark.android.appbase.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.webkit.WebSettings;

public class NetUtil {
    public static String getApiUserAgent(Context context, String agent) {
        String userAgent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                userAgent = WebSettings.getDefaultUserAgent(context);
            } catch (Exception e) {
                userAgent = System.getProperty("http.agent");
            }
            StringBuffer sb = getFormatUA(userAgent);
            if (TextUtils.isEmpty(agent)) {
                return sb.toString();
            } else {
                return sb.toString() + "/" + agent;
            }
        } else {
            userAgent = System.getProperty("http.agent");
            StringBuffer sb = getFormatUA(userAgent);
            if (TextUtils.isEmpty(agent)) {
                return sb.toString();
            } else {
                return sb.toString() + "/" + agent;
            }
        }
    }

    @NonNull
    private static StringBuffer getFormatUA(String userAgent) {
        StringBuffer sb = new StringBuffer();
        if (userAgent == null) {
            return sb;
        }
        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        return sb;
    }


    public static String getNetType(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // TODO: 2018/5/17 检查权限
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                return "WIFI";
            } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                switch (info.getSubtype()) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                        return "2G";
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                        return "3G";
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        return "4G";
                    default:
                        return "UNKNOWN";
                }
            } else {
                return "UNKNOWN";
            }
        }
        return "NONE";
    }
}

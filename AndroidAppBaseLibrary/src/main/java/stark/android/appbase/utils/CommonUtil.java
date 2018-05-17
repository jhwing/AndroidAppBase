package stark.android.appbase.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;


/**
 * Created by jihongwen on 2018/3/9.
 */
public class CommonUtil {

    private static final String TAG = "CommonUtil";

    /**
     * 打开应用市场
     */
    public static void toMarket(Context context) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.getPackageName())));
        } catch (android.content.ActivityNotFoundException e) {
            Log.e(TAG, "market can't open " + e);
        }
    }

    public static void toShare(Context context, String title, String content) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, title);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        context.startActivity(Intent.createChooser(intent, "分享"));
    }

    public static void copy(Context context, String label, String content) {
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setPrimaryClip(ClipData.newPlainText(label, content));
    }

    public static boolean isAppInstall(Context context, String pkg) {
        if (context == null || pkg == null) {
            return false;
        }
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(pkg, PackageManager.GET_GIDS);
            Log.d(TAG, "app info " + info.packageName);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return true;
    }
}
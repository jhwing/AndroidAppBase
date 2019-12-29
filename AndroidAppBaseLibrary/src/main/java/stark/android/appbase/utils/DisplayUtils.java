package stark.android.appbase.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;

/**
 * 单位转换
 */
public final class DisplayUtils {
    private DisplayUtils() {
    }

    private static final float DENSITY = Resources.getSystem().getDisplayMetrics().density;

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        java.lang.reflect.Field field = null;
        int x = 0;
        int statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
            return statusBarHeight;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public static int getDefaultHeight(Context context) {
        if (context instanceof Activity) {
            return ((Activity) context).getWindowManager().getDefaultDisplay().getHeight();
        }
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public static int dp2Px(int dp) {
        return Math.round(dp * DENSITY);
    }


    public static int px2dp(Context ctx, float pxValue) {
        final float density = ctx.getResources().getDisplayMetrics().density;
        return (int) (pxValue / density + 0.5f);
    }

    public static int dp2px(Context ctx, float dpValue) {
        final float density = ctx.getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f);
    }

    public static int sp2px(Context ctx, float spValue) {
        final float scaledDensity = ctx.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scaledDensity + 0.5f);
    }

    public static int px2sp(Context ctx, float pxValue) {
        final float scaledDensity = ctx.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / scaledDensity + 0.5f);
    }

    public static int dp2sp(Context ctx, float dpValue) {
        final float density = ctx.getResources().getDisplayMetrics().scaledDensity;
        final float scaledDensity = ctx.getResources().getDisplayMetrics().scaledDensity;
        return (int) (dpValue * density / scaledDensity + 0.5f);
    }

    public static int sp2dp(Context ctx, float spValue) {
        final float density = ctx.getResources().getDisplayMetrics().scaledDensity;
        final float scaledDensity = ctx.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scaledDensity / density + 0.5f);
    }
}

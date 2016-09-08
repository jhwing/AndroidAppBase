package stark.android.appbase.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by jihongwen on 15/12/11.
 */
public abstract class BaseRelativeLayout<T> extends RelativeLayout {

    protected Context mContext;

    protected String referer;

    public BaseRelativeLayout(Context context) {
        super(context);
        init(context);
    }

    public BaseRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BaseRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    private void init(Context context) {
        mContext = context;
        View.inflate(context, getViewLayout(), this);
        if (isInEditMode()) {
            return;
        }
        createView();
    }

    protected int dpToPx(int dps) {
        return Math.round(getResources().getDisplayMetrics().density * dps);
    }

    public abstract int getViewLayout();

    public abstract void createView();

    public abstract void bindView(T t, String referer);

    public abstract void unbind(Context context);

}

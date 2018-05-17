package stark.android.appbase.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import stark.android.appbase.R;


public class NavigationTab extends FrameLayout {

    private boolean tabSelected = false;

    public NavigationTab(Context context) {
        this(context, null);
    }

    public NavigationTab(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavigationTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NavigationTab);
            tabSelected = typedArray.getBoolean(R.styleable.NavigationTab_tabSelected, false);
            typedArray.recycle();
        }
    }

    public boolean isTabSelected() {
        return tabSelected;
    }
}

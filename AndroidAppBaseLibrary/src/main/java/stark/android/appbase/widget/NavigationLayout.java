package stark.android.appbase.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class NavigationLayout extends LinearLayout implements View.OnClickListener {

    private OnNavigationTabClickListener onNavigationTabClickListener;

    public NavigationLayout(Context context) {
        this(context, null);
    }

    public NavigationLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavigationLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        super.addView(child, params);
        if (child instanceof NavigationTab) {
            if (((NavigationTab) child).isTabSelected()) {
                setSelected(child);
            }
            child.setOnClickListener(this);
        }
    }

    public void setOnNavigationTabClickListener(OnNavigationTabClickListener onNavigationTabClickListener) {
        this.onNavigationTabClickListener = onNavigationTabClickListener;
    }

    private void setSelected(View v) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (v != child) {
                child.setSelected(false);
            }
        }
        v.setSelected(true);
    }

    @Override
    public void onClick(View v) {
        setSelected(v);
        if (onNavigationTabClickListener != null) {
            onNavigationTabClickListener.onTabClick(v);
        }
    }

    public void setSelectedView(int resId) {
        View view = findViewById(resId);
        if (view != null) {
            view.setSelected(true);
        }
    }

    public interface OnNavigationTabClickListener {

        void onTabClick(View v);
    }
}

package stark.android.appbase.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import stark.android.appbase.R


class NavigationTab @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {

    var isTabSelected = false
        private set

    init {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.NavigationTab)
            isTabSelected = typedArray.getBoolean(R.styleable.NavigationTab_tabSelected, false)
            typedArray.recycle()
        }
    }
}

package stark.android.appbase.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

class NavigationLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr), View.OnClickListener {

    private var onNavigationTabClickListener: OnNavigationTabClickListener? = null

    override fun addView(child: View, params: ViewGroup.LayoutParams) {
        super.addView(child, params)
        if (child is NavigationTab) {
            if (child.isTabSelected) {
                setSelected(child)
            }
            child.setOnClickListener(this)
        }
    }

    fun setOnNavigationTabClickListener(onNavigationTabClickListener: OnNavigationTabClickListener) {
        this.onNavigationTabClickListener = onNavigationTabClickListener
    }

    private fun setSelected(v: View) {
        val childCount = childCount
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (v !== child) {
                child.isSelected = false
            }
        }
        v.isSelected = true
    }

    override fun onClick(v: View) {
        setSelected(v)
        if (onNavigationTabClickListener != null) {
            onNavigationTabClickListener!!.onTabClick(v)
        }
    }

    fun setSelectedView(resId: Int) {
        val view = findViewById<View>(resId)
        if (view != null) {
            view.isSelected = true
        }
    }

    interface OnNavigationTabClickListener {

        fun onTabClick(v: View)
    }
}

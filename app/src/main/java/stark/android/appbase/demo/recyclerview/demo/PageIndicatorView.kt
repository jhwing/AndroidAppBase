package stark.android.appbase.demo.recyclerview.demo

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import stark.android.appbase.demo.R

class PageIndicatorView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    val margin = (3 * resources.displayMetrics.density).toInt()

    fun setIndicator(count: Int) {
        removeAllViews()
        for (i in 0 until count) {
            val layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            layoutParams.setMargins(margin, margin, margin, margin)
            val view = ImageView(context)
            if (i == 0) {
                view.setImageResource(R.drawable.seekpoint_highlight)
            } else {
                view.setImageResource(R.drawable.seekpoint_normal)
            }
            addView(view, layoutParams)
        }
    }

    fun setSelectedPage(page: Int) {
        for (i in 0 until childCount) {
            val view = getChildAt(i) as ImageView
            if (i == page) {
                view.setImageResource(R.drawable.seekpoint_highlight)
            } else {
                view.setImageResource(R.drawable.seekpoint_normal)
            }
        }
    }
}
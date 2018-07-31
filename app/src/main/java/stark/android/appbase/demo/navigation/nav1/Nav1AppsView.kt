package stark.android.appbase.demo.navigation.nav1

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import stark.android.appbase.demo.R
import stark.android.appbase.demo.utils.AnimationUtil

/**
 * 3列3行
 */
class Nav1AppsView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    val rows = 3
    val columns = 3

    init {
        for (i in 0.until(rows * columns)) {
            addView(getView())
        }
    }

    private fun getView(): View {
        return LayoutInflater.from(context).inflate(R.layout.nav1_apps_item_view, null)
    }

    fun show() {
        visibility = View.VISIBLE
        animation = AnimationUtil.moveToViewLocation()
    }

    fun hide() {
        visibility = View.GONE
        animation = AnimationUtil.moveToViewBottom()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
//        val itemWidth = width / columns
//        val itemHeight = height / rows
        val count = childCount
        for (i in 0.until(count)) {
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec)
        }
        setMeasuredDimension(width, height)
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val count = childCount
        val width = width
        val height = height
        val itemWidth = width / columns
        val itemHeight = height / rows
        var rowIndex = 0
        for (i in 0.until(count)) {
            var cl = 0
            var ct = 0
            var cr = 0
            var cb = 0
            val columnsIndex = i % columns
            Log.d("jihongwen", "columnsIndex=$columnsIndex")
            if (columnsIndex == 0 && i != 0) {
                rowIndex++
            }
            Log.d("jihongwen", "rowIndex=$rowIndex")
            cl = itemWidth * (i % columns)
            ct = itemHeight * rowIndex
            cr = cl + itemWidth
            cb = ct + itemHeight
            getChildAt(i).layout(cl, ct, cr, cb)
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }
}
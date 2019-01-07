package stark.android.appbase.demo.recyclerview.demo

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.facebook.drawee.view.SimpleDraweeView

/**
 * 多图 3行 3列
 *
 */
class GridImageView : ViewGroup {

    private val mViewConfig = ViewConfig(context)

    @JvmOverloads
    constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr) {

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mViewConfig.reset()
        mViewConfig.setParentSize(measuredWidth, mViewConfig.itemWidth)
        // 宽度固定，高度根据行数确定,默认一行
        var parentWidth = measuredWidth
        var parentHeight = mViewConfig.itemWidth
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val rect = mViewConfig.getChildRect(i)
            parentHeight = mViewConfig.parentHeight
            child.measure(MeasureSpec.makeMeasureSpec(rect.width(), MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(rect.height(), MeasureSpec.EXACTLY))
        }
        setMeasuredDimension(View.getDefaultSize(parentWidth, widthMeasureSpec), View.getDefaultSize(parentHeight, heightMeasureSpec))
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        mViewConfig.reset()
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val rect = mViewConfig.getChildRect(i)
            child.layout(rect.left, rect.top, rect.right, rect.bottom)
        }
    }

    fun setImages(imgUrls: List<String>) {
        if (imgUrls.size == childCount) {
            // 更新
            for (i in 0 until childCount) {
                val child = getChildAt(i) as SimpleDraweeView
                child.setImageURI(imgUrls[i])
            }
        } else {
            // 重建
            removeAllViews()
            imgUrls.forEach {
                val imageView = SimpleDraweeView(context)
                addView(imageView)
                imageView.setImageURI(it)
            }
        }
    }

    class ViewConfig(context: Context) {
        var rows = 3
        var columns = 3

        var widthMargin = ((context.resources.displayMetrics.density + 0.5f) * 3).toInt()
        var heightMargin = ((context.resources.displayMetrics.density + 0.5f) * 3).toInt()
        var parentWidth = context.resources.displayMetrics.widthPixels
        var parentHeight = context.resources.displayMetrics.heightPixels
        var itemWidth = parentWidth / rows
        var itemHeight = parentHeight / columns

        var mRowIndex = 0

        fun reset() {
            mRowIndex = 0
        }

        fun isCurrentRow(i: Int): Boolean {
            var rowIndex = i / rows % rows
            return mRowIndex == rowIndex
        }

        fun setParentSize(width: Int, height: Int) {
            parentWidth = width
            parentHeight = height
            itemWidth = parentWidth / rows
            //itemHeight = parentHeight / columns
            // 多图以正方形显示
            itemHeight = itemWidth
        }

        fun getChildRect(i: Int): Rect {
            // 超过 rows * columns 就是下一页
            var rowIndex = i / rows % rows
            // 第一页和最后一页不加margin，最左边和最右边不加margin
            val left = itemWidth * (i % columns)
            val right = left + itemWidth - widthMargin

            val top = itemHeight * rowIndex
            val bottom = top + itemHeight - heightMargin
            if (mRowIndex != rowIndex) {
                // 不在同一行，高度增加margin
                parentHeight += itemHeight
            }

            mRowIndex = rowIndex
            return Rect(left, top, right, bottom)
        }
    }
}
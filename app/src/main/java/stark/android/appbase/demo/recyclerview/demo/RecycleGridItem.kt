package stark.android.appbase.demo.recyclerview.demo

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.widget.FrameLayout

class RecycleGridItem @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val totalSpace = (parent as RecyclerView).measuredHeight
        val width = MeasureSpec.getSize(widthMeasureSpec)
        setMeasuredDimension(width, totalSpace / 3)
        Log.d("jihongwen", "totalSpace=$totalSpace")
    }
}
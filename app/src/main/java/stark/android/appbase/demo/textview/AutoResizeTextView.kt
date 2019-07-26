package stark.android.appbase.demo.textview

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.TextView
import timber.log.Timber

class AutoResizeTextView : TextView {

    val defTextSize = textSize
    val defPaint = paint

    constructor(context: Context?) : super(context) {

    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {

    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

    }

    private fun init() {

    }

    override fun setText(text: CharSequence, type: BufferType?) {
        Timber.d("setText $text")
        val bounds = Rect()
        paint.getTextBounds(text.toString(), 0, text.length, bounds)
        Timber.d("new text width ${bounds.width()} textView width $maxWidth")
        // 输入后的文本宽度大于最大宽度开始缩小字体
        if (bounds.width() > maxWidth) {
            autoResizeZoomIn(text)
        } else if (defTextSize > textSize) {
            autoResizeZoomOut(text)
        }
        super.setText(text, type)
    }

    private fun autoResizeZoomIn(text: CharSequence) {
        var resize = textSize
        val bounds = Rect()
        paint.getTextBounds(text.toString(), 0, text.length, bounds)
        while (bounds.width() > maxWidth) {
            resize = textSize - 1
            paint.textSize = resize
            paint.getTextBounds(text.toString(), 0, text.length, bounds)
        }
        Timber.d("auto resize ${bounds.width()}")
        setTextSize(TypedValue.COMPLEX_UNIT_PX, resize)
    }

    private fun autoResizeZoomOut(text: CharSequence) {
        var resize = textSize
        val bounds = Rect()
        val lastBounds = Rect()
        paint.getTextBounds(text.toString(), 0, text.length, bounds)
        defPaint.getTextBounds(getText().toString(), 0, getText().length, lastBounds)
        while (bounds.width() < maxWidth && bounds.width() < lastBounds.width()) {
            resize = textSize + 1
            paint.textSize = resize
            paint.getTextBounds(text.toString(), 0, text.length, bounds)
        }
        Timber.d("auto resize ${bounds.width()}")
        setTextSize(TypedValue.COMPLEX_UNIT_PX, resize - 1)
    }
}
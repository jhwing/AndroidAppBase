package stark.android.appbase.demo.navigation.drag

import android.content.Context
import android.support.v4.widget.ViewDragHelper
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout

/**
 * 使用 ViewDragHelper
 */
class TestViewGroup @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var mDragOriLeft = 0
    var mDragOriTop = 0

    private val callback = object : ViewDragHelper.Callback() {
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return true
        }

        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            return left
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            return top
        }

        override fun onViewCaptured(capturedChild: View, activePointerId: Int) {
            super.onViewCaptured(capturedChild, activePointerId)
            mDragOriLeft = capturedChild.left
            mDragOriTop = capturedChild.top
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            super.onViewReleased(releasedChild, xvel, yvel)
            mDragHelper.settleCapturedViewAt(mDragOriLeft, mDragOriTop)
            invalidate()
        }
    }

    val mDragHelper: ViewDragHelper = ViewDragHelper.create(this, this.callback)

    override fun computeScroll() {
        super.computeScroll()
        if (mDragHelper.continueSettling(true)) {
            invalidate()
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return mDragHelper.shouldInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        mDragHelper.processTouchEvent(event)
        return true
    }
}
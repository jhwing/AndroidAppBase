package stark.android.appbase.demo.navigation.drag

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.customview.widget.ViewDragHelper
import stark.android.appbase.demo.R

/**
 * 使用 ViewDragHelper
 */
class TestViewGroup @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var drawPanel: View? = null
    private var testDrawView3: View? = null
    private var slidingUpPanel: View? = null
    private var openBtn: View? = null
    private var closeBtn: View? = null
    private var moreBtn: View? = null

    enum class PanelState {
        EXPLODED, HIDDEN
    }

    var panelState = PanelState.HIDDEN

    override fun onFinishInflate() {
        super.onFinishInflate()
        openBtn = findViewById(R.id.openBtn)
        closeBtn = findViewById(R.id.closeBtn)
        moreBtn = findViewById(R.id.moreBtn)
        closeBtn?.alpha = 0f
        drawPanel = findViewById(R.id.drawPanel)
        testDrawView3 = findViewById(R.id.testDrawView3)
        slidingUpPanel = findViewById(R.id.slidingUpPanel)
        drawPanel?.setOnClickListener {
            if (panelState == PanelState.HIDDEN) {
                panelState = PanelState.EXPLODED
                smoothSlideTo(0)
            } else {
                panelState = PanelState.HIDDEN
                smoothSlideTo(oriSlidingUpTop)
            }
        }

        moreBtn?.setOnClickListener {
            requestLayout()
        }
    }

    var oriSlidingUpTop = 0

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (drawPanel != null) {
            if (panelState == PanelState.HIDDEN) {
                val parentHeight = bottom - top
                val height = drawPanel!!.height
                oriSlidingUpTop = bottom - height
                slidingUpPanel?.layout(left, oriSlidingUpTop, right, oriSlidingUpTop + parentHeight)
            }
        }
    }

    // 展开和收起

    var mDragOriLeft = 0
    var mDragOriTop = 0

    private val callback = object : ViewDragHelper.Callback() {
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            val tag = child.tag
            return "drawView" == tag
        }

        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            Log.d("jihongwen", "clampViewPositionHorizontal left=$left dx=$dx")
            return left
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            Log.d("jihongwen", "clampViewPositionVertical top=$top dy=$dy")
            return top
        }

        override fun onViewPositionChanged(changedView: View, left: Int, top: Int, dx: Int, dy: Int) {
            super.onViewPositionChanged(changedView, left, top, dx, dy)
            Log.d("jihongwen", "left=$left top=$top dx=$dx dy=$dy")
            changeBtn(top)
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
            Log.d("jihongwen", "onViewReleased invalidate")
        }
    }

    val mDragHelper: ViewDragHelper = ViewDragHelper.create(this, this.callback)

    override fun computeScroll() {
        super.computeScroll()
        Log.d("jihongwen", "computeScroll")
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this)
            Log.d("jihongwen", "computeScroll invalidate")
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return mDragHelper.shouldInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        mDragHelper.processTouchEvent(event)
        return true
    }

    private fun smoothSlideTo(finalTop: Int) {
        if (mDragHelper.smoothSlideViewTo(slidingUpPanel!!, slidingUpPanel!!.left, finalTop)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    private fun changeBtn(top: Int) {
        // 0f~360f
        val rate = (oriSlidingUpTop - top) / oriSlidingUpTop.toFloat()
        openBtn?.rotation = rate * 360f
        closeBtn?.rotation = rate * 360f
        Log.d("jihongwen", "rotation=" + openBtn?.rotation)
        openBtn?.alpha = 1 - rate
        closeBtn?.alpha = rate
    }
}
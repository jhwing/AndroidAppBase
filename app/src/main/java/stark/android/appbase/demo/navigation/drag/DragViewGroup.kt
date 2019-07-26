package stark.android.appbase.demo.navigation.drag

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.FrameLayout
import androidx.core.view.ViewCompat

/**
 * 简单拖拽实现
 */
class DragViewGroup @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {


    var mLastPointX = 0f
    var mLastPointY = 0f

    var mDragViewOrigX = 0f
    var mDragViewOrigY = 0f

    // 用户标识正在被拖拽的 child
    var mDragView: View? = null

    // 用于识别最小的滑动距离
    var mSlop = ViewConfiguration.get(context).scaledWindowTouchSlop

    // 状态
    enum class State {
        IDLE, DRAGGING
    }

    var mState = State.IDLE


    /**
     * 判断触摸的位置是否落在child身上
     */
    private fun isPointOnViews(event: MotionEvent): Boolean {
        var result = false
        val rect = Rect()
        for (i in 0.until(childCount)) {
            val child = getChildAt(i)
            val l = child.x
            val t = child.y
            val r = child.x + child.width
            val b = child.y + child.height
            rect.set(l.toInt(), t.toInt(), r.toInt(), b.toInt())
            Log.d("jihongwen", " l $l t $t r $r b $b")
            if (rect.contains(event.x.toInt(), event.y.toInt())) {
                mDragView = child
                mDragViewOrigX = mDragView!!.x
                mDragViewOrigY = mDragView!!.y
                result = true
                break
            }
        }
        return result && mState != State.DRAGGING
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val action = event.action
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                if (isPointOnViews(event)) {
                    mState = State.DRAGGING
                    mLastPointX = event.x
                    mLastPointY = event.y
                }
            }
            MotionEvent.ACTION_MOVE -> {
                val deltaX = event.x - mLastPointX
                val deltaY = event.y - mLastPointY
                if (mState == State.DRAGGING && mDragView != null && (Math.abs(deltaX) > mSlop || Math.abs(deltaY) > mSlop)) {
                    ViewCompat.offsetLeftAndRight(mDragView!!, deltaX.toInt())
                    ViewCompat.offsetTopAndBottom(mDragView!!, deltaY.toInt())
                    mLastPointX = event.x
                    mLastPointY = event.y
                }
            }
            MotionEvent.ACTION_UP -> {
                if (mState == State.DRAGGING) {
                    if (mDragView != null) {
                        val animatorX = ValueAnimator.ofFloat(mDragView!!.x, mDragViewOrigX)
                        animatorX.addUpdateListener {
                            mDragView!!.x = it.animatedValue as Float
                        }
                        val animatorY = ValueAnimator.ofFloat(mDragView!!.y, mDragViewOrigY)
                        animatorY.addUpdateListener {
                            mDragView!!.y = it.animatedValue as Float
                        }
                        val animatorSet = AnimatorSet()
                        animatorSet.play(animatorX).with(animatorY)
                        animatorSet.addListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator?) {
                                super.onAnimationEnd(animation)
                                mDragView = null
                            }
                        })
                        animatorSet.start()
                    } else {
                        mDragView = null
                    }
                    mState = State.IDLE
                }
            }
        }
        return true
    }
}
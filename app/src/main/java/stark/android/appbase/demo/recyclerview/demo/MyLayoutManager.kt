package stark.android.appbase.demo.recyclerview.demo

import android.content.Context
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup

class MyLayoutManager(context: Context) : RecyclerView.LayoutManager() {

    private var mOrientationHelper: OrientationHelper = OrientationHelper.createOrientationHelper(this, RecyclerView.VERTICAL)
    private var verticalScrollOffset = 0
    private var totalHeight = 0
    private var mShouldReverseLayout = false
    private var mLayoutState: LayoutState = LayoutState()

    // 布局子view，
    // 确定显示区域
    // 在显示区域绘制view
    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        //如果没有item，直接返回
        if (itemCount <= 0) return
        // 跳过preLayout，preLayout主要用于支持动画
        if (state.isPreLayout) {
            return
        }
        mLayoutState.mRecycle = false
        //在布局之前，将所有的子View先Detach掉，放入到Scrap缓存中
        Log.d("jihongwen", "onLayoutChildren")
        val extra = getExtraLayoutSpace(state)
        Log.d("jihongwen", "onLayoutChildren extra=$extra")
        detachAndScrapAttachedViews(recycler)
        // 定义竖直方向的偏移量
        updateLayoutState()
        mLayoutState.mInfinite = resolveIsInfinite()
        fill(recycler, mLayoutState, state)
    }


    private fun getExtraLayoutSpace(state: RecyclerView.State): Int {
        return if (state.hasTargetScrollPosition()) {
            mOrientationHelper.totalSpace
        } else {
            0
        }
    }

    /**
     * @param layoutDirection 布局方向
     * @param requiredSpace 请求空间
     * @param canUseExistingSpace  已存在的空间是否可用
     * @param state
     */
    private fun updateLayoutState(layoutDirection: Int, requiredSpace: Int,
                                  canUseExistingSpace: Boolean, state: RecyclerView.State) {
        mLayoutState.mInfinite = resolveIsInfinite()
        mLayoutState.mExtra = getExtraLayoutSpace(state)
        mLayoutState.mLayoutDirection = layoutDirection
        var scrollingOffset: Int
        if (layoutDirection == LayoutState.LAYOUT_END) {
            mLayoutState.mExtra = mOrientationHelper.endPadding
            // 布局方向上第一个子view
            val child: View? = getChildClosestToEnd()
            mLayoutState.mItemDirection = if (mShouldReverseLayout) LayoutState.ITEM_DIRECTION_HEAD else LayoutState.ITEM_DIRECTION_TAIL
            mLayoutState.mCurrentPosition = getPosition(child) + mLayoutState.mItemDirection
            mLayoutState.mOffset = mOrientationHelper.getDecoratedEnd(child)
            scrollingOffset = mOrientationHelper.getDecoratedEnd(child) - mOrientationHelper.endAfterPadding
        } else {
            val child: View? = getChildClosestToStart()
            mLayoutState.mExtra = mOrientationHelper.startAfterPadding
            mLayoutState.mItemDirection = if (mShouldReverseLayout) LayoutState.ITEM_DIRECTION_TAIL else LayoutState.ITEM_DIRECTION_HEAD
            mLayoutState.mCurrentPosition = getPosition(child) + mLayoutState.mItemDirection
            mLayoutState.mOffset = mOrientationHelper.getDecoratedStart(child)
            scrollingOffset = -mOrientationHelper.getDecoratedStart(child) + mOrientationHelper.startAfterPadding
        }
        mLayoutState.mAvailable = requiredSpace
        if (canUseExistingSpace) {
            mLayoutState.mAvailable -= scrollingOffset
        }
        mLayoutState.mScrollingOffset = scrollingOffset
    }

    private fun getChildClosestToStart(): View {
        return getChildAt(if (mShouldReverseLayout) childCount - 1 else 0)
    }

    private fun getChildClosestToEnd(): View {
        return getChildAt(if (mShouldReverseLayout) 0 else childCount - 1)
    }

    private fun updateLayoutState() {
        mLayoutState.mAvailable = mOrientationHelper.endAfterPadding
        mLayoutState.mOffset = 0
    }

    fun fill(recycler: RecyclerView.Recycler, layoutState: LayoutState, state: RecyclerView.State): Int {
        val start = layoutState.mAvailable
        var remainingSpace = mLayoutState.mAvailable + mLayoutState.mExtra

        while (remainingSpace > 0 && layoutState.hasMore(state)) {
            var child = mLayoutState.next(recycler)
            addView(child)
            measureChildWithMargins(child, 0, 0)
            val consumed = mOrientationHelper.getDecoratedMeasurement(child)
            var left: Int = 0
            var top: Int = mLayoutState.mOffset
            var right: Int = width
            var bottom: Int
            bottom = mLayoutState.mOffset + consumed
            layoutDecorated(child, left, top, right, bottom)
            mLayoutState.mOffset += consumed * mLayoutState.mLayoutDirection
            remainingSpace -= consumed
            mLayoutState.mAvailable -= consumed

            if (layoutState.mScrollingOffset != LayoutState.SCROLLING_OFFSET_NaN) {
                layoutState.mScrollingOffset += consumed
                if (layoutState.mAvailable < 0) {
                    layoutState.mScrollingOffset += layoutState.mAvailable
                }
                recycleByLayoutState(recycler, layoutState)
            }

            Log.d("jihongwen", "mLayoutState.mAvailable " + mLayoutState.mAvailable)
        }
        return start - layoutState.mAvailable
    }

    private fun recycleByLayoutState(recycler: RecyclerView.Recycler, layoutState: LayoutState) {
        if (!layoutState.mRecycle || layoutState.mInfinite) {
            return
        }
        if (layoutState.mLayoutDirection == LayoutState.LAYOUT_START) {
            recycleViewsFromEnd(recycler, layoutState.mScrollingOffset)
        } else {
            recycleViewsFromStart(recycler, layoutState.mScrollingOffset)
        }
    }

    private fun recycleViewsFromEnd(recycler: RecyclerView.Recycler, dt: Int) {
        val childCount = childCount
        if (dt < 0) {
            return
        }
        val limit = mOrientationHelper.end - dt
        if (mShouldReverseLayout) {
            for (i in 0 until childCount) {
                val child = getChildAt(i)
                if (mOrientationHelper.getDecoratedStart(child) < limit || mOrientationHelper.getTransformedStartWithDecoration(child) < limit) {
                    // stop here
                    recycleChildren(recycler, 0, i)
                    return
                }
            }
        } else {
            for (i in childCount - 1 downTo 0) {
                val child = getChildAt(i)
                if (mOrientationHelper.getDecoratedStart(child) < limit || mOrientationHelper.getTransformedStartWithDecoration(child) < limit) {
                    // stop here
                    recycleChildren(recycler, childCount - 1, i)
                    return
                }
            }
        }
    }

    private fun recycleChildren(recycler: RecyclerView.Recycler, startIndex: Int, endIndex: Int) {
        if (startIndex == endIndex) {
            return
        }

        Log.d("jihongwen", "Recycling " + Math.abs(startIndex - endIndex) + " items")
        if (endIndex > startIndex) {
            for (i in endIndex - 1 downTo startIndex) {
                removeAndRecycleViewAt(i, recycler)
            }
        } else {
            for (i in startIndex downTo endIndex + 1) {
                removeAndRecycleViewAt(i, recycler)
            }
        }
    }

    private fun recycleViewsFromStart(recycler: RecyclerView.Recycler, dt: Int) {
        if (dt < 0) {
            return
        }
        // ignore padding, ViewGroup may not clip children.
        val childCount = childCount
        if (mShouldReverseLayout) {
            for (i in childCount - 1 downTo 0) {
                val child = getChildAt(i)
                if (mOrientationHelper.getDecoratedEnd(child) > dt || mOrientationHelper.getTransformedEndWithDecoration(child) > dt) {
                    // stop here
                    recycleChildren(recycler, childCount - 1, i)
                    return
                }
            }
        } else {
            for (i in 0 until childCount) {
                val child = getChildAt(i)
                if (mOrientationHelper.getDecoratedEnd(child) > dt || mOrientationHelper.getTransformedEndWithDecoration(child) > dt) {
                    // stop here
                    recycleChildren(recycler, 0, i)
                    return
                }
            }
        }
    }

    override fun canScrollVertically(): Boolean {
        return true
    }

    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler, state: RecyclerView.State): Int {
        return scrollBy(dy, recycler, state)
    }

    private fun scrollBy(dy: Int, recycler: RecyclerView.Recycler, state: RecyclerView.State): Int {
        // 实际要滑动的距离
        if (childCount == 0 || dy == 0) {
            return 0
        }
        mLayoutState.mRecycle = true
        val layoutDirection = if (dy > 0) LayoutState.LAYOUT_END else LayoutState.LAYOUT_START
        val absDy = Math.abs(dy)
        updateLayoutState(layoutDirection, absDy, true, state)
        Log.d("jihongwen", "mLayoutState: $mLayoutState")

        val consumed = mLayoutState.mScrollingOffset + fill(recycler, mLayoutState, state)
        if (consumed < 0) {
            return 0
        }
        val scrolled = if (absDy > consumed) layoutDirection * consumed else dy
        mOrientationHelper.offsetChildren(-scrolled)
        mLayoutState.mLastScrollDelta = scrolled
        return scrolled
    }

    internal fun resolveIsInfinite(): Boolean {
        return mOrientationHelper.mode == View.MeasureSpec.UNSPECIFIED && mOrientationHelper.end == 0
    }

    fun getVerticalSpace(): Int {
        return height - paddingBottom - paddingTop
    }

    fun getHorizontalSpace(): Int {
        return width - paddingRight - paddingLeft
    }

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    class LayoutState {

        companion object {
            val SCROLLING_OFFSET_NaN = Integer.MIN_VALUE

            // 布局方向
            val LAYOUT_START = -1
            // 布局方向
            val LAYOUT_END = 1

            val ITEM_DIRECTION_HEAD = -1
            val ITEM_DIRECTION_TAIL = 1
        }

        //

        var mRecycle = true
        var mInfinite = false
        var mOffset = 0
        var mExtra = 0
        var mAvailable = 0
        var mScrollingOffset = 0
        var mCurrentPosition = 0
        var mLastScrollDelta = 0

        var mItemDirection = ITEM_DIRECTION_TAIL
        var mLayoutDirection = LAYOUT_END

        fun hasMore(state: RecyclerView.State): Boolean {
            return mCurrentPosition >= 0 && mCurrentPosition < state.itemCount
        }

        fun next(recycler: RecyclerView.Recycler): View {
            Log.d("jihongwen", "next mCurrentPosition=$mCurrentPosition")
            val view = recycler.getViewForPosition(mCurrentPosition)
            mCurrentPosition += mItemDirection
            return view
        }

        override fun toString(): String {
            return "LayoutState(mOffset=$mOffset, mExtra=$mExtra, mAvailable=$mAvailable, mScrollingOffset=$mScrollingOffset, mCurrentPosition=$mCurrentPosition, mLastScrollDelta=$mLastScrollDelta, mItemDirection=$mItemDirection, mLayoutDirection=$mLayoutDirection)"
        }

    }
}
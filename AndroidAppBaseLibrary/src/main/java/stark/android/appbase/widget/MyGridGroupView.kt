package stark.android.appbase.widget

import android.animation.ArgbEvaluator
import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.content.ClipData
import android.content.Context
import android.graphics.*
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.view.DragEvent.*
import android.view.View.*
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.TranslateAnimation
import android.widget.Adapter
import android.widget.Scroller

open class MyGridGroupView : ViewGroup {

    private val mTouchSlop = ViewConfiguration.get(context).scaledPagingTouchSlop
    private val mMaxFlingVelocity = ViewConfiguration.get(context).scaledMaximumFlingVelocity
    private val mMinFlingVelocity = ViewConfiguration.get(context).scaledMinimumFlingVelocity
    private var velocityTracker = VelocityTracker.obtain()

    private var shadowColor = Color.parseColor("#FFFFFF")
    //private var itemViewBgColor = Color.parseColor("#FF669900")
    private var itemViewBgColor = Color.TRANSPARENT
    private var itemViewSelectedBgColor = Color.parseColor("#FF7DB808")

    private var mAdapter: Adapter? = null
    private var mViewConfig = ViewConfig(context)
    private var scroller = Scroller(context)
    private var tempIndexList: IntArray? = null // 保存移动后的位置
    private var startX = 0f
    private var childStartX = 0f
    private var childStartY = 0f
    private var page = 0
    private var currentPage = 0
    private var edge = 0
    private var dragTime = System.currentTimeMillis()
    private var dragChildIndex = 0
    private var isHandled = true

    private var childOnTouchListener: OnTouchListener? = null
    private var childOnDragListener: OnDragListener? = null
    private var childOnLongClickListener: OnLongClickListener? = null

    private var onItemClickListener: OnItemClickListener? = null
    private var onPageChangeListener: OnPageChangeListener? = null
    private var onItemMovedListener: OnItemMovedListener? = null
    private var onItemExchangedListener: OnItemExchangedListener? = null

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr) {

        setOnTouchListener { v, event ->
            return@setOnTouchListener true
        }

        edge = -1

        childOnTouchListener = OnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    childStartX = event.x
                    childStartY = event.y
                    Log.d("jihongwen", "childOnTouchListener $childStartX $childStartY")
                }
            }
            false
        }

        childOnDragListener = OnDragListener { v, event ->
            when (event.action) {
                ACTION_DRAG_ENTERED -> {// 当拖拽影子刚进入View对象的边框时，View对象的拖拽事件监听器会接收这种事件操作类型。
                    val view = event.localState as View
                    changePosition(getChildViewIndex(view), getChildViewIndex(v))
                }
                ACTION_DROP -> {// 当用户在一个View对象之上释放了拖拽影子，这个对象的拖拽事件监听器就会收到这种操作类型。如果这个监听器在响应ACTION_DRAG_STARTED拖拽事件中返回了true，那么这种操作类型只会发送给一个View对象。如果用户在没有被注册监听器的View对象上释放了拖拽影子，或者用户没有在当前布局的任何部分释放操作影子，这个操作类型就不会被发送。如果View对象成功的处理放下事件，监听器要返回true，否则应该返回false。
                    val x = event.x
                    val y = event.y
                    val oriView = event.localState as View
                    val oriIndex = getChildViewIndex(oriView)
                    // 这时oriView 已经移动到新的位置了
                    moveToWithAnimation(x, y, oriView, oriIndex)
                    Log.d("jihongwen", "ACTION_DROP")
                }
                ACTION_DRAG_LOCATION -> {// 在View对象收到一个ACTION_DRAG_ENTERED事件之后，并且拖拽影子依然还在这个对象的边框之内时，这个View对象的拖拽事件监听器会接收这种事件操作类型
                    // 如果拖动出当前界面一定范围，跳到另一个page
                    val currentEdge = getEdgeState(getChildViewIndex(event.localState as View), event.x.toInt())
                    Log.d("jihongwen", "currentEdge = $currentEdge")
                    if (edge == -1) {
                        // 没有靠近边缘
                        edge = currentEdge
                        dragTime = System.currentTimeMillis()
                        dragChildIndex = getChildViewIndex(v)
                    } else {
                        if (currentEdge != edge) {
                            edge = -1
                            return@OnDragListener true
                        }
                        if (dragChildIndex != getChildViewIndex(v)) {
                            edge = -1
                            return@OnDragListener true
                        }
                        // 悬停超过800毫秒
                        if (System.currentTimeMillis() - dragTime >= 700L) {
                            if (currentEdge == 0) {
                                // left edge
                                setCurrentPage(currentPage - 1)
                                Log.d("jihongwen", "left edge")
                            } else {
                                // right edge
                                setCurrentPage(currentPage + 1)
                                Log.d("jihongwen", "right edge")
                            }
                            edge = -1
                        }
                    }

                }
                ACTION_DRAG_ENDED -> {// 当系统结束拖拽操作时，View对象拖拽监听器会接收这种事件操作类型。这种操作类型之前不一定是ACTION_DROP事件。如果系统发送了一个ACTION_DROP事件，那么接收ACTION_DRAG_ENDED操作类型不意味着放下操作成功了。监听器必须调用getResult()方法来获得响应ACTION_DROP事件中的返回值。如果ACTION_DROP事件没有被发送，那么getResult()会返回false。
                    val view = event.localState as View
                    view.alpha = 1.0f
                    view.setBackgroundColor(itemViewBgColor)
                    val vIndex = getChildViewIndex(v)
                    val viewIndex = getChildViewIndex(view)
                    if (vIndex == viewIndex)
                        onItemMovedListener?.onItemMoved()
                    Log.d("jihongwen", "ACTION_DRAG_ENDED vIndex=$vIndex viewIndex=$viewIndex")
                }
            }
            return@OnDragListener true
        }

        childOnLongClickListener = OnLongClickListener {
            it.startDrag(ClipData.newPlainText("", ""), DragShadowBuilder(it, childStartX, childStartY, getContext()), it, DRAG_FLAG_OPAQUE)
            it.alpha = 0.0f
            return@OnLongClickListener true
        }
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
        for (i in 0 until childCount) {
            getChildAt(i).setOnClickListener {
                onItemClickListener.onItemClick(it, i)
            }
        }
    }

    fun setOnItemExchangedListener(onItemExchangedListener: OnItemExchangedListener) {
        this.onItemExchangedListener = onItemExchangedListener
    }

    fun setOnPageChangeListener(onPageChangeListener: OnPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener
    }

    fun setOnItemMovedListener(onItemMovedListener: OnItemMovedListener) {
        this.onItemMovedListener = onItemMovedListener
    }

    fun setAdapter(adapter: Adapter) {
        removeAllViews()
        tempIndexList = null
        mAdapter = adapter
        if (mAdapter != null) {
            tempIndexList = IntArray(mAdapter!!.count)
            for (i in 0 until mAdapter!!.count) {
                val view = mAdapter!!.getView(i, null, this)
                view.setOnTouchListener(childOnTouchListener)
                view.setOnLongClickListener(childOnLongClickListener)
                view.setOnDragListener(childOnDragListener)
                if (onItemClickListener != null) {
                    view.setOnClickListener {
                        onItemClickListener?.onItemClick(it, getChildViewIndex(it))
                    }
                }
                addView(view)
                tempIndexList!![i] = i
            }
        }
        page = Math.ceil((childCount / mViewConfig.pageViewCounts).toDouble()).toInt()
    }

    fun getAdapter(): Adapter? {
        return mAdapter
    }

    fun setCurrentPage() {
        var dx = getScrollDx()
        scroller.startScroll(scroller.finalX, scroller.finalY, dx, 0, 350)
        mViewConfig.dxOffset = 0
        invalidate()
    }

    fun setCurrentPage(target: Int) {
        Log.d("jihongwen", "setCurrentPage target=$target")
        if (target in 0..page) {
            currentPage = target
            scroller.startScroll(scroller.finalX, scroller.finalY, mViewConfig.parentWidth * target - scroller.finalX, 0, 350)
            mViewConfig.dxOffset = 0
            invalidate()
            onPageChangeListener?.onPageChange(target)
        }
    }

    fun getTempListIndex(): IntArray? {
        return tempIndexList
    }

    private fun getScrollDx(): Int {
        val offset = Math.abs(mViewConfig.dxOffset)
        var dx = -mViewConfig.dxOffset
        if (offset > mViewConfig.parentWidth / 8) {
            if (mViewConfig.dxOffset > 0) {
                currentPage++
                dx = mViewConfig.parentWidth - mViewConfig.dxOffset
            } else {
                currentPage--
                dx = -(mViewConfig.parentWidth - offset)
            }
        }
        return dx
    }

    private fun getNextPage(): Int {
        val offset = Math.abs(mViewConfig.dxOffset)
        if (offset > mViewConfig.parentWidth / 8) {
            if (mViewConfig.dxOffset > 0) {
                currentPage++
            } else {
                currentPage--
            }
        }
        return currentPage
    }

    private fun dropMoveAnim(n: Int, n2: Int, view: View) {
        val rect = mViewConfig.getChildRect(n)
        val rect2 = mViewConfig.getChildRect(n2)
        view.layout(rect2.left, rect2.top, rect2.right, rect2.bottom)
        rect.offset(-view.left, -view.top)
        rect2.offset(-view.left, -view.top)
        val translateAnimation = TranslateAnimation(rect.left.toFloat(), rect2.left.toFloat(), rect.top.toFloat(), rect2.top.toFloat())
        translateAnimation.duration = 250L
        translateAnimation.isFillEnabled = true
        translateAnimation.fillAfter = true
        translateAnimation.interpolator = DecelerateInterpolator()
        view.clearAnimation()
        view.startAnimation(translateAnimation as Animation)
    }

    // 移动动画，松手时移动到指定位置
    private fun moveToWithAnimation(x: Float, y: Float, view: View, index: Int) {
        if (index < 0 || index >= this.childCount) {
            return
        }
        val rect = mViewConfig.getChildRect(index)
        rect.offset(-view.left, -view.top)
        val translateAnimation = TranslateAnimation((rect.left - (this.childStartX - x)), rect.left.toFloat(), (rect.top + (y - this.childStartY)), rect.top.toFloat())
        translateAnimation.duration = 250L
        translateAnimation.isFillEnabled = true
        translateAnimation.fillAfter = true
        translateAnimation.interpolator = DecelerateInterpolator()
        view.clearAnimation()
        val valueAnimator = ValueAnimator.ofObject(ArgbEvaluator() as TypeEvaluator<*>, *arrayOf<Any>(itemViewSelectedBgColor, itemViewBgColor))
        valueAnimator.duration = 250L
        valueAnimator.addUpdateListener { it ->
            view.setBackgroundColor(it.animatedValue as Int)
        }
        view.startAnimation(translateAnimation as Animation)
        valueAnimator.start()
    }

    private fun changePosition(startIndex: Int, endIndex: Int) {
        if (startIndex != endIndex && startIndex < this.childCount && startIndex >= 0 && endIndex >= 0 && endIndex < this.childCount) {
            val startView = this.getChildAt(this.tempIndexList!![startIndex])
            val rect = mViewConfig.getChildRect(endIndex)
            startView.layout(rect.left, rect.top, rect.right, rect.bottom)
            for (i in 0 until this.childCount) {
                if (startIndex < endIndex) { // 从前往后移动
                    if (i in (startIndex + 1)..endIndex) {
                        this.dropMoveAnim(i, i - 1, this.getChildAt(this.tempIndexList!![i]))
                        this.changeIndex(i, i - 1)
                    }
                } else { // 从后往前移动
                    val n3 = this.childCount - 1 - i
                    if (n3 in endIndex..(startIndex - 1)) {
                        this.dropMoveAnim(n3, n3 + 1, this.getChildAt(this.tempIndexList!![n3]))
                        this.changeIndex(n3, n3 + 1)
                    }
                }
            }
        }
    }

    private fun changeIndex(index1: Int, index2: Int) {
        val tempIndex = this.tempIndexList!![index1]
        this.tempIndexList!![index1] = this.tempIndexList!![index2]
        this.tempIndexList!![index2] = tempIndex
        onItemExchangedListener?.onItemExchanged(index1, index2)
    }

    /**
     * 0 left
     * 1 right
     * -1 not edge
     */
    private fun getEdgeState(oriIndex: Int, oriX: Int): Int {
        val halfWidth = mViewConfig.itemWidth / 3
        val rows = mViewConfig.rows
        val isInRightViews: Boolean
        val isInLeftViews: Boolean
        isInRightViews = oriIndex % rows == rows - 1 // 2,5,8 靠右
        isInLeftViews = oriIndex % rows == 0 // 0,3,6 靠左

        // 接近左侧边缘
        if (oriX < halfWidth && isInLeftViews) {
            Log.d("jihongwen", "arrived the edge of left oriX=$oriX")
            return 0
        }
        // 接近右侧边缘
        if (oriX >= mViewConfig.itemWidth - halfWidth && isInRightViews) {
            Log.d("jihongwen", "arrived the edge of right oriX=$oriX")
            return 1
        }
        // 没有接近边缘
        return -1
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = View.MeasureSpec.getSize(widthMeasureSpec)
        val height = View.MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val childCount = childCount
        mViewConfig.setParentSize(measuredWidth, height)
        for (i in 0 until childCount) {
            val child = getChildAt(tempIndexList!![i])
            val rect = mViewConfig.getChildRect(i)
            child.measure(MeasureSpec.makeMeasureSpec(rect.width(), MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(rect.height(), MeasureSpec.EXACTLY))
            child.layout(rect.left, rect.top, rect.right, rect.bottom)
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_MOVE -> {
                return !isHandled
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                scroller.abortAnimation()
                startX = ev.x
            }
            MotionEvent.ACTION_MOVE -> {
                velocityTracker.addMovement(ev)
                velocityTracker.computeCurrentVelocity(1000, mMaxFlingVelocity.toFloat())
                val xv = velocityTracker.xVelocity
                val dx = -xv.toInt() / 100
                move(dx)
                if (Math.abs(ev.x - startX) > mTouchSlop)
                    isHandled = false
            }
            MotionEvent.ACTION_UP -> {
                velocityTracker.addMovement(ev)
                velocityTracker.computeCurrentVelocity(1000, mMaxFlingVelocity.toFloat())
                setCurrentPage(getNextPage())
                // todo 回收 velocityTracker
                isHandled = true
            }
        }

        return super.dispatchTouchEvent(ev)
    }

    fun getChildViewIndex(view: View): Int {
        for (i in 0 until tempIndexList!!.size) {
            val child = getChildAt(tempIndexList!![i])
            if (child == view) {
                return i
            }
        }
        return -1
    }

    private fun move(dx: Int) {
        var offset = mViewConfig.dxOffset
        if (dx == 0) {
            return
        }
        offset
        // todo 判断有问题,
        if (currentPage == 0) {
            // 如果在最左边的page，不能再往左滑动
            if ((scroller.finalX + mViewConfig.parentWidth) < mViewConfig.parentWidth)
                return
        }
        if (currentPage == page) {
            if ((scroller.finalX + mViewConfig.parentWidth) > mViewConfig.parentWidth * (page + 1))
                return
        }
        // 如果在最右边的page,不能再往右滑动
        Log.d("jihongwen", "finalX =${scroller.finalX}  dx=$dx")
        Log.d("jihongwen", "scrollX =$scrollX  dx=$dx")
        scrollX
        mViewConfig.dxOffset += dx
        scroller.startScroll(scroller.finalX, scroller.finalY, dx, 0, 1000)
        invalidate()
    }

    override fun computeScroll() {
        if (this.scroller.computeScrollOffset()) {
            val scrollX = this.scrollX
            val scrollY = this.scrollY
            val currX = scroller.currX
            val currY = scroller.currY
            if (scrollX != currX || scrollY != currY) {
                //Log.d("jihongwen", "scrollX=$scrollX scrollY$scrollY currX=$currX currY=$currY")
                this.scrollTo(currX, currY)
            }
            ViewCompat.postInvalidateOnAnimation(this)
        }
        super.computeScroll()
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, index: Int)
    }

    interface OnItemExchangedListener {
        fun onItemExchanged(item1: Int, item2: Int)
    }

    interface OnItemMovedListener {
        fun onItemMoved()
    }

    interface OnPageChangeListener {
        fun onPageChange(page: Int)
    }

    class DragShadowBuilder(view: View, val startX: Float, val startY: Float, val context: Context) : View.DragShadowBuilder(view) {

        var shadowColor = Color.parseColor("#FFFFFF")
        private var itemViewBgColor = Color.parseColor("#FF669900")
        private var itemViewSelectedBgColor = Color.parseColor("#FF7DB808")

        val p1 = Paint()
        val p2 = Paint()

        override fun onDrawShadow(canvas: Canvas) {
            val view = this.view
            if (view != null) {
                val rect = Rect()
                view.getDrawingRect(rect)
                val rectF = RectF(rect)
                this.p1.setShadowLayer(50.0f, 0.0f, 0.0f, shadowColor)
                canvas.translate(100.0f, 100.0f)
                canvas.drawRoundRect(rectF, 0.0f, 0.0f, this.p1)
                // view selected
                view.setBackgroundColor(itemViewSelectedBgColor)
                view.draw(canvas)
                --rect.right
                --rect.bottom
                this.p2.color = itemViewSelectedBgColor
                this.p2.style = Paint.Style.STROKE
                this.p2.strokeWidth = 1.0f
                canvas.drawRect(rect, this.p2)
                return
            }
            Log.e("View", "Asked to draw drag shadow but no view")
        }

        override fun onProvideShadowMetrics(point: Point, point2: Point) {
            super.onProvideShadowMetrics(point, point2)
            point.set((point.x * 1.6f).toInt(), (point.y * 1.6f).toInt())
            point2.set((this.startX + 100).toInt(), (this.startY + 100).toInt())
        }

    }

    class ViewConfig(context: Context) {
        var rows = 3
        var columns = 3
        var pageViewCounts = rows * columns
        var parentWidth = context.resources.displayMetrics.widthPixels
        var parentHeight = context.resources.displayMetrics.heightPixels
        var itemWidth = parentWidth / rows
        var itemHeight = parentHeight / columns

        var dxOffset = 0

        fun inFirstPage(): Boolean {
            return true
        }

        fun setParentSize(width: Int, height: Int) {
            parentWidth = width
            parentHeight = height
            itemWidth = parentWidth / rows
            itemHeight = parentHeight / columns
        }

        fun getChildRect(i: Int): Rect {
            // 超过 rows * columns 就是下一页
            var page = i / (rows * columns)
            var rowIndex = i / rows % rows
            Log.d("jihongwen", "rowIndex=$rowIndex page=$page")
            val left = itemWidth * (i % columns) + parentWidth * page
            val top = itemHeight * rowIndex
            val right = left + itemWidth
            val bottom = top + itemHeight
            Log.d("jihongwen", "left=$left top=$top right=$right bottom$bottom")
            return Rect(left, top, right, bottom)
        }
    }
}
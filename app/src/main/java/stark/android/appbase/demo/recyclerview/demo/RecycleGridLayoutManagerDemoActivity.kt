package stark.android.appbase.demo.recyclerview.demo

import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.support.v7.widget.helper.ItemTouchHelper.ACTION_STATE_IDLE
import android.util.Log
import kotlinx.android.synthetic.main.activity_recycler_grid_layout_manager_demo.*
import stark.android.appbase.activity.BaseToolbarActivity
import stark.android.appbase.activity.setToolbar
import stark.android.appbase.demo.R


class RecycleGridLayoutManagerDemoActivity : BaseToolbarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_grid_layout_manager_demo)
        setToolbar("LayoutManager Demo", true)
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = RecycleGridLayoutManagerDemoAdapter(this).apply {
            setItems(ArrayList<String>().apply {
                for (i in 0..15) {
                    add("$i item")
                }
            })
        }

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun isLongPressDragEnabled(): Boolean {
                return true
            }

            override fun onChildDraw(c: Canvas?, recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                if (!isCurrentlyActive) {
                    viewHolder?.itemView?.setBackgroundColor(ContextCompat.getColor(this@RecycleGridLayoutManagerDemoActivity, R.color.demo_grid_item_color))
                }
                Log.d("jihongwen", "onChildDraw dX=$dX dY=$dY actionState=$actionState isCurrentlyActive=$isCurrentlyActive")
            }

            override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
                super.onSelectedChanged(viewHolder, actionState)
                if (ACTION_STATE_IDLE != actionState) {
                    viewHolder?.itemView?.setBackgroundColor(ContextCompat.getColor(this@RecycleGridLayoutManagerDemoActivity, R.color.demo_grid_item_selected_color))
                }
            }

            override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder?): Int {
                return if (recyclerView.layoutManager is GridLayoutManager || recyclerView.layoutManager is StaggeredGridLayoutManager) {
                    val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                    //不需要滑动
                    val swipeFlags = 0
                    makeMovementFlags(dragFlags, swipeFlags)
                } else {
                    val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
                    val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
                    makeMovementFlags(dragFlags, swipeFlags)
                }
            }

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                if (viewHolder.itemViewType != target.itemViewType) {
                    return false
                }
                // Notify the adapter of the move
                with(recyclerView.adapter as RecycleGridLayoutManagerDemoAdapter) {
                    onItemMove(viewHolder.adapterPosition, target.adapterPosition)
                }
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                with(recyclerView.adapter as RecycleGridLayoutManagerDemoAdapter) {
                    onItemDissmiss(viewHolder.adapterPosition)
                }
            }
        })

        itemTouchHelper.attachToRecyclerView(recyclerView)

    }
}
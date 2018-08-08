package stark.android.appbase.demo.recyclerview.demo

interface ItemTouchHelperAdapter {
    //数据交换
    fun onItemMove(fromPosition: Int, toPosition: Int)

    //数据删除
    fun onItemDissmiss(position: Int)
}
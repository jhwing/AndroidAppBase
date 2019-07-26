package stark.android.appbase.demo.recyclerview.demo

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_grid_view_list.*
import stark.android.appbase.activity.BaseToolbarActivity
import stark.android.appbase.demo.R
import stark.android.appbase.recycleview.BaseRecycleViewAdapter
import stark.android.appbase.recycleview.BaseRecycleViewHolder

class GridImageItemActivity : BaseToolbarActivity() {

    var mAdapter: MyAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grid_view_list)
        gridImageList.layoutManager = LinearLayoutManager(this)
        mAdapter = MyAdapter(this)
        gridImageList.adapter = mAdapter
        getItems()
    }

    private fun getItems() {
        var items = ArrayList<GridItem>()
        items.add(GridItem(listOf(
                "http://img.pptjia.com/image/20180117/16e6d1b2adcb7796e411899957f5f686.jpg",
                "http://img.pptjia.com/image/20180117/767f4b74a8d7b453b149430ee364c9ce.jpg",
                "http://img.pptjia.com/image/20180117/f4b76385a3ccdbac48893cc6418806d5.jpg",
                "https://p9.pstatp.com/weili/l/79054052830405574.webp",
                "http://h.hiphotos.baidu.com/image/pic/item/2e2eb9389b504fc292c1b0cfecdde71191ef6d59.jpg",
                "http://a.hiphotos.baidu.com/image/pic/item/060828381f30e924a0be504446086e061d95f7a9.jpg",
                "http://b.hiphotos.baidu.com/image/pic/item/18d8bc3eb13533faf3b993e4a1d3fd1f40345be8.jpg",
                "http://b.hiphotos.baidu.com/image/pic/item/a5c27d1ed21b0ef4e2a263d4d0c451da80cb3ef6.jpg")))
        items.add(GridItem(listOf("http://e.hiphotos.baidu.com/image/pic/item/500fd9f9d72a6059099ccd5a2334349b023bbae5.jpg")))
        items.add(GridItem(listOf("http://c.hiphotos.baidu.com/image/pic/item/d439b6003af33a87b379cc6fcc5c10385343b5ab.jpg")))
        items.add(GridItem(listOf("http://d.hiphotos.baidu.com/image/pic/item/d4628535e5dde711db4f433caeefce1b9c16615e.jpg")))
        items.add(GridItem(listOf("http://b.hiphotos.baidu.com/image/pic/item/3bf33a87e950352ae7ac25c25a43fbf2b3118b2e.jpg")))
        items.add(GridItem(listOf("http://e.hiphotos.baidu.com/image/pic/item/4bed2e738bd4b31c53eb94718ed6277f9f2ff821.jpg")))

        items.add(GridItem(listOf("http://c.hiphotos.baidu.com/image/pic/item/1ad5ad6eddc451dafb29685abbfd5266d11632a7.jpg", "http://a.hiphotos.baidu.com/image/pic/item/0bd162d9f2d3572cc94ceb968713632763d0c3ca.jpg")))

        mAdapter?.setItems(items)
    }

    class GridItem(var urls: List<String>)

    class MyAdapter(context: Context) : BaseRecycleViewAdapter<GridItem>(context) {
        override fun onCreateViewHolderDelegate(parent: ViewGroup, viewType: Int): BaseRecycleViewHolder<GridItem> {
            return MyViewHolder(mInflater.inflate(R.layout.item_grid_image_item_view, parent, false))
        }


        class MyViewHolder(var view: View) : BaseRecycleViewHolder<GridItem>(view) {

            private var gridImageView = view.findViewById<GridImageView>(R.id.gridImageView)

            override fun bindTo(t: GridItem) {
                gridImageView.setImages(t.urls)
            }

        }

    }
}
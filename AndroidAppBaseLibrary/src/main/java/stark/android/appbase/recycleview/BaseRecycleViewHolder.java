package stark.android.appbase.recycleview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class BaseRecycleViewHolder<T> extends RecyclerView.ViewHolder {

    public BaseRecycleViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bindTo(T t);

}

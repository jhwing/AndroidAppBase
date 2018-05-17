package stark.android.appbase.recycleview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import stark.android.appbase.R;


public class BaseRecycleViewEmptyHolder<T> extends BaseRecycleViewHolder<T> {
    public BaseRecycleViewEmptyHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bindTo(T t) {
    }

    public static <T> BaseRecycleViewEmptyHolder<T> create(LayoutInflater layoutInflater, ViewGroup parent) {
        return new BaseRecycleViewEmptyHolder<>(layoutInflater.inflate(R.layout.base_recycle_view_empty_item, parent, false));
    }
}

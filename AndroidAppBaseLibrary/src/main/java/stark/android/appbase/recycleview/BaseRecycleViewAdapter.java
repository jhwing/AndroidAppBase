package stark.android.appbase.recycleview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class BaseRecycleViewAdapter<T> extends RecyclerView.Adapter<BaseRecycleViewHolder<T>> {

    protected List<T> items;

    protected LayoutInflater mInflater;

    public BaseRecycleViewAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public void setItems(List<T> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void addItems(List<T> items) {
        items.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public BaseRecycleViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        final BaseRecycleViewHolder viewHolder = onCreateViewHolderDelegate(parent, viewType);
        return viewHolder;
    }

    public abstract BaseRecycleViewHolder<T> onCreateViewHolderDelegate(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(BaseRecycleViewHolder<T> holder, int position) {
        holder.bindTo(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }
}

package stark.android.appbase.base;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by jihongwen on 16/9/8.
 */

public abstract class BaseFragment<T> extends Fragment implements IView<T> {

    T mPresenter;

    @Override
    public void setPresenter(T presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }
}

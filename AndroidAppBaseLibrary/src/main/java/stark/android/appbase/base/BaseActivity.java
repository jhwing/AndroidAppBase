package stark.android.appbase.base;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by jihongwen on 16/9/8.
 */

public abstract class BaseActivity<T> extends AppCompatActivity implements IView<T> {

    T mPresenter;

    @Override
    public void setPresenter(T presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public Context getViewContext() {
        return this;
    }

    protected String getScreenName() {
        return getClass().getSimpleName();
    }

    public abstract void getIntentData();
}

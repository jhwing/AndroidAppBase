package stark.android.appbase.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import stark.android.appbase.R;

/**
 * Created by jihongwen on 16/8/4.
 */

public abstract class BaseToolbarActivity<T> extends BaseActivity<T> {

    Toolbar toolbar;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        setToolbar(toolbar);
    }

    @Override
    public void getIntentData() {

    }

    public void setToolbar(Toolbar toolbar) {

    }

    public abstract int getToolbarId();

}

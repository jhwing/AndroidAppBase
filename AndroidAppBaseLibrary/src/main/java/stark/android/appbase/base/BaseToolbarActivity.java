package stark.android.appbase.base;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import stark.android.appbase.R;

/**
 * Created by jihongwen on 16/8/4.
 */

public abstract class BaseToolbarActivity extends BaseActivity {

    Toolbar toolbar;

    TextView middleTitle;

    protected void setToolbar(CharSequence title) {
        setToolbar(title, false);
    }

    protected void setToolbar(CharSequence title, boolean isSub) {
        toolbar = findViewById(R.id.toolbar);
        middleTitle = findViewById(R.id.middleTitle);
        toolbar.setTitle("");
        if (middleTitle != null)
            middleTitle.setText(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(isSub);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

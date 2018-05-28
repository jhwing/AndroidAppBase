package stark.android.appbase.base;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by jihongwen on 16/9/8.
 */

public abstract class BaseActivity extends AppCompatActivity {


    public void startFragment(Fragment fragment, int resId) {
        getSupportFragmentManager().beginTransaction().add(resId, fragment).commit();
    }
}

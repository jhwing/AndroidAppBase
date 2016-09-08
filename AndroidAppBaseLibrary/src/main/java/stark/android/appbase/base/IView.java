package stark.android.appbase.base;

import android.content.Context;

/**
 * Created by jihongwen on 16/7/26.
 */

public interface IView<T> {

    void setPresenter(T presenter);

    Context getViewContext();
}

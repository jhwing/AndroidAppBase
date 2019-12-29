package stark.android.appbase.dialog;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import stark.android.appbase.R;


public class BaseDialogFragment extends DialogFragment {

    protected int mGravity = Gravity.CENTER;
    protected int mWidth = WindowManager.LayoutParams.MATCH_PARENT;
    protected int mHeight = WindowManager.LayoutParams.WRAP_CONTENT;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.SK_Base_Dialog);
    }

    public void setGravity(int gravity) {
        mGravity = gravity;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Window window = getDialog().getWindow();
        window.setGravity(mGravity);
        WindowManager.LayoutParams params = window.getAttributes();
        params.height = mHeight;
        params.width = mWidth;
        window.setAttributes(params);
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.getDecorView().setPadding(0, 0, 0, 0);
    }
}

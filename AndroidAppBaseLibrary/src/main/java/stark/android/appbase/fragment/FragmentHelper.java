package stark.android.appbase.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class FragmentHelper {

    String currentTag = "";

    FragmentFactory factory;

    public FragmentHelper(FragmentFactory factory) {
        this.factory = factory;
    }

    public void resume(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            currentTag = savedInstanceState.getString("currentTag", "");
        }
    }

    public void saveState(Bundle outState) {
        outState.putString("currentTag", currentTag);
    }

    public void changeFragment(String newTag, FragmentManager fragmentManager) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment oldFragment = fragmentManager.findFragmentByTag(currentTag);
        if (oldFragment != null) {
            transaction.hide(oldFragment);
            oldFragment.setUserVisibleHint(false);
        }
        currentTag = newTag;
        Fragment newFragment = fragmentManager.findFragmentByTag(currentTag);
        if (newFragment != null) {
            transaction.show(newFragment);
            newFragment.setUserVisibleHint(true);
        } else {
            newFragment = factory.newFragmentByTag(newTag);
            if (newFragment == null) {
                return;
            }
            transaction.add(factory.fragmentContainer(), newFragment, newTag);
            newFragment.setUserVisibleHint(true);
        }
        transaction.commitAllowingStateLoss();
    }

    public interface FragmentFactory {
        int fragmentContainer();

        Fragment newFragmentByTag(String tag);
    }
}

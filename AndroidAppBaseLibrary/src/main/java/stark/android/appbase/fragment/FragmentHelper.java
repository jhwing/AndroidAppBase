package stark.android.appbase.fragment;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentHelper {

    public String currentTag = "";

    FragmentFactory factory;

    public FragmentHelper(FragmentFactory factory) {
        this.factory = factory;
    }

    public String resume(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            this.currentTag = savedInstanceState.getString("currentTag", "");
            return this.currentTag;
        } else {
            return this.currentTag;
        }
    }

    public void saveState(Bundle outState) {
        outState.putString("currentTag", currentTag);
    }

    public void changeFragment(String newTag, FragmentManager fragmentManager, Bundle savedInstanceState) {
        String resumeTag = this.resume(savedInstanceState);
        if (TextUtils.isEmpty(resumeTag)) {
            resumeTag = newTag;
        }

        this.changeFragment(resumeTag, fragmentManager);
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

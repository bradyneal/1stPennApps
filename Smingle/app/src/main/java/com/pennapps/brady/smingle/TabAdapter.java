package com.pennapps.brady.smingle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by brady on 9/12/14.
 */
public class TabAdapter extends FragmentPagerAdapter {

    public static final int QUIZ_TAB = 0;
    public static final int CONTACT_TAB = 1;
    public static final int PROFILES_TAB = 2;

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment f = null;
        if (i == QUIZ_TAB) {
            f = new QuizFragment();
        } else if (i == CONTACT_TAB) {
            f = new AddContactFragment();
        } else if (i == PROFILES_TAB) {
            f = new ProfilesFragment();
        }
        return f;
    }

    @Override
    public int getCount() {
        return 3;
    }

}

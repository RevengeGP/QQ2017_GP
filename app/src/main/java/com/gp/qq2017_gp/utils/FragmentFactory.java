package com.gp.qq2017_gp.utils;

import android.support.v4.app.Fragment;

import com.gp.qq2017_gp.view.ContactFragment;
import com.gp.qq2017_gp.view.ConversationFragment;
import com.gp.qq2017_gp.view.PluginFragment;

/**
 * Created by GP on 2017/8/23.
 */

public class FragmentFactory {


    private static ConversationFragment sConversationFragment;
    private static ContactFragment sContactFragment;
    private static PluginFragment sPluginFragment;

    public static Fragment getFragment(int position) {
        Fragment baseFragment = null;
        switch (position) {
            case 0:
                if (sConversationFragment == null) {
                    sConversationFragment = new ConversationFragment();
                }
                baseFragment = sConversationFragment;
                break;
            case 1:
                if (sContactFragment == null) {
                    sContactFragment = new ContactFragment();
                }
                baseFragment = sContactFragment;
                break;
            case 2:
                if (sPluginFragment == null) {
                    sPluginFragment = new PluginFragment();
                }
                baseFragment = sPluginFragment;
                break;
        }
        return baseFragment;

    }

}

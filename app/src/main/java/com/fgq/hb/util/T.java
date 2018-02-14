package com.fgq.hb.util;

import android.support.annotation.StringRes;
import android.widget.Toast;

import com.fgq.hb.base.App;


public class T {

    private static Toast mToast;

    public static void show(@StringRes int resId) {
        show((App.getContext().getString(resId)));
    }

    public static void show(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(App.getContext(), text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
        }
        mToast.show();
    }


}

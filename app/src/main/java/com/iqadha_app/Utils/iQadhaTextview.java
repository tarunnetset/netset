package com.iqadha_app.Utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by netset on 15/11/16.
 */

public class iQadhaTextview extends TextView {

    public iQadhaTextview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public iQadhaTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public iQadhaTextview(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/ProximaNova-Regular.otf");
            setTypeface(tf);
        }
    }

}

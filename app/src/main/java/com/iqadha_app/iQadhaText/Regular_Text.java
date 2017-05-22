package com.iqadha_app.iQadhaText;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by netset on 17/11/16.
 */

public class Regular_Text extends TextView {

    public Regular_Text(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public Regular_Text(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Regular_Text(Context context) {
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
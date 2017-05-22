package com.iqadha_app.iQadhaText;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by netset on 17/11/16.
 */

public class Bold_text extends TextView {

    public Bold_text(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public Bold_text(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Bold_text(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/ProximaNova-Bold.otf");
            setTypeface(tf);
        }
    }
}

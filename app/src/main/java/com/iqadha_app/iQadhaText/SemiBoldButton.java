package com.iqadha_app.iQadhaText;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by netset on 17/11/16.
 */

public class SemiBoldButton extends Button {
    public SemiBoldButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public SemiBoldButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SemiBoldButton(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/ProximaNova-Semibold.otf");
            setTypeface(tf);
        }
    }
}

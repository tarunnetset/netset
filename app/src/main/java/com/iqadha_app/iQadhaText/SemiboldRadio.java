package com.iqadha_app.iQadhaText;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.RadioButton;

/**
 * Created by netset on 18/11/16.
 */

public class SemiboldRadio extends RadioButton {

    public SemiboldRadio(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public SemiboldRadio(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SemiboldRadio(Context context) {
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

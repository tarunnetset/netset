package com.iqadha_app.iQadhaText;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by netset on 17/11/16.
 */

public class SemiBold_edit extends EditText {

    public SemiBold_edit(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public SemiBold_edit(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SemiBold_edit(Context context) {
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

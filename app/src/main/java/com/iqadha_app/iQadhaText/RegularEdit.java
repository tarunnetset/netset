package com.iqadha_app.iQadhaText;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by netset on 17/11/16.
 */

public class RegularEdit extends EditText {

    public RegularEdit(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public RegularEdit(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RegularEdit(Context context) {
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

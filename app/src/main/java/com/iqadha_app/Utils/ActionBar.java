package com.iqadha_app.Utils;


import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iqadha_app.R;


/**
 * Created by netset on 6/4/16.
 */
public class ActionBar extends RelativeLayout {

    private Context mContext;
    private TextView menucancel, TitleTextView2;
    public static TextView editABTextView;
    ImageView menuButton, filterButton;
    RelativeLayout filterLay, EditLay, actionBarRelativeLayout;

    // RelativeLayout deleteLay, editLay;


    public ActionBar(Context context, AttributeSet attrs) {

        super(context, attrs);
        mContext = context;
        /*Typeface font = Typeface.createFromAsset(mContext.getAssets(),
                "bar.otf");
*/
        LayoutInflater mInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RelativeLayout rl = (RelativeLayout) mInflater.inflate(R.layout.actionbar, null);

        addView(rl);
        actionBarRelativeLayout = (RelativeLayout) rl.findViewById(R.id.actionBarRelativeLayout);
        menucancel = (TextView) rl.findViewById(R.id.menucancel);
        editABTextView = (TextView) rl.findViewById(R.id.editABTextView);
        filterButton = (ImageView) rl.findViewById(R.id.filterButton);
        menuButton = (ImageView) rl.findViewById(R.id.menuButton);
        TitleTextView2 = (TextView) rl.findViewById(R.id.centetext);
        //  deleteLay = (RelativeLayout) rl.findViewById(R.id.deleteLay);
        EditLay = (RelativeLayout) rl.findViewById(R.id.EditLay);
        //  menuLay = (RelativeLayout) rl.findViewById(R.id.menuLay);
        filterLay = (RelativeLayout) rl.findViewById(R.id.filterLay);

    }

/*

    *//*Delete Button Visibility *//*
    public void setDeleteCarBtnVisibility(int i) {
        deleteLay.setVisibility(i);

    }

    *//* Set Actionbar Delete Button Click Listener   *//*
    public void setDeleteButtonClickListener(OnClickListener onclicked) {
        deleteLay.setOnClickListener(onclicked);

    }*/


    /*Edit Button Visibility */
    public void setEditCarBtnVisibility(int i) {
        EditLay.setVisibility(i);

    }

    public void setonmenu(OnClickListener onclicked) {
        filterLay.setOnClickListener(onclicked);

    }

    public void ActionBackgroundColor(String color) {
        actionBarRelativeLayout.setBackgroundColor(Color.parseColor(color));
    }

    /* Set Actionbar Edit Button Click Listener   */
    public void setEditButtonClickListener(OnClickListener onclicked) {
        EditLay.setOnClickListener(onclicked);

    }


    /* Set Actionbar Edit Button Text Size   */
    public void setEditText(String text) {
        this.editABTextView.setText("" + text);

    }


    /*Filter Button Visibility*/
    public void setFilterBtnVisibility(int i) {
        filterLay.setVisibility(i);

    }

    /* Set Actionbar Filter Button Click Listener   */
    public void setFilterBtnClickListener(OnClickListener onclicked) {
        filterButton.setOnClickListener(onclicked);

    }


  /*  Set Actionbar
    Title*/

    public void setTitle(String title) {
        this.TitleTextView2.setText("" + title);

    }


  /*  Set Actionbar
    Title Text
    Size*/

    public void setTitleTextSize(Float value) {
        this.TitleTextView2.setTextSize(value);

    }

    public void setTitleColor(int Color) {
        TitleTextView2.setTextColor(Color);
    }


    public void setTitletextVisibility(int i) {
        TitleTextView2.setVisibility(i);

    }


    /* Set Actionbar Menu Button Click Listener   */
    public void setMenuButtonClickListener(OnClickListener onclicked) {

        menuButton.setOnClickListener(onclicked);

    }


    /* Set Actionbar Menu Button Visibility   */
    public void setMenuButtonVisibility(int i) {
        menuButton.setVisibility(i);

    }

    public void setcanceltextVisibility(int i) {
        menucancel.setVisibility(i);

    }

    public void setMenuCancelClickListener(OnClickListener onclicked) {

        menucancel.setOnClickListener(onclicked);

    }


    /* Set Actionbar Menu Button Visibility   */
    public void setMenuButtonDrawable(int i) {
        menuButton.setImageResource(i);

    }


    public void setFilterButtonDrawable(int i) {
        filterButton.setImageResource(i);
    }
}


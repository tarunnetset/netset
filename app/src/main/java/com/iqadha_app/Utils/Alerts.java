package com.iqadha_app.Utils;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iqadha_app.Adapters.ExpandableListAdapter;
import com.iqadha_app.Adapters.HomeGridAdap;
import com.iqadha_app.Adapters.PrayAdapter;
import com.iqadha_app.AsynchClasses.Globals;
import com.iqadha_app.FireBaseModel.FaqModel;
import com.iqadha_app.FireBaseModel.PrayListData;
import com.iqadha_app.Fragments.SettingFragment;
import com.iqadha_app.Fragments.SettingQuadha_fragments;
import com.iqadha_app.HomeActivity;
import com.iqadha_app.R;
import com.iqadha_app.SignIn;
import com.iqadha_app.Splash;
import com.zl.reik.dilatingdotsprogressbar.DilatingDotsProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by netset on 7/11/16.
 */


public class Alerts {
    private static int wi, he;
    public static ProgressDialog mProgressDialog;
    public static Dialog dialogBox;
    public static PopupWindow popUp;
    public static int child_count = 0, count = 0;
    public static List<String> listDataHeader;
    public static HashMap<String, List<String>> listDataChild;

    public static void Alertnet(final Context c, String message, String title) {
        new AlertDialog.Builder(c)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.setClassName("com.android.settings", "com.android.settings.wifi.WifiSettings");
                        c.startActivity(intent);
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }


    public static void AlertInfo(final Context c) {


        final Dialog dialog = new Dialog(c, android.R.style.Theme_Material_Light_Dialog_Alert);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alert_info);


        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        DatabaseReference datebase2 = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference ref = datebase2.child(Globals.INFO).child(Globals.LANG).child("English").child(Globals.PAGE).child(Globals.FAQ).child(Globals.QUESTIONARIES).getRef();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("counttt", dataSnapshot.getChildrenCount() + "");
                child_count = (int) dataSnapshot.getChildrenCount();
                ref.orderByKey().addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        count++;
                        FaqModel faq = dataSnapshot.getValue(FaqModel.class);
                        listDataHeader.add(faq.Question);
                        List<String> answer = new ArrayList<String>();
                        answer.add(faq.Answer);
                        listDataChild.put(listDataHeader.get(count - 1), answer);
                        if (child_count == count) {
                            //stop progress bar here
                            Log.e("Complete===", "" + "**************");
                            //language_LV.setAdapter(new LanguageAdapter(getActivity(),languages,selected_language));

                            ExpandableListView questions = (ExpandableListView) dialog.findViewById(R.id.questions);
                            ExpandableListAdapter listAdapter1 = new ExpandableListAdapter(c, listDataHeader, listDataChild);
                            questions.setAdapter(listAdapter1);

                        }


                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("error===", databaseError.toString());
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        ImageView dismiss = (ImageView) dialog.findViewById(R.id.dismiss);
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void AlertPray(final Context c, ArrayList<String> pray_typ, ArrayList<Integer> pray_image, final ArrayList<String> count, Drawable color, int gravity, final HomeGridAdap adap_home, final String typ) {

        final ArrayList<String> upadte;
        final Dialog dialog = new Dialog(c, android.R.style.Theme_Material_Light_Dialog_Alert);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alert_pray);
        Button save = (Button) dialog.findViewById(R.id.save);
        // View trans=(View)dialog.findViewById(R.id.trans);
        Window window = dialog.getWindow();
        window.setGravity(gravity);
        dialog.getWindow().setGravity(gravity);
        if (gravity == Gravity.TOP) {
            WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
            layoutParams.x = 0; // left margin
            layoutParams.y = 100; // bottom margin
            dialog.getWindow().setAttributes(layoutParams);
        }
        save.setBackground(color);
        ImageView dismiss = (ImageView) dialog.findViewById(R.id.dismiss);
        if ((Splash.currentmin >= Splash.Nightmin) || (Splash.currentmin <= Splash.Daymin)) {
            save.setBackgroundResource(R.drawable.purple_btn);
            save.setPadding(20, 0, 20, 0);
        } else {
            save.setBackgroundResource(R.drawable.yellow_btn);
            save.setPadding(20, 0, 20, 0);
        }
        ListView list_pray = (ListView) dialog.findViewById(R.id.list_pray);

        upadte = new ArrayList<String>();
        upadte.add("0");
        upadte.add("0");
        upadte.add("0");
        upadte.add("0");
        upadte.add("0");
        upadte.add("0");

        list_pray.setAdapter(new PrayAdapter(c, pray_typ, pray_image, upadte));
        CommonMethods.setListViewHight(list_pray);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("countlist", count + "");
                Log.e("upadte===", upadte + "");
                update(Globals.getUid(), count, typ, upadte);
                adap_home.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        dialog.show();
    }


    public static void update(String userid, ArrayList<String> count, String typ, ArrayList<String> update) {
        Map<String, PrayListData> users = new HashMap<String, PrayListData>();
        if (typ.equals("prayers")) {


            int fazr = (Integer.parseInt(count.get(0)) - Integer.parseInt(update.get(0)));
            if (fazr < 0) {
                fazr = 0;
            }
            int zuhr = (Integer.parseInt(count.get(1)) - Integer.parseInt(update.get(1)));
            if (zuhr < 0) {
                zuhr = 0;
            }
            int asr = (Integer.parseInt(count.get(2)) - Integer.parseInt(update.get(2)));
            if (asr < 0) {
                asr = 0;
            }
            int magrib = (Integer.parseInt(count.get(3)) - Integer.parseInt(update.get(3)));
            if (magrib < 0) {
                magrib = 0;
            }
            int isha = (Integer.parseInt(count.get(4)) - Integer.parseInt(update.get(4)));
            if (isha < 0) {
                isha = 0;
            }
            int witr = (Integer.parseInt(count.get(5)) - Integer.parseInt(update.get(5)));
            if (witr < 0) {
                witr = 0;
            }
            users.put("1", new PrayListData(Globals.FAJR, "" + fazr));
            users.put("2", new PrayListData(Globals.ZUHR, "" + zuhr));
            users.put("3", new PrayListData(Globals.ASR, "" + asr));
            users.put("4", new PrayListData(Globals.MAGRIB, "" + magrib));
            users.put("5", new PrayListData(Globals.ISHA, "" + isha));
            users.put("6", new PrayListData(Globals.WITR, "" + witr));
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child(Globals.PRAYERS).child(userid).child(Globals.DATA).setValue(users);
        } else {

            int fast = (Integer.parseInt(count.get(0)) - Integer.parseInt(update.get(0)));
            if (fast < 0) {
                fast = 0;
            }
            users.put("1", new PrayListData("Fasts Qadha", "" + fast));

            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child(Globals.FASTS).child(userid).child(Globals.DATA).setValue(users);
        }
    }


    public static void alertpp(Context c, ArrayList<String> pray_typ, ArrayList<Integer> pray_image, ArrayList<String> count, Drawable color, int gravity, View v) {
        LayoutInflater inflater = LayoutInflater.from(c);
        final View dialog = inflater.inflate(R.layout.alert_pray, null, false);
        final PopupWindow popUp;
        getDeviceMetrics(c);
        if (gravity == Gravity.BOTTOM) {
            popUp = new PopupWindow(dialog, wi, 500, false);
        } else {
            popUp = new PopupWindow(dialog, wi, he, false);
        }
        popUp.setTouchable(true);
        popUp.setFocusable(true);
        popUp.setOutsideTouchable(true);
        popUp.showAsDropDown(v);
        dimBehind(popUp);

        //dialog.setContentView(R.layout.alert_pray);
        LinearLayout layout_linear = (LinearLayout) dialog.findViewById(R.id.layout_linear);
        layout_linear.setGravity(gravity);

        Button save = (Button) dialog.findViewById(R.id.save);
        save.setBackground(color);
        ImageView dismiss = (ImageView) dialog.findViewById(R.id.dismiss);
        if ((Splash.currentmin >= Splash.Nightmin) || (Splash.currentmin <= Splash.Daymin)) {

            save.setBackgroundResource(R.drawable.purple_btn);
            save.setPadding(20, 0, 20, 0);
        } else {

            save.setBackgroundResource(R.drawable.yellow_btn);
            save.setPadding(20, 0, 20, 0);
        }

        ListView list_pray = (ListView) dialog.findViewById(R.id.list_pray);
        list_pray.setAdapter(new PrayAdapter(c, pray_typ, pray_image, count));
        setListViewHeightBasedOnItems(list_pray);
        //  CommonMethods.setListViewHight(list_pray);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUp.dismiss();
            }
        });
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUp.dismiss();
            }
        });
    }

    public static void alertSettings(final Context c, final Boolean check, Boolean gender, Boolean edit, Boolean message, final String title, String hint, final View v, Boolean small_line, final int editpos) {

        LayoutInflater inflater = LayoutInflater.from(c);
        final View dialog = inflater.inflate(R.layout.alert_settings, null, false);
        getDeviceMetrics(c);
        popUp = new PopupWindow(dialog, wi, he - 140, false);
        popUp.setTouchable(true);
        popUp.setFocusable(true);
        popUp.setOutsideTouchable(true);
        popUp.showAsDropDown(v);
        dimBehind(popUp);
        final CheckBox apply_all = (CheckBox) dialog.findViewById(R.id.apply_all);
        TextView Tille_head = (TextView) dialog.findViewById(R.id.Tille_head);
        TextView message_text = (TextView) dialog.findViewById(R.id.message_text);
        final EditText edit_field = (EditText) dialog.findViewById(R.id.edit_field);
        LinearLayout gen_layout = (LinearLayout) dialog.findViewById(R.id.gen_layout);
        LinearLayout edit_ll = (LinearLayout) dialog.findViewById(R.id.edit_ll);
        Button save = (Button) dialog.findViewById(R.id.save);

        final RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.radio_group_gender);
        View v_first = (View) dialog.findViewById(R.id.v_first);
        View v_second = (View) dialog.findViewById(R.id.v_second);

        //check box
        if (!check) {
            apply_all.setVisibility(View.GONE);
        } else {
            apply_all.setVisibility(View.VISIBLE);
        }

        //radio button gender
        if (!gender) {
            gen_layout.setVisibility(View.GONE);
        } else {
            gen_layout.setVisibility(View.VISIBLE);
            if (hint.equals("Male")) {
                ((RadioButton) radioGroup.findViewById(R.id.male_user)).setChecked(true);
            } else {
                ((RadioButton) radioGroup.findViewById(R.id.female_user)).setChecked(true);
            }

        }
        //edittext info
        if (!edit) {
            edit_ll.setVisibility(View.GONE);
        } else {
            edit_field.setText(hint);
            edit_field.setSelection(hint.length());
            edit_ll.setVisibility(View.VISIBLE);
            if (title.contains("Edit") || title.contains("Phone")) {
                edit_field.setInputType(InputType.TYPE_CLASS_NUMBER);
            }

        }
        //message infor
        if (!message) {
            message_text.setVisibility(View.GONE);

        } else {
            save.setText("Sign out");
            message_text.setVisibility(View.VISIBLE);
        }

        //view info
        if (small_line) {
            v_second.setVisibility(View.VISIBLE);
            v_first.setVisibility(View.GONE);
            if (!check) {
                apply_all.setVisibility(View.INVISIBLE);
            }
        } else {
            v_first.setVisibility(View.VISIBLE);
            v_second.setVisibility(View.GONE);
            apply_all.setVisibility(View.GONE);
        }
        final ImageView dismiss = (ImageView) dialog.findViewById(R.id.dismiss);
        Tille_head.setText(title);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (title.equals("Gender")) {
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    RadioButton radioButton = (RadioButton) dialog.findViewById(selectedId);
                    String value = radioButton.getText().toString();
                    updateProfile(Globals.getUid(), title, value);
                    dismiss();
                } else if (title.contains("Edit")) {
                    Log.e("UserTotalDayTill", Globals.getUserTotalDayTillBaligh(c));
                    if (Integer.parseInt(edit_field.getText().toString()) <= Integer.parseInt(Globals.getUserTotalDayTillBaligh(c))) {
                        if (check) {
                            if (apply_all.isChecked()) {
                                updateCount(Globals.getUid(), edit_field.getText().toString().trim(), "All", 0);
                            } else {
                                updateCount(Globals.getUid(), edit_field.getText().toString().trim(), "Single", editpos);
                            }
                        } else {
                            updateSingleCount(Globals.getUid(), editpos, edit_field.getText().toString().trim());
                        }
                        dismiss();
                    } else {
                        Toast.makeText(c, "Wrong prayers detail entered", Toast.LENGTH_LONG).show();
                    }

                } else if (title.equalsIgnoreCase("Sign out")) {
                    SharedPreferences pref = c.getSharedPreferences("com.iqadha", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.clear();
                    editor.commit();
                    c.startActivity(new Intent(c, SignIn.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                    dismiss();
                } else {
                    if (edit_field.getText().toString().length() > 0) {
                        updateProfile(Globals.getUid(), title, edit_field.getText().toString());
                        dismiss();
                    }
                }


                //  Activity activity = (Activity) c;


            }
        });
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUp.dismiss();
            }
        });
        dimBehind(popUp);

    }

    public static void dismiss() {
        android.support.v4.app.Fragment currentFragment = HomeActivity.fragmentManager.findFragmentById(Globals.frag_ID);
        if (currentFragment instanceof MyDialogCloseListener)
            ((MyDialogCloseListener) currentFragment).handleDialogClose(popUp);
        popUp.dismiss();
    }

    public static void updateSingleCount(final String Userid, final int key, final String updatedata) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = mDatabase.child(Globals.FASTS).child(Userid).child(Globals.DATA).child((key + 1) + "");
        ref.child("pray_Count").setValue(updatedata);
        SettingFragment.countprayers.remove(key);
        SettingFragment.countprayers.add(key, updatedata);
        SettingQuadha_fragments.SettingAdap.notifyDataSetChanged();
        SettingQuadha_fragments.ListCountSum();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("datasnapsot", dataSnapshot.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public static void updateCount(String userid, String value, String typ, int key) {
        if (typ.equals("All")) {
            Map<String, PrayListData> users = new HashMap<String, PrayListData>();
            users.put("1", new PrayListData("Fajr", value));
            users.put("2", new PrayListData("Zuhr", value));
            users.put("3", new PrayListData("Asr", value));
            users.put("4", new PrayListData("Maghrib", value));
            users.put("5", new PrayListData("Isha", value));
            users.put("6", new PrayListData("Witr", value));
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child(Globals.PRAYERS).child(userid).child(Globals.DATA).setValue(users);
            repeatvalue(SettingFragment.countprayers.size(), value);
        } else {
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            DatabaseReference ref = mDatabase.child(Globals.PRAYERS).child(userid).child(Globals.DATA).child((key + 1) + "");
            ref.child("pray_Count").setValue(value);
            SettingFragment.countprayers.remove(key);
            SettingFragment.countprayers.add(key, value);
            SettingQuadha_fragments.SettingAdap.notifyDataSetChanged();
            SettingQuadha_fragments.ListCountSum();
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.e("datasnapsot", dataSnapshot.toString());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }

    public static void repeatvalue(int length, String value) {
        for (int i = 0; i < SettingFragment.countprayers.size(); i++) {
            SettingFragment.countprayers.remove(i);
            SettingFragment.countprayers.add(i, value);
        }
        Log.e("new List", SettingFragment.countprayers + "");

        SettingQuadha_fragments.SettingAdap.notifyDataSetChanged();
        SettingQuadha_fragments.ListCountSum();
    }


    public static void updateProfile(String userid, String title, String value) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = mDatabase.child(Globals.USER).child(userid).child("UserProfile");
        if (title.equals("Name")) {
            ref.child("Name").setValue(value);
        } else if (title.equals("Email")) {
            ref.child("Email").setValue(value);
        } else if (title.equals("Phone Number")) {
            ref.child("Phone_number").setValue(value);
        } else if (title.equals("Gender")) {
            ref.child("Gender").setValue(value);
        } else if (title.equals("Language")) {
            ref.child("Language").setValue(value);
        }
    }


    public static DisplayMetrics getDeviceMetrics(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        display.getMetrics(metrics);
        wi = display.getWidth();
        he = display.getHeight();
        return metrics;
    }


    public static void dimBehind(final PopupWindow popupWindow) {
        View container;
        if (popupWindow.getBackground() == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                container = (View) popupWindow.getContentView().getParent();
            } else {
                container = popupWindow.getContentView();
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                container = (View) popupWindow.getContentView().getParent().getParent();
            } else {
                container = (View) popupWindow.getContentView().getParent();
            }
        }
        Context context = popupWindow.getContentView().getContext();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        p.dimAmount = 0.4f;
        wm.updateViewLayout(container, p);
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popupWindow.dismiss();
            }
        });
    }


    public static void showProgressDialog(Context c) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(c);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage("Loading...");
        }
        mProgressDialog.show();
    }


    public static void showCommonDialog(Context mContext, String message) {
        dialogBox = new Dialog(mContext, android.R.style.Theme_Translucent_NoTitleBar);
        dialogBox.setContentView(R.layout.layout_progress_bar);
        dialogBox.setCancelable(false);
        DilatingDotsProgressBar mDilatingDotsProgressBar = (DilatingDotsProgressBar) dialogBox.findViewById(R.id.progress);
        TextView mMessage = (TextView) dialogBox.findViewById(R.id.message);
        mMessage.setText(message);
        mDilatingDotsProgressBar.showNow();
        dialogBox.show();
    }


    public static void cancelDialog() {
        try {
            if (dialogBox != null) {
                if (dialogBox.isShowing()) {
                    dialogBox.cancel();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }

    }


}

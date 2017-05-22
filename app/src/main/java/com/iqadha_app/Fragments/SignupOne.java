package com.iqadha_app.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bruce.pickerview.popwindow.DatePickerPopWin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iqadha_app.AsynchClasses.CommonFunctions;
import com.iqadha_app.AsynchClasses.Globals;
import com.iqadha_app.FireBaseModel.PrayListData;
import com.iqadha_app.FireBaseModel.UserSignup;
import com.iqadha_app.R;
import com.iqadha_app.StepSignup;
import com.iqadha_app.Utils.Alerts;
import com.iqadha_app.Utils.CommonMethods;
import com.iqadha_app.Utils.SnackBar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import static com.iqadha_app.Utils.Alerts.cancelDialog;
import static com.iqadha_app.Utils.Alerts.showCommonDialog;
import static java.util.Calendar.DATE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

/**
 * Created by netset on 8/11/16.
 */

public class SignupOne extends Fragment implements View.OnClickListener {
    View rootView;
    LinearLayout pick_date, pick_baligh;
    Button next;
    DatePickerPopWin pickerPopWin;
    TextView dob_text, baligh_text, balig_txt, txtdob;
    public static FragmentManager fragmentManager;
    String limtday_month;
    int maxyear, minyeardob, minyearbalighdate = 0;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.signup_1, container, false);
        fragmentManager = getFragmentManager();
        pick_date = (LinearLayout) rootView.findViewById(R.id.pick_dob);
        txtdob = (TextView) rootView.findViewById(R.id.txtdob);
        balig_txt = (TextView) rootView.findViewById(R.id.balig_txt);
        Calendar calendar = Calendar.getInstance();
        maxyear = calendar.get(YEAR);
        minyeardob = 1975;
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //   Globals.getttime(getActivity());
        DatabaseReference offsetRef = FirebaseDatabase.getInstance().getReference(".info/serverTimeOffset");
        offsetRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                double offset = snapshot.getValue(Double.class);
                double estimatedServerTimeMs = System.currentTimeMillis() + offset;

                Log.e("timmemee", estimatedServerTimeMs + "");

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis((long) estimatedServerTimeMs);
                System.out.println("Date1:" + calendar.getTime().toString());

                calendar.set(Calendar.HOUR_OF_DAY, 0);  //For 12 AM use 0 and for 12 PM use 12.
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);

                Date date = calendar.getTime();

                System.out.println("Date2:" + date.toString());
                Globals.todaydate = parseTodaysDate(date.toString());
                Log.e("today>>>>>>", Globals.todaydate);


            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Listener was cancelled");
            }
        });

        pick_baligh = (LinearLayout) rootView.findViewById(R.id.pick_baligh);
        baligh_text = (TextView) rootView.findViewById(R.id.baligh_text);
        dob_text = (TextView) rootView.findViewById(R.id.dob_text);
        pick_baligh.setOnClickListener(this);
        StepSignup.action.setTitle("STEP 1");
        next = (Button) rootView.findViewById(R.id.next);
        pick_date.setOnClickListener(this);
        next.setOnClickListener(this);
        select_nothing();
        return rootView;
    }

    public static String parseTodaysDate(String time) {
        String inputPattern = "EEE MMM d HH:mm:ss zzz yyyy";
        String outputPattern = "dd MMM, yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        Date date = null;
        String str = null;
        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);

            Log.i("mini", "Converted Date Today:" + str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }


    void select_nothing() {
        pick_baligh.setBackgroundColor(getResources().getColor(R.color.white));
        balig_txt.setTextColor(getResources().getColor(R.color.green));
        baligh_text.setTextColor(getResources().getColor(R.color.green));
        pick_date.setBackgroundColor(getResources().getColor(R.color.white));
        dob_text.setTextColor(getResources().getColor(R.color.green));
        txtdob.setTextColor(getResources().getColor(R.color.green));
    }


    public String getCurrentTimezoneOffset() {

        TimeZone tz = TimeZone.getDefault();
        Calendar cal = GregorianCalendar.getInstance(tz);
        int offsetInMillis = tz.getOffset(cal.getTimeInMillis());
        String offset = String.format("%02d:%02d", Math.abs(offsetInMillis / 3600000), Math.abs((offsetInMillis / 60000) % 60));
        offset = "GMT" + (offsetInMillis >= 0 ? "+" : "-") + offset;
        return offset;
    }

    void select_dobdate() {

        pick_date.setBackgroundColor(getResources().getColor(R.color.light_green));
        dob_text.setTextColor(getResources().getColor(R.color.white));
        txtdob.setTextColor(getResources().getColor(R.color.white));
        pick_baligh.setBackgroundColor(getResources().getColor(R.color.white));
        baligh_text.setTextColor(getResources().getColor(R.color.green));
        balig_txt.setTextColor(getResources().getColor(R.color.green));

    }

    void select_balighdate() {

        pick_baligh.setBackgroundColor(getResources().getColor(R.color.light_green));
        balig_txt.setTextColor(getResources().getColor(R.color.white));
        baligh_text.setTextColor(getResources().getColor(R.color.white));
        pick_date.setBackgroundColor(getResources().getColor(R.color.white));
        dob_text.setTextColor(getResources().getColor(R.color.green));
        txtdob.setTextColor(getResources().getColor(R.color.green));

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.pick_dob:
                select_dobdate();
                pickerPopWin = new DatePickerPopWin.Builder(getActivity(), new DatePickerPopWin.OnDatePickedListener() {
                    @Override
                    public void onDatePickCompleted(String year, String month, String day, String dateDesc, int m) {
                        Globals.Dob = dateDesc;
                        limtday_month = day + " " + month;
                        // Globals.Dob = getFormatedDate(dateDesc);
                        String date_check = (m + 1) + "/" + day + "/" + year;
                        Log.e("dateDesc is", date_check);
                        if (CommonMethods.isValidDate(date_check)) {
                            Log.e("date existt", "true ");
                            Date d = CommonMethods.ConvertStringtoDate(dateDesc, "d MMM, yyyy");
                            Calendar cal = Calendar.getInstance();
                            cal.set(Integer.parseInt(year), m, Integer.parseInt(day));
                            cal.add(YEAR, 9);
                            minyearbalighdate = Integer.parseInt(year) + 9;
                            dob_text.setText(Globals.Dob);
                            select_nothing();
                        } else {
                            dob_text.setText(getString(R.string.format));
                            select_nothing();
                            final SnackBar snackBarActionClick = new SnackBar();
                            snackBarActionClick.view(pick_baligh)
                                    .text(getString(R.string.select_valid_date), "")
                                    .duration(SnackBar.SnackBarDuration.LONG)
                                    .show();
                        }
                    }
                }, new DatePickerPopWin.OnInfo() {
                    @Override
                    public void Infoclick() {
                        Alerts.AlertInfo(getActivity());
                    }
                }).textConfirm("Done") //text of confirm button
                        .textCancel("CANCEL") //text of cancel button
                        .btnTextSize(16) // button text size
                        .viewTextSize(25) // pick view text size
                        .texttitle("Date of Birth")
                        .colorCancel(Color.parseColor("#999999")) //color of cancel button
                        .colorConfirm(Color.parseColor("#009900"))//color of confirm button
                        .minYear(minyeardob) //min year in loop
                        .maxYear(maxyear) // max year in loop
                        .dateChose("") // date chose when init popwindow
                        .showDayMonthYear(true)
                        .build();
                pickerPopWin.showPopWin(getActivity());
                break;
            case R.id.pick_baligh:
                if (minyearbalighdate != 0) {
                    select_balighdate();
                    pickerPopWin = new DatePickerPopWin.Builder(getActivity(), new DatePickerPopWin.OnDatePickedListener() {
                        @Override
                        public void onDatePickCompleted(String year, String month, String day, String dateDesc, int m) {

                            String date_check = (m + 1) + "/" + day + "/" + year;
                            Log.e("dateDesc is", date_check);
                            if (CommonMethods.isValidDate(date_check)) {
                                Globals.Balig_date = dateDesc;
                                int year_is=getDiffYears(Globals.Dob, Globals.Balig_date, "d MMM, yyyy");
                                if (year_is>=9) {
                                    Log.e("year_gap======", year_is + "");
                                    baligh_text.setText(dateDesc);
                                    select_nothing();
                                    next.setVisibility(View.VISIBLE);
                                }else {
                                    baligh_text.setText(getString(R.string.format));
                                    select_nothing();
                                    final SnackBar snackBarActionClick = new SnackBar();
                                    snackBarActionClick.view(pick_baligh)
                                            .text(getString(R.string.not_baligh), "")
                                            .duration(SnackBar.SnackBarDuration.LONG)
                                            .show();
                                }
                            } else {
                                baligh_text.setText(getString(R.string.format));
                                select_nothing();
                                final SnackBar snackBarActionClick = new SnackBar();
                                snackBarActionClick.view(pick_baligh)
                                        .text(getString(R.string.select_valid_date), "")
                                        .duration(SnackBar.SnackBarDuration.LONG)
                                        .show();
                            }
                        }
                    }, new DatePickerPopWin.OnInfo() {
                        @Override
                        public void Infoclick() {
                            Alerts.AlertInfo(getActivity());
                        }
                    }).textConfirm("Done") //text of confirm button
                            .textCancel("CANCEL") //text of cancel button
                            .btnTextSize(16) // button text size
                            .viewTextSize(25) // pick view text size
                            .texttitle("Baligh Date (Puberty)")
                            .colorCancel(Color.parseColor("#999999")) //color of cancel button
                            .colorConfirm(Color.parseColor("#009900"))//color of confirm button
                            .minYear(minyearbalighdate) //min year in loop
                            .maxYear(maxyear) // max year in loop
                            .dateChose(limtday_month) // date chose when init popwindow
                            .showDayMonthYear(true)
                            .build();
                    pickerPopWin.showPopWin(getActivity());
                } else {
                    final SnackBar snackBarActionClick = new SnackBar();
                    snackBarActionClick.view(pick_baligh)
                            .text("Select Date of Birth First", "")
                            .duration(SnackBar.SnackBarDuration.LONG)
                            .show();
                }
                break;
            case R.id.next:
                Log.e("date.toString()", Globals.Balig_date + "    to day>>>>   " + Globals.todaydate + "");
                Globals.day = Float.valueOf(Daybetween(Globals.Balig_date, Globals.todaydate, "d MMM, yyyy"));
                Log.e("difference of Day is", Globals.day + "");
                signUp(Globals.email,Globals.pass);
                break;
        }
    }





    private void onAuthSuccess(FirebaseUser user) {
        UserSignup useris = new UserSignup(Globals.getUid(), Globals.email, Globals.gender, Globals.Dob, Globals.Balig_date, "", "","English");
        if( CommonFunctions.getConnectivityStatus(getActivity())) {
            addpray(user.getUid());
            addqadha(user.getUid());
            writeNewUser(Globals.getUid(), useris);

            Fragment fragment2 = new SignupTwo();
            Globals.frag_ID = R.id.container;
            CommonMethods.SwitchFragements(fragmentManager, fragment2);


        }else {
            Alerts.Alertnet(getActivity(), "Please Connect to Internet", "No Internet Access");
        }



    }

    private void writeNewUser(final String userid, final UserSignup usersignup) {
        mDatabase.child(Globals.USER).child(userid).child("Total_Qadha").setValue((int) +Globals.day + "");
        mDatabase.child(Globals.USER).child(userid).child("UserProfile").setValue(usersignup);
    }


    private void addpray(String userid) {
        Map<String, PrayListData> users = new HashMap<String, PrayListData>();
        users.put("1", new PrayListData(Globals.FAJR, Globals.day+""));
        users.put("2", new PrayListData(Globals.ZUHR, Globals.day+""));
        users.put("3", new PrayListData(Globals.ASR, Globals.day+""));
        users.put("4", new PrayListData(Globals.MAGRIB, Globals.day+""));
        users.put("5", new PrayListData(Globals.ISHA, Globals.day+""));
        users.put("6", new PrayListData(Globals.WITR, Globals.day+""));
        mDatabase.child(Globals.PRAYERS).child(userid).child(Globals.DATA).setValue(users);
    }


    private void addqadha(String userid) {
        Map<String, PrayListData> users = new HashMap<String, PrayListData>();
        users.put("1", new PrayListData(Globals.FASTS_QADHA, "0"));
        mDatabase.child(Globals.FASTS).child(userid).child(Globals.DATA).setValue(users);
    }

    private void signUp(String email, String password) {
        showCommonDialog(getActivity(), "");

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        cancelDialog();
                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());
                        } else {
                            task.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(), e.getMessage() + " " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }

                });
    }






    public long Daybetween(String date1, String date2, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.ENGLISH);
        Date Date1 = null, Date2 = null;
        try {
            Date1 = sdf.parse(date1);
            Date2 = sdf.parse(date2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (Date2.getTime() - Date1.getTime()) / (24 * 60 * 60 * 1000);
    }

    public static int getDiffYears(String date1, String date2,String pattern) {

        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.ENGLISH);
        Date first = null, last = null;
        try {
            first = sdf.parse(date1);
            last = sdf.parse(date2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Calendar a = getCalendar(first);
        Calendar b = getCalendar(last);
        int diff = b.get(YEAR) - a.get(YEAR);
        if (a.get(MONTH) > b.get(MONTH) ||
                (a.get(MONTH) == b.get(MONTH) && a.get(DATE) > b.get(DATE))) {
            diff--;
        }
        return diff;
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }

}


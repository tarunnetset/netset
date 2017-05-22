package com.iqadha_app.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.iqadha_app.AsynchClasses.CommonFunctions;
import com.iqadha_app.AsynchClasses.Globals;
import com.iqadha_app.R;
import com.iqadha_app.StepSignup;
import com.iqadha_app.Utils.Alerts;
import com.iqadha_app.Utils.CommonMethods;
import com.natasa.progressviews.CircleProgressBar;
import com.natasa.progressviews.utils.OnProgressViewListener;
import com.skyfishjy.library.RippleBackground;

import java.util.Timer;
import java.util.TimerTask;

import static com.iqadha_app.AsynchClasses.Globals.getUid;
import static com.iqadha_app.Fragments.SignupOne.fragmentManager;

/**
 * Created by netset on 8/11/16.
 */
public class SignupTwo extends Fragment {
    View rootView;
    private CircleProgressBar circleProgressBar;
    RippleBackground ll2;
    RelativeLayout ll;
    TextView txt;
    private DatabaseReference datebase2;
    DatabaseReference ref,reffast;
    Animation anim_zoom;
    TextView count_quadha;
    float add;
    int count;
    Timer T;
    Boolean update = false;
    Boolean runin = false;
    ValueEventListener listener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.signup_2, container, false);
        StepSignup.action.setVisibility(View.GONE);
        circleProgressBar = (CircleProgressBar) rootView.findViewById(R.id.circle_progress);
        txt = (TextView) rootView.findViewById(R.id.txt);
        ll = (RelativeLayout) rootView.findViewById(R.id.ll);
        ll2 = (RippleBackground) rootView.findViewById(R.id.ll2);
        count_quadha = (TextView) rootView.findViewById(R.id.count_quadha);
        ll.setVisibility(View.VISIBLE);
        anim_zoom = AnimationUtils.loadAnimation(getActivity(), R.anim.scale);
        anim_zoom.reset();
        add = (Globals.day / 100);

        String myUserId = getUid();

        if( CommonFunctions.getConnectivityStatus(getActivity())) {
            updateFasts(myUserId);
            updateAllPrayers(myUserId);
        }else {
            Alerts.Alertnet(getActivity(), "Please Connect to Internet", "No Internet Access");
        }



        if (runin) {
            cancetT();
        }
        T = new Timer();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                YoYo.with(Techniques.BounceIn).duration(1000).playOn(ll);
                //  setTimer();
                NewstartTimer(circleProgressBar);
            }
        }, 200);

        CircularCountPrayers();
        return rootView;
    }

    void cancetT() {
        if (T != null)
            T.cancel();
        runin = false;

    }

    void CircularCountPrayers(){
        circleProgressBar.setRoundEdgeProgress(true);
        circleProgressBar.setTextSize(55);
        circleProgressBar.setStartPositionInDegrees(-90);
        circleProgressBar.setBackgroundColor(Color.TRANSPARENT);
        circleProgressBar.setProgressColor(getResources().getColor(R.color.light_green));
        circleProgressBar.setTextColor(getResources().getColor(R.color.green));
        //you can set listener for progress in every ProgressView
        circleProgressBar.setOnProgressViewListener(new OnProgressViewListener() {
            @Override
            public void onFinish() {
                //do something on progress finish
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        YoYo.with(Techniques.BounceIn).duration(1000).playOn(count_quadha);
                        ll2.startRippleAnimation();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ll2.stopRippleAnimation();
                                count_quadha.setText((int) Globals.day + "");
                                ref.removeEventListener(listener);
                                Fragment fragment3 = new SignupThree();
                                Globals.frag_ID=R.id.container;
                                CommonMethods.SwitchFragements(fragmentManager,fragment3);


                            }
                        }, 800);


                    }
                }, 1000);


            }

            @Override
            public void onProgressUpdate(float progress) {
                count_quadha.setText((int) Math.round(progress) + "");
                txt.setVisibility(View.VISIBLE);


            }
        });
    }



    void updateFasts(String Userid) {
        datebase2 = FirebaseDatabase.getInstance().getReference();
        Query myTopPostsQuery = datebase2.child(Globals.FASTS).child(Userid).child(Globals.DATA);
        reffast = myTopPostsQuery.getRef();
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.e("Count on data change", "" + snapshot.getChildrenCount());
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    DatabaseReference prayatpos = reffast.child(postSnapshot.getKey());
                    DatabaseReference updatePraycount = prayatpos.child("pray_Count");

                    updatePraycount.setValue("0");

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("The read failed: ", databaseError.getMessage());
            }
        };
        reffast.addValueEventListener(listener);
    }



    void updateAllPrayers(String Userid) {
        datebase2 = FirebaseDatabase.getInstance().getReference();
        Query myTopPostsQuery = datebase2.child(Globals.PRAYERS).child(Userid).child(Globals.DATA);
        ref = myTopPostsQuery.getRef();
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.d("Count on data change", "" + snapshot.getChildrenCount());
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    DatabaseReference prayatpos = ref.child(postSnapshot.getKey());
                    DatabaseReference updatePraycount = prayatpos.child("pray_Count");
                    int day = (int) Globals.day;
                    updatePraycount.setValue(String.valueOf(day));

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("The read failed: ", databaseError.getMessage());
            }
        };
        ref.addValueEventListener(listener);
    }


    void NewstartTimer(final CircleProgressBar circleProgressBar) {
        T.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (count <= 100) {
                    runin = true;
                    Log.d("count", count + "");
                    //  final float finalProgress = count *add;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Log.e("inside timer", "Timerr");
                            circleProgressBar.setProgress(count);
                            float finalProgress = count * add;
                            count_quadha.setText((int) finalProgress + "");
                            count++;
                        }
                    });

                } else {
                    Log.d("elsee count", count + "");
                    cancetT();
                }
            }
        }, 50, 50);

    }


}

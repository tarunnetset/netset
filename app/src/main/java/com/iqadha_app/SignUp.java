package com.iqadha_app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.iqadha_app.AsynchClasses.Globals;
import com.iqadha_app.Utils.Alerts;
import com.iqadha_app.Utils.CommonMethods;
import com.iqadha_app.Utils.SnackBar;

/**
 * Created by netset on 8/11/16.
 */
public class SignUp extends Activity implements View.OnClickListener {
    Button signup_btn;
    TextView signin_txt, male_btn, female_btn;
    EditText pass_sign_up, email_signup;
    LinearLayout out_side;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    // SegmentControl mSegmentHorzontal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initialize();
        nothing();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signup_btn:
                Globals.email = email_signup.getText().toString().trim();
                Globals.pass = pass_sign_up.getText().toString().trim();
                // startActivity(new Intent(getApplicationContext(), StepSignup.class));
                if (!CommonMethods.isValidEmail(Globals.email)) {
                    //  email_signup.setError("Please fill valid email address");
                    email_signup.setBackgroundResource(R.drawable.red_edtbtn);
                } else if (Globals.email.length() == 0) {
                    //  email_signup.setError("Email address is required");
                    email_signup.setBackgroundResource(R.drawable.red_edtbtn);
                } else if (Globals.pass.length() == 0) {
                    //pass_sign_up.setError("Password is required");
                    pass_sign_up.setBackgroundResource(R.drawable.red_edtbtn);
                } else if (Globals.gender.equals("NO")) {
                    Toast.makeText(getApplicationContext(), "Select Gender", Toast.LENGTH_LONG).show();
                } else {
                    Globals.FirebaseUserid = pref.getString("FirebaseuserId", "");
                    if (Globals.FirebaseUserid.length() > 0) {
                        startActivity(new Intent(getApplicationContext(), StepSignup.class));
                    } else {
                        signUp(Globals.email);
                    }
                     /* signUp(Globals.email, Globals.pass);
                      startActivity(new Intent(getApplicationContext(), StepSignup.class));*/
                }

                break;
            case R.id.signin_txt:
                startActivity(new Intent(getApplicationContext(), SignIn.class));
                break;
            case R.id.male_btn:
                try {
                    Alerts.hideKeyboard(SignUp.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                male_select();
                //  female_btn.setTextAppearance(getApplicationContext(), R.style.button_unselect);
                break;
            case R.id.female_btn:
                try {
                    Alerts.hideKeyboard(SignUp.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                female_select();
                break;

        }

    }


    void initialize() {
        pref = getSharedPreferences("com.iqadha", MODE_PRIVATE);
        editor = pref.edit();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        out_side = (LinearLayout) findViewById(R.id.out_side);
        signup_btn = (Button) findViewById(R.id.signup_btn);
        signin_txt = (TextView) findViewById(R.id.signin_txt);
        email_signup = (EditText) findViewById(R.id.email_signup);
        signup_btn.setOnClickListener(this);
        signin_txt.setOnClickListener(this);
        male_btn = (TextView) findViewById(R.id.male_btn);
        pass_sign_up = (EditText) findViewById(R.id.pass_sign_up);
        pass_sign_up.setTransformationMethod(PasswordTransformationMethod.getInstance());
        male_btn.setOnClickListener(this);
        female_btn = (TextView) findViewById(R.id.female_btn);
        female_btn.setOnClickListener(this);
        pass_sign_up.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                //change bg afterTextChanged
                if (s.length() > 0) {
                    pass_sign_up.setBackgroundResource(R.drawable.green_box);
                } else {
                    pass_sign_up.setBackgroundResource(R.drawable.grey_box);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //change bg beforeTextChanged
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //change bg whenTextChanged
            }
        });
        email_signup.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                //change bg afterTextChanged
                if (s.length() > 0) {
                    email_signup.setBackgroundResource(R.drawable.green_box);
                } else {
                    email_signup.setBackgroundResource(R.drawable.grey_box);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //change bg beforeTextChanged
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //change bg whenTextChanged
            }
        });

    }


    private void signUp(String email) {

        mAuth.fetchProvidersForEmail(email).addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                if (task.isSuccessful()) {
                    //onAuthSuccess(task.getResult().getUser());
                    //Log.e("Task","result task"+task.getResult().getProviders().size());
                    if (task.getResult().getProviders().size()==1)
                    {
                        final SnackBar snackBarActionClick = new SnackBar();
                        snackBarActionClick.view(email_signup)
                                .text(getString(R.string.already_registered), "")
                                .duration(SnackBar.SnackBarDuration.LONG)
                                .show();
                    }else {
                        startActivity(new Intent(getApplicationContext(), StepSignup.class));
                        finish();
                    }

                } else {
                    task.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage() + " " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

    }




    void nothing() {
        male_btn.setBackgroundColor(getResources().getColor(R.color.white));
        female_btn.setBackgroundColor(getResources().getColor(R.color.white));
        female_btn.setTextColor(getResources().getColor(R.color.grey));
        male_btn.setTextColor(getResources().getColor(R.color.grey));
        email_signup.setBackgroundResource(R.drawable.edit_back);
        pass_sign_up.setBackgroundResource(R.drawable.edit_back);
        out_side.setBackgroundResource(R.drawable.edit_back);
        Globals.gender = "NO";
    }


    void male_select() {
        //male_btn.setTextAppearance(getApplicationContext(), R.style.button);
        male_btn.setTextColor(getResources().getColor(R.color.white));
        male_btn.setBackgroundColor(getResources().getColor(R.color.green));
        female_btn.setBackgroundColor(getResources().getColor(R.color.white));
        female_btn.setTextColor(getResources().getColor(R.color.black));
        out_side.setBackground(getResources().getDrawable(R.mipmap.green_rect_box));
        Globals.gender = "Male";

    }

    void female_select() {
        male_btn.setBackgroundColor(getResources().getColor(R.color.white));
        male_btn.setTextColor(getResources().getColor(R.color.black));
        female_btn.setBackgroundColor(getResources().getColor(R.color.green));
        female_btn.setTextColor(getResources().getColor(R.color.white));
        out_side.setBackground(getResources().getDrawable(R.mipmap.green_rect_box));
        Globals.gender = "Female";

    }
}

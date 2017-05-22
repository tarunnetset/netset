package com.iqadha_app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.iqadha_app.Utils.CommonMethods;

import static com.iqadha_app.Utils.Alerts.cancelDialog;
import static com.iqadha_app.Utils.Alerts.showCommonDialog;

/**
 * Created by netset on 7/11/16.
 */

public class SignIn extends Activity implements View.OnClickListener {
    Button signin_btn;
    TextView signup_text;
    EditText pass, email;

    //initialize fire base
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    //prefrencess
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sigin);
        Initialise();


    }

    void Initialise() {

        pref = getSharedPreferences("com.iqadha", MODE_PRIVATE);
        editor = pref.edit();

        //oncreate initialisation
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();


        signin_btn = (Button) findViewById(R.id.signin_btn);
        signup_text = (TextView) findViewById(R.id.signup_txt);
        signin_btn.setOnClickListener(this);
        signup_text.setOnClickListener(this);
        pass = (EditText) findViewById(R.id.pass_signin);
        email = (EditText) findViewById(R.id.email);
        email.setBackgroundResource(R.drawable.edit_back);
        pass.setBackgroundResource(R.drawable.edit_back);
        pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                //change bg afterTextChanged
                if (s.length() > 0) {
                    pass.setBackgroundResource(R.drawable.green_box);
                } else {
                    pass.setBackgroundResource(R.drawable.grey_box);
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
        email.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                //change bg afterTextChanged
                if (s.length() > 0) {
                    email.setBackgroundResource(R.drawable.green_box);
                } else {
                    email.setBackgroundResource(R.drawable.grey_box);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signup_txt:

                startActivity(new Intent(getApplicationContext(), SignUp.class));
                break;
            case R.id.signin_btn:

                String emailis = email.getText().toString().trim();
                String passis = pass.getText().toString().trim();
                if (emailis.length() == 0) {
                    email.setBackgroundResource(R.drawable.red_edtbtn);
                } else if (!CommonMethods.isValidEmail(emailis)) {
                    email.setBackgroundResource(R.drawable.red_edtbtn);
                } else if (passis.length() == 0) {
                    pass.setBackgroundResource(R.drawable.red_edtbtn);
                } else {

                    SiginMethod(emailis, passis);

                }
                break;
        }
    }


    //Fire base Signin method
    void SiginMethod(String email, String password) {
        showCommonDialog(SignIn.this, "");
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.e("Signin ", "signIn:onComplete:" + task.isSuccessful());
                        cancelDialog();
                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());

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
// firebase success sign in

    private void onAuthSuccess(FirebaseUser user) {

        Log.e("LOgin  outhhh", "innnnn");
        editor.putString("FirebaseuserId", user.getUid()).commit();
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        finish();
    }


}

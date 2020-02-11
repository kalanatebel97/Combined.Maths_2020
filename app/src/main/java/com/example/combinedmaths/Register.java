package com.example.combinedmaths;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Register extends AppCompatActivity {
    EditText mFullName, mEmail, mPassword, mConfirmPassword, mPhone;
    Button mRegisterButton;
    TextView mLoginButton;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName = findViewById(R.id.fullName);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.pwd);
        mConfirmPassword = findViewById(R.id.confirmPwd);
        mPhone = findViewById(R.id.phoneNumber);
        mRegisterButton = findViewById(R.id.registerButton);
        mLoginButton = findViewById(R.id.createText);

        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);


        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();

        }

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String confirmPwd = mConfirmPassword.getText().toString().trim();
                String phoneNum = mPhone.getText().toString().trim();
                String fullName = mFullName.getText().toString().trim();

                if (TextUtils.isEmpty(fullName)) {
                    mFullName.setError("Full name is Required!");
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is Required!");
                    return;
                }
                if (TextUtils.isEmpty(phoneNum)) {
                    mPhone.setError("Phone Number is Required!");
                    return;
                }
                if (phoneNum.length() > 10 || phoneNum.length() < 10) {
                    mPhone.setError("Phone number must be = 10 numbers!");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is Required!");
                    return;
                }
                if (password.length() < 6) {
                    mPassword.setError("Password must be >= 6 Characters!");
                    return;
                }
                if (TextUtils.isEmpty(confirmPwd)) {
                    mConfirmPassword.setError("Confirm Password is Required!");
                    return;
                }
                if (!password.equals(confirmPwd)) {
                    mConfirmPassword.setError("Password Not matching!!");
                    return;
                }



                progressBar.setVisibility(View.VISIBLE);

                //Register the user in firebase

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(Register.this, "User created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));


                        } else {
                            Toast.makeText(Register.this, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));

            }
        });

    }
}

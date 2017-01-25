package com.doufa.sekritoo.auth;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.doufa.sekritoo.BaseActivity;
import com.doufa.sekritoo.MainActivity;
import com.doufa.sekritoo.R;
import com.doufa.sekritoo.posts.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "SignInActivity";

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private EditText mEmailField;
    private EditText mPasswordField;
    private EditText mResetEmailField;
    private EditText mSexeField;

    private Button mSignInButton;
    private Button mSignUpButton;
    private Button mForgotPasswordButton;
    private Button mSendResetEmailButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        // Views
        mEmailField = (EditText) findViewById(R.id.field_email);
        mPasswordField = (EditText) findViewById(R.id.field_password);
        mResetEmailField = (EditText) findViewById(R.id.field_resetEmail);
        mSignInButton = (Button) findViewById(R.id.button_sign_in);
        mSignUpButton = (Button) findViewById(R.id.button_sign_up);
        mForgotPasswordButton =(Button) findViewById(R.id.button_forgot_password);
        mSendResetEmailButton =(Button) findViewById(R.id.button_sendResetMail);

        // Click listeners
        mSignInButton.setOnClickListener(this);
        mSignUpButton.setOnClickListener(this);
        mForgotPasswordButton.setOnClickListener(this);
        mSendResetEmailButton.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();

        // Check auth on Activity start
        if (mAuth.getCurrentUser() != null) {
            onSignInSuccess(mAuth.getCurrentUser());
        }
    }

    private void signIn() {
        Log.d(TAG, "signIn");
        if (!validateForm()) {
            return;
        }

        showProgressDialog();
        final String email = mEmailField.getText().toString();
        final String password = mPasswordField.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signIn:onComplete:" + task.isSuccessful());
                        hideProgressDialog();

                        if (task.isSuccessful()) {
                            onSignInSuccess(task.getResult().getUser());
                        } else {

                            Toast.makeText(SignInActivity.this, task.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    private void signUp() {
        Log.d(TAG, "signUp");
        if (!validateForm()) {
            return;
        }
        showProgressDialog();
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();
        String sexe = mSexeField.getText().toString();
        /****/

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUser:onComplete:" + task.isSuccessful());
                        hideProgressDialog();
                        if (task.isSuccessful()) {
                            onSignUpSuccess(task.getResult().getUser());
                        } else {
                            Toast.makeText(SignInActivity.this, task.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void resetPassword(){
        if (TextUtils.isEmpty(mResetEmailField.getText().toString())) {
            mResetEmailField.setError("Required");
        } else {

            showProgressDialog();
            String email = mResetEmailField.getText().toString();

            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Log.d(TAG, "ResetPassword:onComplete:" + task.isSuccessful());
                    hideProgressDialog();

                    if (task.isSuccessful()) {
                        Toast.makeText(SignInActivity.this,"Check your mail !",
                                Toast.LENGTH_LONG).show();
                        mSendResetEmailButton.setVisibility(View.GONE);
                        mResetEmailField.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(SignInActivity.this, task.getException().getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void onSignInSuccess(FirebaseUser user) {

        // Update last visit date
        mDatabase.child("users").child(user.getUid())
                .child("lastVisit").setValue(System.currentTimeMillis());

        // Go to PostsHomeActivity
        startActivity(new Intent(SignInActivity.this, MainActivity.class));
        finish();
    }

    private void onSignUpSuccess(FirebaseUser user) {
        String username = usernameFromEmail(user.getEmail());
        // Write new user
        writeNewUser(user.getUid(), username, user.getEmail(),mSexeField.getText().toString());
        // Go to PostsHomeActivity
        startActivity(new Intent(SignInActivity.this, MainActivity.class));
        finish();
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(mEmailField.getText().toString())) {
            mEmailField.setError("Required");
            result = false;
        } else {
            mEmailField.setError(null);
        }

        if (TextUtils.isEmpty(mPasswordField.getText().toString())) {
            mPasswordField.setError("Required");
            result = false;
        } else {
            mPasswordField.setError(null);
        }

        return result;
    }

    // [START basic_write]
    private void writeNewUser(String userId, String name, String email, String sexe) {
        User user = new User(name, email,sexe);
        user.lastVisit= System.currentTimeMillis();

        mDatabase.child("users").child(userId).setValue(user);
    }
    // [END basic_write]

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.button_sign_in) {
            signIn();
        } else if (i == R.id.button_sign_up) {
            signUp();
        }
        else if (i==R.id.button_forgot_password){
            mSendResetEmailButton.setVisibility(View.VISIBLE);
            mResetEmailField.setVisibility(View.VISIBLE);

        } else if(i== R.id.button_sendResetMail){
            resetPassword();
        }
    }
}

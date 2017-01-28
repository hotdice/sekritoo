package com.doufa.sekritou;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.doufa.sekritou.auth.SignInActivity;
import com.doufa.sekritou.posts.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;

public class UserProfileActivity extends Fragment {

    private static final String TAG = "UserProfileActivity";
    private FirebaseUser mUser;

    private Button mEditProfileButton;
    private Button mCancelEditionButton;
    private TextView mUsernameTextView;
    private TextView mUsermailTextView;
    private TextView mUserCountryTextView;
    private TextView mUserSignUpDateTextView;
    private TextView mUserLastVisitTextView;

    private EditText mNewPasswordEditext;
    private Button mUpdatePassword;
    private Button mConfirmUpdatePassword;
    private Button mCancelUpdatePassword;
    private Button mDeleteMyAccount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.activity_user_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Account");
        mUser = FirebaseAuth.getInstance().getCurrentUser();

        mUsernameTextView = (TextView) view.findViewById(R.id.userNameTitle);
        mUsermailTextView = (TextView) view.findViewById(R.id.userEmailTitle);
        mUserSignUpDateTextView = (TextView) view.findViewById(R.id.userSignUpDate);
        mUserLastVisitTextView = (TextView) view.findViewById(R.id.userLastVisitDate);
        mDeleteMyAccount = (Button) view.findViewById(R.id.button_deleteMyAccount);
        refreshProfileInformation();

        mDeleteMyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Delete sekritou account")
                        .setMessage("Do you really want to delete your account?")
                        .setIcon(android.R.drawable.ic_delete)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                mUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(getContext(), "Account successfully deleted!", Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(getContext(),SignInActivity.class));
                                            getActivity().finish();
                                        }
                                        else {
                                            Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });

                            }})
                        .setNegativeButton("No", null).show();
            }
        });
        // init update password UI
//        mNewPasswordEditext = (EditText) view.findViewById(R.id.field_newPassword);
//        mUpdatePassword = (Button)  view.findViewById(R.id.button_updatePassword);
//        mCancelUpdatePassword =(Button) view.findViewById(R.id.button_cancelUpdatePassword);
//        mConfirmUpdatePassword = (Button) view.findViewById(R.id.button_confirmUpdatePassword);

//        mUpdatePassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mNewPasswordEditext.setVisibility(View.VISIBLE);
//                mConfirmUpdatePassword.setVisibility(View.VISIBLE);
//                mCancelUpdatePassword.setVisibility(View.VISIBLE);
//            }
//        });
//        mCancelUpdatePassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mNewPasswordEditext.setVisibility(View.GONE);
//                mConfirmUpdatePassword.setVisibility(View.GONE);
//                mCancelUpdatePassword.setVisibility(View.GONE);
//            }
//        });
//        mConfirmUpdatePassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (TextUtils.isEmpty(mNewPasswordEditext.getText().toString())) {
//                    mNewPasswordEditext.setError("Required");
//                } else {
//                    mUser.updatePassword(mNewPasswordEditext.getText().toString())
//                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            Log.d(TAG, "UpdatePassword:onComplete:" + task.isSuccessful());
//
//                            if(task.isSuccessful()) {
//                                mNewPasswordEditext.setVisibility(View.GONE);
//                                mConfirmUpdatePassword.setVisibility(View.GONE);
//                                mCancelUpdatePassword.setVisibility(View.GONE);
//                                Toast.makeText(getContext(), "Password successfully updated!",Toast.LENGTH_LONG).show();
//                            }
//                            else {
//                                Toast.makeText(getContext(), task.getException().getMessage(),Toast.LENGTH_LONG).show();
//                            }
//                        }
//                    });
//                }
//            }
//        });
    }

    @Override
    public void onStart() {
        super.onStart();
        refreshProfileInformation();
    }

    public void refreshProfileInformation() {

       if(isAdded()){
        final String uid = mUser.getUid();
        FirebaseDatabase.getInstance().getReference().child("users").child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user information
                        User user = dataSnapshot.getValue(User.class);
                        mUsermailTextView.setText(String.format(getString(R.string.useremail), user.email));
                        mUsernameTextView.setText(String.format(getString(R.string.username), user.username));
                        mUserSignUpDateTextView.setText(String.format(getString(R.string.userSignUpDate), DateFormat.getDateInstance(DateFormat.MEDIUM).format(user.createdAt)));
                        mUserLastVisitTextView.setText(String.format(getString(R.string.userLastVisitDate),
                                DateFormat.getDateTimeInstance().format(user.lastVisit)));
                    }
                });
           }

    }
}

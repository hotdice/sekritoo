package com.doufa.sekritoo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.doufa.sekritoo.posts.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;

public class UserProfileActivity extends Fragment {

    private FirebaseUser mUser;
    private Button mEditProfileButton;
    private Button mCancelEditionButton;
    private TextView mUsernameTextView;
    private TextView mUsermailTextView;
    private TextView mUserCountryTextView;
    private TextView mUserSignUpDateTextView;
    private TextView mUserLastVisitTextView;
    private TextView mUserSexeTextView;

    //    private EditText mUpdateCountryEditext;
    private EditText mUpdateSexeEditext;

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
        getActivity().setTitle("Profile");


      //  mEditProfileButton = (Button) view.findViewById(R.id.editButton);
       // mCancelEditionButton = (Button) view.findViewById(R.id.cancelEditionButton);
        mUsernameTextView = (TextView) view.findViewById(R.id.userNameTitle);
        mUsermailTextView = (TextView) view.findViewById(R.id.userEmailTitle);
        mUserSignUpDateTextView = (TextView) view.findViewById(R.id.userSignUpDate);
        mUserLastVisitTextView = (TextView) view.findViewById(R.id.userLastVisitDate);

        // mUserCountryTextView = (TextView) view.findViewById(R.id.userCountryTitle);
       // mUserSexeTextView = (TextView) view.findViewById(R.id.userSexe);
        //mUpdateSexeEditext = (EditText) view.findViewById(R.id.updateSexeEdittext);
        refreshProfileInformation();
    }
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_user_profile);
//
//        mEditProfileButton = (Button) findViewById(R.id.editButton);
//        mCancelEditionButton = (Button) findViewById(R.id.cancelEditionButton);
//        mUsernameTextView = (TextView) findViewById(R.id.userNameTitle);
//        mUsermailTextView = (TextView) findViewById(R.id.userEmailTitle);
//        mUserSignUpDateTextView = (TextView) findViewById(R.id.userSignUpDate);
//        mUserCountryTextView = (TextView) findViewById(R.id.userCountryTitle);
//        mUserSexeTextView = (TextView) findViewById(R.id.userSexe);
//        mUpdateSexeEditext = (EditText) findViewById(R.id.updateSexeEdittext);
//    }

    @Override
    public void onStart() {
        super.onStart();
        refreshProfileInformation();
    }

    public void refreshProfileInformation() {

//        FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
//        mUsernameTextView.setText(String.format(getString(R.string.username), user.getDisplayName().));
//        mUsermailTextView.setText(String.format(getString(R.string.useremail), user.getEmail()));
        // mUserSignUpDateTextView.setText(String.format(getString(R.string.userSignUpDate),user.signed_up_at);
        //        ImageView userPicture = (ImageView) findViewById(R.id.userPicture);
//        Picasso.with(getApplicationContext()).load(mUser.getPhotoUrl().into(userPicture);
        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().child("users").child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
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

package com.doufa.sekritou;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doufa.sekritou.posts.NewPostActivity;
import com.doufa.sekritou.posts.PostsHomeActivity;


public class HomeActivity extends Fragment {

    private FloatingActionButton mTellSecret;
    private FloatingActionButton mSeeSecret;


    public HomeActivity() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        getActivity().setTitle("Home");

        //Find the +1 button
        mSeeSecret = (FloatingActionButton) view.findViewById(R.id.floatingActionButton_seeSecret);
        mTellSecret = (FloatingActionButton) view.findViewById(R.id.floatingActionButton_tellSecret);

        mSeeSecret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new PostsHomeActivity();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            }
        });

        mTellSecret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), NewPostActivity.class);
                startActivity(i);
            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Refresh the state of the +1 button each time the activity receives focus.
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }



}

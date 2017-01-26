package com.doufa.sekritoo.posts;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doufa.sekritoo.R;
import com.doufa.sekritoo.posts.fragment.MyPostsFragment;
import com.doufa.sekritoo.posts.fragment.RecentPostsFragment;
import com.doufa.sekritoo.posts.fragment.TopPostsFragment;

public class PostsHomeActivity extends Fragment {

    private static final String TAG = "PostsHomeActivity";

    private FragmentPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.activity_secret, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Secrets");
                // Create the adapter that will return a fragment for each section
                mPagerAdapter = new FragmentPagerAdapter(getFragmentManager()) {
                    private final Fragment[] mFragments = new Fragment[]{
                            new RecentPostsFragment(),
                            new TopPostsFragment(),
                            new MyPostsFragment()
                    };
                    private final String[] mFragmentNames = new String[]{
                            getString(R.string.heading_recent),
                            getString(R.string.heading_my_top_posts),
                            getString(R.string.heading_my_posts)

                    };

                    @Override
                    public Fragment getItem(int position) {
                        return mFragments[position];
                    }

                    @Override
                    public int getCount() {
                        return mFragments.length;
                    }

                    @Override
                    public CharSequence getPageTitle(int position) {
                        return mFragmentNames[position];
                    }
                };
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) view.findViewById(R.id.container);
        mViewPager.setAdapter(mPagerAdapter);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        view.findViewById(R.id.fab_new_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 startActivity(new Intent(getActivity().getApplicationContext(), NewPostActivity.class));

            }
        });

    }
}

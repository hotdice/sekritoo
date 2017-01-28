package com.doufa.sekritou.posts.fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class MyPostsFragment extends PostListFragment {

    public MyPostsFragment() {
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // All my posts
        Query myPostsQuery = databaseReference.child("user-posts")
                .child(getUid());
        return myPostsQuery;
    }
}

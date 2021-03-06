package com.doufa.sekritou.posts.fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class TopPostsFragment extends PostListFragment {

    public TopPostsFragment() {
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // [START my_top_posts_query]
        // My top posts by number of stars
        Query myTopPostsQuery = databaseReference.child("posts")
                .orderByChild("starCount").limitToFirst(20);
        // [END my_top_posts_query]

        return myTopPostsQuery;
    }
}

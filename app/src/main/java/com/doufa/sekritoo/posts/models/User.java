package com.doufa.sekritoo.posts.models;

import com.google.firebase.database.IgnoreExtraProperties;

// [START blog_user_class]
@IgnoreExtraProperties
public class User {

    public String username;
    public String email;
    public long createdAt;
    public long lastVisit;
    public String sexe;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email, String sexe) {
        this.username = username;
        this.email = email;
        this.sexe=sexe;

        createdAt = System.currentTimeMillis();
        lastVisit= System.currentTimeMillis();
    }

}
// [END blog_user_class]

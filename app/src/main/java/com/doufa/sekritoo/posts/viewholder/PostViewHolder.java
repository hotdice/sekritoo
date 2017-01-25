package com.doufa.sekritoo.posts.viewholder;

import android.graphics.Color;
import android.provider.Settings;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.doufa.sekritoo.R;
import com.doufa.sekritoo.posts.PostsHomeActivity;
import com.doufa.sekritoo.posts.models.Post;
import com.doufa.sekritoo.posts.models.User;
import com.doufa.sekritoo.posts.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;

public class PostViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView;
    public TextView authorView;
    public ImageView starView;
    public TextView numStarsView;
    public TextView bodyView;
    public TextView posted_atView;
    public TextView numCommView;

    public PostViewHolder(View itemView) {
        super(itemView);

        titleView = (TextView) itemView.findViewById(R.id.post_title);
        authorView = (TextView) itemView.findViewById(R.id.post_author);
        starView = (ImageView) itemView.findViewById(R.id.star);
        numStarsView = (TextView) itemView.findViewById(R.id.post_num_stars);
        numCommView = (TextView) itemView.findViewById(R.id.post_num_comm);
        bodyView = (TextView) itemView.findViewById(R.id.post_body);
        posted_atView = (TextView) itemView.findViewById(R.id.post_date);
    }

    public void bindToPost(Post post, View.OnClickListener starClickListener) {
        titleView.setText(post.title);
        authorView.setText(post.author);
        numStarsView.setText(String.valueOf(post.starCount));
        numCommView.setText(String.valueOf(post.commCount));
        bodyView.setText(post.body);
        posted_atView.setText(Utils.CalPostTime(post.posted_at));
        starView.setOnClickListener(starClickListener);
    }


}

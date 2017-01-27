package com.doufa.sekritou.posts.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.doufa.sekritou.R;
import com.doufa.sekritou.posts.models.Post;
import com.doufa.sekritou.posts.utils.Utils;

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

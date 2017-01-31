package com.doufa.sekritou.notif;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.doufa.sekritou.R;
import com.doufa.sekritou.posts.PostDetailActivity;
import com.doufa.sekritou.posts.models.Comment;
import com.doufa.sekritou.posts.models.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class IServicePushNotif extends IntentService {

    private String uid;

    private DatabaseReference mDatabase;

    public IServicePushNotif() {
        super("IServicePushNotif");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            mDatabase = FirebaseDatabase.getInstance().getReference();
            newPostListener();
            newCommentListener();
        }
    }


   private void newPostListener () {
       mDatabase.child("posts").addChildEventListener(new ChildEventListener() {
           @Override
           public void onChildAdded(DataSnapshot dataSnapshot, String s) {
               String pkey = dataSnapshot.getKey();
               Post post = dataSnapshot.getValue(Post.class);
                 if (!uid.equals(post.uid))
               pushNotif(pkey,"someone just posted a new secret!",post.body);

           }

           @Override
           public void onChildChanged(DataSnapshot dataSnapshot, String s) {

           }

           @Override
           public void onChildRemoved(DataSnapshot dataSnapshot) {

           }

           @Override
           public void onChildMoved(DataSnapshot dataSnapshot, String s) {

           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });

   }
    private void newCommentListener(){
        mDatabase.child("post-comments").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                String pkey = dataSnapshot.getKey();
//                Comment comm = dataSnapshot.getValue(Comment.class);
//                pushNotif(pkey,comm.author+" just commented on your secret!",comm.text);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
               final String pkey = dataSnapshot.getKey();
                final Comment comm = dataSnapshot.getValue(Comment.class);

                mDatabase.child("posts").child(pkey).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot data) {
                        Post post = data.getValue(Post.class);

                        if (post.uid.equals(uid)) {
                            pushNotif(pkey, comm.author + " just commented on your secret!", comm.text);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void  pushNotif(String post_key, String subject, String body){

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle("Sekritou")
                        .setSubText(body)
                .setContentText(subject)
                .setSmallIcon(R.mipmap.ic_sekritou_icon);

        Intent resultIntent = new Intent(getApplicationContext(),PostDetailActivity.class);
        resultIntent.putExtra("post_key",post_key);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(PostDetailActivity.class);
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(0, mBuilder.build());
    }


}

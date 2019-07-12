package com.example.instagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.instagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class InstagramDetActivity extends AppCompatActivity {

    Post post;

    public ImageView ivPhoto;
    public TextView tvHandle;
    public TextView tvDescription;
    public TextView tvTime;

//    public void styleActionBar() {
//        ActionBar ab = getSupportActionBar();
//
//        ab.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.insta_blue)));
//
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instagram_det);

       // styleActionBar();

        // unwrap the movie and assign field
        post = getIntent().getParcelableExtra(Post.class.getSimpleName());


        // view objects
        ivPhoto = findViewById(R.id.ivPhoto);
        tvDescription = findViewById(R.id.tvDescription);
        tvHandle = findViewById(R.id.tvHandle);
        tvTime = findViewById(R.id.tvTime);


        // set tv title & overview
        tvDescription.setText(post.getDescription());
        tvTime.setText(getRelativeTimeAgo(String.valueOf(post.getCreatedAt())));

        ParseFile image = post.getImage();
        Glide.with(this).load(image.getUrl()).into(ivPhoto);





        ParseQuery<ParseUser> userQuery = new ParseQuery<ParseUser>(ParseUser.class);
        userQuery.addDescendingOrder(Post.KEY_CREATED_AT);
        userQuery.whereEqualTo("objectId", post.getUser().getObjectId());
        userQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> users, ParseException e) {

                tvHandle.setText(users.get(0).getUsername());
            }
        });


    }

    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public static String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }
}

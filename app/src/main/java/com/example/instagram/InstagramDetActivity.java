package com.example.instagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.instagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class InstagramDetActivity extends AppCompatActivity {

    Post post;

    public ImageView ivPhoto;
    public TextView tvHandle;
    public TextView tvDescription;

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


        // set tv title & overview
        tvDescription.setText(post.getDescription());

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
}

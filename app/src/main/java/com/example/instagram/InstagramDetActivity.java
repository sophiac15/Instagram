package com.example.instagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.instagram.model.Post;
import com.parse.ParseFile;

import org.parceler.Parcels;

public class InstagramDetActivity extends AppCompatActivity {

    Post post;

    public ImageView ivPhoto;
    public TextView tvHandle;
    public TextView tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instagram_det);

        // unwrap the movie and assign field
        post = Parcels.unwrap(getIntent().getParcelableExtra(Post.class.getSimpleName()));


        // view objects
        ivPhoto = findViewById(R.id.ivPhoto);
        tvDescription = findViewById(R.id.tvDescription);
        tvHandle = findViewById(R.id.tvHandle);


        // set tv title & overview
        tvDescription.setText(post.getDescription());
        tvHandle.setText(post.getUser().getUsername());

        ParseFile image = post.getImage();
        Glide.with(this).load(image.getUrl()).into(ivPhoto);
    }
}

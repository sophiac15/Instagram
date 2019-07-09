package com.example.instagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseUser;

public class HomeActivity extends AppCompatActivity {

    private TextView descriptionInput;
    private Button refreshButton;
    private Button createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        descriptionInput = findViewById(R.id.description_et);
        createButton = findViewById(R.id.create_btn);
        refreshButton = findViewById(R.id.refresh_btn);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String description = descriptionInput.getText().toString();
                final ParseUser user = ParseUser.getCurrentUser();

                //createPost(description, imageFile, user);
            }
        });

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loadTopPosts();
            }
        });

    }


}

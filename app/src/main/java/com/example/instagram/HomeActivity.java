package com.example.instagram;

import android.content.Intent;
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
    private Button logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        descriptionInput = findViewById(R.id.description_et);
        createButton = findViewById(R.id.create_btn);
        refreshButton = findViewById(R.id.refresh_btn);

        logoutBtn = findViewById(R.id.logout_btn);


        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String description = descriptionInput.getText().toString();
                final ParseUser user = ParseUser.getCurrentUser();

                //createPost(description, imageFile, user);

                final Intent intent = new Intent(HomeActivity.this, MakePostActivity.class);
                startActivity(intent);
                finish();
            }
        });

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loadTopPosts();
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                final Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


}

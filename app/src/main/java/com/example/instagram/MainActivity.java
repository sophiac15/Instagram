package com.example.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {

    private EditText usernameInput;
    private EditText passwordInput;
    private Button loginBtn;

    private EditText usernameSignUp;
    private EditText passwordSignUp;
    private EditText emailSignUp;
    private Button signupBtn;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        usernameInput = findViewById(R.id.username_et);
        passwordInput = findViewById(R.id.password_et);
        loginBtn = findViewById(R.id.login_btn);

        usernameSignUp = findViewById(R.id.username_su);
        passwordSignUp = findViewById(R.id.password_su);
        emailSignUp = findViewById(R.id.email_su);
        signupBtn = findViewById(R.id.signup_btn);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = usernameInput.getText().toString();
                final String password = passwordInput.getText().toString();
                login(username, password);

            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = usernameSignUp.getText().toString();
                final String password = passwordSignUp.getText().toString();
                final String email = emailSignUp.getText().toString();
                signup(username, password, email);
            }
        });

    }

    private void signup(String username, String password, String email) {
        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("SignupActivity", "Signed-up!");
                    final Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.e("SignupActivity", "Sign-up failure!");
                    e.printStackTrace();
                }
            }
        });

    }


    private void login(String username, String password) {
        // TODO
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null) {
                    Log.d("LoginActivity", "Logged in!");
                    final Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.e("LoginActivity", "Login failure!");
                    e.printStackTrace();
                }
            }
        });

    }
}

package com.example.instagram.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.instagram.HomeActivity;
import com.example.instagram.R;
import com.example.instagram.model.Post;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;


public class ComposeFragment extends Fragment {

    private EditText etDescription;
    private ImageView ivPostImage;
    private Button btnCaptureImage;
    private Button btnSubmit;

    private final String TAG = "ComposeFragment";
    private HomeActivity parent;
    private File photoFile;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parent = (HomeActivity) getContext();

        return inflater.inflate(R.layout.fragment_compose, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etDescription = view.findViewById(R.id.description_et);
        ivPostImage = view.findViewById(R.id.parseImageView);
        btnCaptureImage = view.findViewById(R.id.takephoto_btn);
        btnSubmit = view.findViewById(R.id.submit_btn);



        btnCaptureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parent.launchCamera();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = etDescription.getText().toString();
                ParseUser user = ParseUser.getCurrentUser();
                if (photoFile == null || ivPostImage.getDrawable() == null) {
                    Log.e(TAG, "No photo to submit");
                    Toast.makeText(getContext(), "There is no photo", Toast.LENGTH_SHORT).show();
                    return;
                }
                savePost(description, user, photoFile);

            }
        });

    }

    private void savePost(String description, ParseUser user, File photoFile) {

        Post post = new Post();
        post.setDescription(description);
        post.setUser(user);
        post.setImage(new ParseFile(photoFile));

        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.d(TAG, "Error while saving");
                    e.printStackTrace();
                    return;
                } else {
                    Log.d(TAG, "Success!");
                    etDescription.setText("");
                    ivPostImage.setImageResource(0);

                }
            }
        });

    }

    public void gotImage(File takenPhotoFile) {
        photoFile = takenPhotoFile;

        Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
        ivPostImage.setImageBitmap(takenImage);
    }
}

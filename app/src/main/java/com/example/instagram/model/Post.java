package com.example.instagram.model;

import android.os.Parcelable;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


@ParseClassName("Post")
public class Post extends ParseObject implements Parcelable {
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_USER = "user";
    public static final String KEY_CREATED_AT = "createdAt";
    public static final String KEY_LIKED_BY = "likedBy";


    public String getDescription() {
        return getString(KEY_DESCRIPTION);
    }

    public void setDescription(String description) {
        put(KEY_DESCRIPTION, description);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile image) {
        put(KEY_IMAGE, image);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    public boolean isLiked() {
        JSONArray array = getLikedBy();
        for (int i = 0; i < array.length(); i++) {
            try {
                if (array.getJSONObject(i).getString("objectId").equals(ParseUser.getCurrentUser().getObjectId())) {
                    return true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public JSONArray getLikedBy() {
        JSONArray a = getJSONArray(KEY_LIKED_BY);
        if (a == null) {
            return new JSONArray();
        }
        return a;
    }

    public void like() {
        ParseUser user = ParseUser.getCurrentUser();
        add(KEY_LIKED_BY, user);
    }

    public void unlike() {
        ParseUser user = ParseUser.getCurrentUser();
        ArrayList<ParseUser> users = new ArrayList<>();
        users.add(user);
        removeAll(KEY_LIKED_BY, users);
    }

    public int getNumLikes() {
        return getLikedBy().length();
    }




}

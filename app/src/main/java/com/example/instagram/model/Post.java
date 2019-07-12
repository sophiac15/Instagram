package com.example.instagram.model;

import android.os.Parcelable;
import android.text.format.DateUtils;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;


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
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }


}

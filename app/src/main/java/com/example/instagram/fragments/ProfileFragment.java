package com.example.instagram.fragments;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.instagram.EndlessRecyclerViewScrollListener;
import com.example.instagram.PostsAdapter;
import com.example.instagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class ProfileFragment extends HomeFragment {

    private static final String TAG = "ProfileFragment";

    @Override
    protected void setRecyclerView(View view){
        whichFragment=1;
        adapter = new PostsAdapter(getContext(), mPosts, whichFragment);
        rvPosts.setAdapter(adapter);


        GridLayoutManager gridLayoutManager= new GridLayoutManager(getContext(), 3);
        rvPosts.setLayoutManager(gridLayoutManager);


        scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadNextData(5);
            }
        };

        rvPosts.addOnScrollListener(scrollListener);
        queryPosts();
    }


    @Override
    protected void queryPosts() {
        ParseQuery<Post> postQuery = new ParseQuery<Post>(Post.class);
        postQuery.include(Post.KEY_USER);
        postQuery.setLimit(max);
        postQuery.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        postQuery.addDescendingOrder(Post.KEY_CREATED_AT);

        if (!mPosts.isEmpty()) {
            Post latest = mPosts.get(mPosts.size() - 1);
            postQuery.whereLessThan("createdAt", latest.getCreatedAt());
        }


        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error with query");
                    e.printStackTrace();
                    return;
                }
                mPosts.addAll(posts);
                adapter.notifyDataSetChanged();

                for (int i = 0; i < posts.size(); i++) {
                    Post post = posts.get(i);
                    Log.d(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());

                }
            }
        });
    }
}
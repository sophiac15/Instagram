package com.example.instagram.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.instagram.EndlessRecyclerViewScrollListener;
import com.example.instagram.PostsAdapter;
import com.example.instagram.R;
import com.example.instagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";
    public RecyclerView rvPosts;
    PostsAdapter adapter;
    List<Post> mPosts;
    public int whichFragment;

    int max = 3;

    private SwipeRefreshLayout swipeContainer;
    public EndlessRecyclerViewScrollListener scrollListener;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvPosts = view.findViewById(R.id.rvPosts);
        mPosts = new ArrayList<>();

        setRecyclerView(view);
    }

    protected void setRecyclerView(View view){
        whichFragment = 0;
        adapter = new PostsAdapter(getContext(), mPosts, whichFragment);
        rvPosts.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getContext());
        rvPosts.setLayoutManager(linearLayoutManager);


        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadNextData(5);
            }
        };

        rvPosts.addOnScrollListener(scrollListener);

        // Lookup the swipe container view
        swipeContainer = view.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clear();
                queryPosts();
                swipeContainer.setRefreshing(false);

            }

        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        queryPosts();
    }

    public void loadNextData(int offset) {
        max = offset;
        queryPosts();
    }


    protected void queryPosts() {
        ParseQuery<Post> postQuery = new ParseQuery<Post>(Post.class);
        postQuery.include(Post.KEY_USER);
        postQuery.setLimit(max);
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

package com.example.instagram;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.instagram.model.Post;
import com.parse.ParseFile;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private List<Post> posts;
    private Context context;


    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Post post = posts.get(position);
        viewHolder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivPhoto;
        public TextView tvHandle;
        public TextView tvDescription;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPhoto = itemView.findViewById(R.id.ivPhoto);
            tvHandle =  itemView.findViewById(R.id.tvHandle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
        }


        public void bind(Post post) {
            tvHandle.setText(post.getUser().getUsername());
            ParseFile image = post.getImage();
            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(ivPhoto);
            }
            //set description
            tvDescription.setText(post.getDescription());
        }

    }

    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }


}

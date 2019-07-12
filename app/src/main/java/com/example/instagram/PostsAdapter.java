package com.example.instagram;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.instagram.model.Post;
import com.parse.ParseFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private List<Post> posts;
    private Context context;
    public int whichFragment;

    public PostsAdapter(Context context, List<Post> posts, int whichFragment) {
        this.context = context;
        this.posts = posts;
        this.whichFragment=whichFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        context = parent.getContext();

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


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView ivPhoto;
        public TextView tvHandle;
        public TextView tvDescription;
        public ImageButton ibLike;
        public TextView tvLikes;
        public TextView tvTime;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPhoto = itemView.findViewById(R.id.ivPhoto);
            tvHandle =  itemView.findViewById(R.id.tvHandle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            ibLike = itemView.findViewById(R.id.ibLike);
            tvLikes = itemView.findViewById(R.id.tvLikes);
            tvTime = itemView.findViewById(R.id.tvTime);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            if (position != RecyclerView.NO_POSITION) {
                Post post = posts.get(position);
                Intent intent = new Intent(context, InstagramDetActivity.class);
                intent.putExtra(Post.class.getSimpleName(), post);
                context.startActivity(intent);
            }
        }

        public void bind(final Post post) {

            ParseFile image = post.getImage();
            if(whichFragment==0) {
                tvHandle.setText(post.getUser().getUsername());
                tvLikes.setText(Integer.toString(post.getNumLikes()));
                tvTime.setText(getRelativeTimeAgo(String.valueOf(post.getCreatedAt())));

                if (post.isLiked()) {
                    ibLike.setImageResource(R.drawable.ufi_heart_active);
                    ibLike.setColorFilter(Color.argb(255,255,0,0));
                } else {
                    ibLike.setImageResource(R.drawable.ufi_heart);
                    ibLike.setColorFilter(Color.argb(255,0,0,0));
                }

                ibLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!post.isLiked()) {
                            // like tweet
                            post.like();
                            ibLike.setImageResource(R.drawable.ufi_heart_active);
                            ibLike.setColorFilter(Color.argb(255,255,0,0));

                        } else {
                            // unlike tweet
                            post.unlike();
                            ibLike.setImageResource(R.drawable.ufi_heart);
                            ibLike.setColorFilter(Color.argb(255,0,0,0));

                        }
                        post.saveInBackground();
                        tvLikes.setText(Integer.toString(post.getNumLikes()));
                    }
                });
                
                if (image != null) {
                    Glide.with(context).load(image.getUrl()).into(ivPhoto);
                }
                //set description
                tvDescription.setText(post.getDescription());

            } else if (whichFragment == 1) {
                tvHandle.setVisibility(View.GONE);
                tvDescription.setVisibility(View.GONE);
                tvLikes.setVisibility(View.GONE);
                ibLike.setVisibility(View.GONE);


                DisplayMetrics displayMetrics= context.getResources().getDisplayMetrics();
                int pxWidth= displayMetrics.widthPixels;

                ConstraintLayout.LayoutParams layoutParams= new ConstraintLayout.LayoutParams(pxWidth/3, pxWidth/3);
                ivPhoto.setLayoutParams(layoutParams);
                if (image != null) {
                    Glide.with(context).load(image.getUrl()).into(ivPhoto);
                }
            }
        }
    }

    public void clear() {
        posts.clear();
        notifyDataSetChanged();
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

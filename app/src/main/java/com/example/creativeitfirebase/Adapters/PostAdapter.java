package com.example.creativeitfirebase.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.creativeitfirebase.Activities.OthersActivity;
import com.example.creativeitfirebase.Model.Post;
import com.example.creativeitfirebase.R;
import com.example.creativeitfirebase.ViewHolder.PostViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {

    Context context;
    List<Post> postList;

    public PostAdapter(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.post_item,parent,false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {

        Post post = postList.get(position);

        if (post != null){

            holder.getPostUserProfile(post.getCreatorId());

            if (!post.getStatusText().equals("")){
                holder.text.setVisibility(View.VISIBLE);
                holder.text.setText(post.getStatusText());
            }else {
                holder.text.setVisibility(View.GONE);
            }
            if (!post.getStatusImg().equals("")){
                holder.image.setVisibility(View.VISIBLE);
                Glide.with(context).load(post.getStatusImg()).into(holder.image);
            }else {
                holder.image.setVisibility(View.GONE);
            }
        }


        holder.time.setText(post.getCreateOn());

        holder.profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, OthersActivity.class);
                intent.putExtra("otherId",post.getCreatorId());
                context.startActivity(intent);

            }
        });




    }

    @Override
    public int getItemCount() {
        return postList.size();
    }
}

package com.example.creativeitfirebase.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.creativeitfirebase.Activities.ChatActivity;
import com.example.creativeitfirebase.Activities.ProfileActivity;
import com.example.creativeitfirebase.Activities.ProfileEditActivity;
import com.example.creativeitfirebase.Fragment.UserFragment;
import com.example.creativeitfirebase.Model.User;
import com.example.creativeitfirebase.R;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.zip.Inflater;

public class ContactAdapter extends RecyclerView.Adapter<ContactViewHolder> {

    Context context;
    List<User> userList;

    public ContactAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_contact,parent,false);

        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {

        User user = userList.get(position);


        holder.name.setText(user.getUserName());
        holder.phone.setText(user.getUserPhone());
        holder.email.setText(user.getUserEmail());


        Picasso.get()
                .load(user.getUserProfilePic())
                //.centerCrop()
                .into(holder.profile_img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ProfileActivity.class);
                intent.putExtra("userId",user.getUserId());
                intent.putExtra("name",user.getUserName());
                intent.putExtra("phone",user.getUserPhone());
                intent.putExtra("email",user.getUserEmail());
                intent.putExtra("cover_img",user.getUserCoverPic());
                intent.putExtra("profile_img",user.getUserProfilePic());
                context.startActivity(intent);

            }
        });

        holder.sendTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ChatActivity.class);
                intent.putExtra("userId",user.getUserId());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}

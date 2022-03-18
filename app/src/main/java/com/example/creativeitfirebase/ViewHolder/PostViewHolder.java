package com.example.creativeitfirebase.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.creativeitfirebase.Model.User;
import com.example.creativeitfirebase.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostViewHolder extends RecyclerView.ViewHolder {

    public CircleImageView profile_image;
    public TextView name,time,text;
    public ImageView picImg,image;
    DatabaseReference databaseReference;

    public PostViewHolder(@NonNull View itemView) {
        super(itemView);

        profile_image = itemView.findViewById(R.id.profile_image);
        name = itemView.findViewById(R.id.name);
        time = itemView.findViewById(R.id.time);
        text = itemView.findViewById(R.id.text);
        picImg = itemView.findViewById(R.id.picImg);
        image = itemView.findViewById(R.id.image);

        databaseReference = FirebaseDatabase.getInstance().getReference("User");

    }

    public void getPostUserProfile(String userId){

        databaseReference.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User user = snapshot.getValue(User.class);

                name.setText(user.getUserName());
                Picasso.get()
                        .load(user.getUserProfilePic())
                        .into(profile_image);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}

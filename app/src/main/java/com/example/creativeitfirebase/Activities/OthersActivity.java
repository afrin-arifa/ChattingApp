package com.example.creativeitfirebase.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.creativeitfirebase.Adapters.PostAdapter;
import com.example.creativeitfirebase.Model.Post;
import com.example.creativeitfirebase.Model.User;
import com.example.creativeitfirebase.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class OthersActivity extends AppCompatActivity {


    ImageView coverPic,editProfile;
    CircleImageView profilePic;

    TextView name,phone,email;

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    DatabaseReference databaseRefer;

    String currentUserId;

    RecyclerView recyclerView;

    List<Post> postList;

    Intent intent;
    String otherId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others);

        coverPic = findViewById(R.id.cover_img);
        profilePic = findViewById(R.id.profile_image);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        editProfile = findViewById(R.id.editProfile);
        recyclerView = findViewById(R.id.recycler);

        intent = getIntent();

        if (intent.hasExtra("otherId")){

            otherId =  intent.getStringExtra("otherId");

        }

            databaseReference = FirebaseDatabase.getInstance().getReference("User").child(otherId);


        databaseRefer = FirebaseDatabase.getInstance().getReference("Posts");


        postList = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(OthersActivity.this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User user = snapshot.getValue(User.class);

                if (user!= null){

                    name.setText(user.getUserName());
                    phone.setText(user.getUserPhone());
                    email.setText(user.getUserEmail());

                    if (user.getUserCoverPic()!=null){
                        Picasso.get()
                                .load(user.getUserCoverPic())
                                .into(coverPic);
                    } else {
                        coverPic.setImageDrawable(getResources().getDrawable(R.drawable.cover2));
                    }

                    if (user.getUserProfilePic()!=null){
                        Picasso.get()
                                .load(user.getUserProfilePic())
                                .into(profilePic);
                    } else {
                        profilePic.setImageDrawable(getResources().getDrawable(R.drawable.profile1));
                    }



                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






        databaseRefer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                postList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Post post = dataSnapshot.getValue(Post.class);

                    if (otherId.equals(post.getCreatorId())){

                        postList.add(post);
                    }

                }
                PostAdapter postAdapter = new PostAdapter(OthersActivity.this, postList);
                recyclerView.setAdapter(postAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }
}
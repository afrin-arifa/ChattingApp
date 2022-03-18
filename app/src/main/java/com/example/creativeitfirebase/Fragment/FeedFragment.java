package com.example.creativeitfirebase.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.creativeitfirebase.Activities.AddPostActivity;
import com.example.creativeitfirebase.Activities.StartActivity;
import com.example.creativeitfirebase.Adapters.ContactAdapter;
import com.example.creativeitfirebase.Adapters.PostAdapter;
import com.example.creativeitfirebase.Model.Post;
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

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class FeedFragment extends Fragment {

    CircleImageView profile_image;
    AppCompatButton addPost_btn;
    RecyclerView postRecycler;

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    String currentUserId;

    DatabaseReference databaseRefer;

    List<Post> postList;

    public FeedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null) {

            currentUserId = firebaseUser.getUid();
            databaseReference = FirebaseDatabase.getInstance().getReference("User").child(currentUserId);
        }

        profile_image = view.findViewById(R.id.profile_image);
        addPost_btn = view.findViewById(R.id.addPost_btn);
        postRecycler = view.findViewById(R.id.postRecycler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        postRecycler.setLayoutManager(layoutManager);

        postList = new ArrayList<>();
        databaseRefer = FirebaseDatabase.getInstance().getReference("Posts");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User user = snapshot.getValue(User.class);

                if (user != null) {


                    if (user.getUserProfilePic() != null) {
                        Picasso.get()
                                .load(user.getUserProfilePic())
                                .into(profile_image);
                    } else {
                        profile_image.setImageDrawable(getResources().getDrawable(R.drawable.profile1));
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        addPost_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddPostActivity.class));
            }
        });

        databaseRefer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                postList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Post post = dataSnapshot.getValue(Post.class);
                    postList.add(post);
                }
                PostAdapter postAdapter = new PostAdapter(getActivity(), postList);
                postRecycler.setAdapter(postAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;


    }
}
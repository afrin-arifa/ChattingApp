package com.example.creativeitfirebase.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.creativeitfirebase.Adapters.ContactAdapter;
import com.example.creativeitfirebase.Model.User;
import com.example.creativeitfirebase.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class UserFragment extends Fragment {


    public UserFragment() {
        // Required empty public constructor
    }

    DatabaseReference databaseReference;

    List<User> userList;
    RecyclerView recyclerView;

    FirebaseUser firebaseUser;

    String currentUser;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user, container, false);

        userList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recycler);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            currentUser = firebaseUser.getUid();
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("User");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                userList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    User user = dataSnapshot.getValue(User.class);

                    if (!user.getUserId().equals(currentUser)) {

                        userList.add(user);
                    }
                }
                ContactAdapter contactAdapter = new ContactAdapter(getActivity(), userList);
                recyclerView.setAdapter(contactAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;


    }
}
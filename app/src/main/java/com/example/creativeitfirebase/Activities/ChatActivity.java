package com.example.creativeitfirebase.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.creativeitfirebase.Adapters.ChatAdapter;
import com.example.creativeitfirebase.Model.Chat;
import com.example.creativeitfirebase.Model.User;
import com.example.creativeitfirebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {

    Intent intent;
    String receiverId;

    Toolbar toolbar;

    CircleImageView profilePic;
    TextView name;

    DatabaseReference databaseReference;
    DatabaseReference chatReference;

    EditText msg;
    ImageView sendMsg;
    RecyclerView chatRecycler;

    FirebaseUser firebaseUser;
    String senderId;

    List<Chat> chatList;
    ChatAdapter chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        intent = getIntent();
        receiverId = intent.getStringExtra("userId");

        chatList = new ArrayList<>();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser!= null){
            senderId = firebaseUser.getUid();

        }

        toolbar = findViewById(R.id.chat_toolbar);
        setSupportActionBar(toolbar);
        profilePic = findViewById(R.id.profile_image);
        name = findViewById(R.id.name);

        msg = findViewById(R.id.typeMsg);
        sendMsg = findViewById(R.id.sendMsg);
        chatRecycler = findViewById(R.id.chatRecycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ChatActivity.this);
        layoutManager.setStackFromEnd(true);
        chatRecycler.setLayoutManager(layoutManager);

        databaseReference = FirebaseDatabase.getInstance().getReference("User").child(receiverId);
        chatReference = FirebaseDatabase.getInstance().getReference("Chats");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User user = snapshot.getValue(User.class);
                Picasso.get()
                        .load(user.getUserProfilePic())
                        .into(profilePic);

                name.setText(user.getUserName());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg_str = msg.getText().toString();

                String chatId = chatReference.push().getKey();

                HashMap<String,Object> chatMap = new HashMap<>();

                chatMap.put("sender",senderId);
                chatMap.put("receiver",receiverId);
                chatMap.put("message",msg_str);
                chatMap.put("chatId",chatId);

                chatReference.child(chatId).setValue(chatMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){

                            Toast.makeText(ChatActivity.this, "Send", Toast.LENGTH_SHORT).show();
                            msg.setText("");
                        }

                    }
                });


            }
        });

        chatReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                chatList.clear();

                for (DataSnapshot dataSnapshot :snapshot.getChildren()) {
                    Chat chat =dataSnapshot.getValue(Chat.class);

                    if (senderId.equals(chat.getSender()) && receiverId.equals(chat.getReceiver())
                    || senderId.equals(chat.getReceiver()) && receiverId.equals(chat.getSender()) )
                    {
                        chatList.add(chat);
                    }
                }
                chatAdapter = new ChatAdapter(ChatActivity.this,chatList);
                chatRecycler.setAdapter(chatAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
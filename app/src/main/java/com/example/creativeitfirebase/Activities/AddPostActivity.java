package com.example.creativeitfirebase.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.creativeitfirebase.Model.Post;
import com.example.creativeitfirebase.Model.User;
import com.example.creativeitfirebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddPostActivity extends AppCompatActivity {

    CircleImageView profile_image;
    AppCompatButton addPost_btn;
    ImageView picimg,previewImg;
    EditText status_text;

    Uri postImgUri;

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    String currentUserId;

    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String time;

    DatabaseReference databaseRefe;
    StorageReference storageReference;

    String postImg;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser!=null){

            currentUserId = firebaseUser.getUid();
            databaseReference = FirebaseDatabase.getInstance().getReference("User").child(currentUserId);
        }

        dialog = new ProgressDialog(this);
        dialog.setTitle("Please wait..");
        dialog.setMessage("Your status is updating...");

        profile_image = findViewById(R.id.profile_image);
        addPost_btn = findViewById(R.id.addPost_btn);
        picimg = findViewById(R.id.picimg);
        previewImg = findViewById(R.id.previewImg);
        status_text = findViewById(R.id.status_text);

        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyy hh:mm a");
        time = simpleDateFormat.format(calendar.getTime());

        databaseRefe = FirebaseDatabase.getInstance().getReference("Posts");
        storageReference = FirebaseStorage.getInstance().getReference("PostImg").child(currentUserId);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User user = snapshot.getValue(User.class);

                if (user!= null){


                    if (user.getUserProfilePic()!=null){
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

        picimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,101);
            }
        });

        addPost_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text = status_text.getText().toString();
                StorageReference storageRefe = storageReference.child("Img_"+ System.currentTimeMillis());

                dialog.show();
                if (postImgUri!= null){

                    storageRefe.putFile(postImgUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()){
                                dialog.dismiss();
                                storageRefe.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        postImg = String.valueOf(uri);

                                        String postId = databaseRefe.push().getKey();

                                        Post post = new Post(text,postImg,postId,currentUserId,time);

                                        databaseRefe.child(postId).setValue(post).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(AddPostActivity.this, "Post Updated", Toast.LENGTH_SHORT).show();
                                                finish();

                                            }
                                        });

                                    }
                                });

                            }

                        }
                    });
                }
                else {

                    String postId = databaseRefe.push().getKey();

                    Post post = new Post(text,"",postId,currentUserId,time);

                    databaseRefe.child(postId).setValue(post).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(AddPostActivity.this, "Post Updated", Toast.LENGTH_SHORT).show();
                            finish();

                        }
                    });
                }


            }
        });

        status_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()>0){

                    addPost_btn.setVisibility(View.VISIBLE);
                } else {
                    if (postImgUri== null){
                        addPost_btn.setVisibility(View.INVISIBLE);
                    }

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==101 && resultCode == RESULT_OK && data!= null){
            postImgUri = data.getData();
            previewImg.setVisibility(View.VISIBLE);
            previewImg.setImageURI(postImgUri);

            addPost_btn.setVisibility(View.VISIBLE);
        }

    }
}
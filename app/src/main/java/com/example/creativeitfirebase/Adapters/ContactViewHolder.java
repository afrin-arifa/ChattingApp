package com.example.creativeitfirebase.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.creativeitfirebase.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactViewHolder extends RecyclerView.ViewHolder {

    CircleImageView profile_img;
    ImageView sendTxt;
    TextView name,phone, email;


    public ContactViewHolder(@NonNull View itemView) {

        super(itemView);

        profile_img = itemView.findViewById(R.id.profile_image);
        name= itemView.findViewById(R.id.name);
        phone= itemView.findViewById(R.id.phone);
        email= itemView.findViewById(R.id.email);
        sendTxt = itemView.findViewById(R.id.sendTxt);
    }
}

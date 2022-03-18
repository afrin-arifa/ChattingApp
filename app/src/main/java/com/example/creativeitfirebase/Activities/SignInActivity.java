package com.example.creativeitfirebase.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.creativeitfirebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    TextInputEditText user_email,user_password;

    AppCompatButton signin_btn;

    TextView no_have_account_text;

    FirebaseAuth firebaseAuth;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        firebaseAuth = FirebaseAuth.getInstance();

        dialog = new ProgressDialog(SignInActivity.this);
        dialog.setMessage("Please wait...");


        user_email = findViewById(R.id.user_email);
        user_password = findViewById(R.id.user_password);

        signin_btn = findViewById(R.id.signin_btn);
        no_have_account_text = findViewById(R.id.no_have_account_text);

        no_have_account_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SignInActivity.this,RegisterActivity.class));
                finish();

            }
        });


        signin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = user_email.getText().toString().trim();
                String pass = user_password.getText().toString().trim();

                if (email.isEmpty()){
                    ShowAlert("Email field can not be empty !");
                }else  if (pass.isEmpty()){
                    ShowAlert("Password field can not be empty !");
                }else  if (pass.length()<6){
                    ShowAlert("Password should be more than 6 ");
                }else {

                    dialog.show();

                    firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            dialog.dismiss();
                            if (task.isSuccessful()){

                                Toast.makeText(SignInActivity.this, "Sign in successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignInActivity.this,MainActivity.class));
                                finish();
                            }

                        }
                    });

                }

            }
        });


    }

    private void ShowAlert(String s) {

        AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
        builder.setTitle("Error");
        builder.setMessage(s);
        builder.setIcon(R.drawable.ic_error);

        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = builder.create();

        alertDialog.show();


    }

}
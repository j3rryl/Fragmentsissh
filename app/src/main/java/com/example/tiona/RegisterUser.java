package com.example.tiona;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth mAuth;
    DatabaseReference reference;
    private EditText email,password,fullName,age;
    private Button registerUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        mAuth=FirebaseAuth.getInstance();

        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        fullName=findViewById(R.id.fullName);
        age=findViewById(R.id.age);
        registerUser=findViewById(R.id.registerUser);

        registerUser.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.registerUser:
                registerUser();
                break;
        }
    }
    public void registerUser(){
        String t_email=email.getText().toString().trim();
        String t_password=password.getText().toString().trim();
        String t_age=age.getText().toString().trim();
        String t_name=fullName.getText().toString().trim();
        if(t_name.isEmpty()){
            fullName.setError("Full name required");
            fullName.requestFocus();
            return;
        }
        if(t_age.isEmpty()){
            age.setError("Age required");
            age.requestFocus();
            return;
        }
        if(t_email.isEmpty()){
            email.setError("Email required");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(t_email).matches()){
            email.setError("Valid email required");
            email.requestFocus();
            return;
        }
        if(t_password.isEmpty()){
            password.setError("Password required");
            password.requestFocus();
            return;
        }
        if(t_password.length()<6){
            password.setError("Password length should be 6 characters");
            password.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(t_email,t_password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(id);
                            FirebaseUser firebaseUser=mAuth.getCurrentUser();
                            assert firebaseUser!=null;
                            String userID=firebaseUser.getUid();
                            reference=FirebaseDatabase.getInstance().getReference("Users").child(userID);
                            HashMap<String,String>hashMap=new HashMap<>();
                            hashMap.put("id",userID);
                            hashMap.put("fullname",t_name);
                            //hashMap.put("age",t_age);
                            //hashMap.put("email",t_email);
                            hashMap.put("imageURL","default");
                            //User user=new User(userID,t_name,t_age,t_email,"default");
                           reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Intent intent =new Intent(RegisterUser.this,Profile.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                        //Toast.makeText(RegisterUser.this,"User has been registered successfully",Toast.LENGTH_LONG).show();

                                    } else {
                                        Toast.makeText(RegisterUser.this,"Failed to register, try again",Toast.LENGTH_LONG).show();

                                    }
                                }
                            });
                        } else {
                            Toast.makeText(RegisterUser.this,"Failed to register the user",Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }

}
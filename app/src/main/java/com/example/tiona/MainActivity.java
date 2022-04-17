package com.example.tiona;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button register, login;
    private EditText email, password;
    private TextView forgotPw;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    private ProgressBar progressBar;

    @Override
    protected void onStart() {
        super.onStart();
        //checks if user is null
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null){
            Intent intent = new Intent(MainActivity.this,Profile.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        register = findViewById(R.id.register);
        login = findViewById(R.id.login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        forgotPw = findViewById(R.id.forgotPw);
        progressBar=findViewById(R.id.progressBar);

        login.setOnClickListener(this);
        register.setOnClickListener(this);
        forgotPw.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                startActivity(new Intent(this, RegisterUser.class));
                break;
            case R.id.login:
                userLogin();
                break;
            case R.id.forgotPw:
                startActivity(new Intent(MainActivity.this,ForgotPassword.class));
                break;
        }
    }

    private void userLogin() {
        String t_email = email.getText().toString().trim();
        String t_password = password.getText().toString().trim();
        if (t_email.isEmpty()) {
            email.setError("Email required");
            email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(t_email).matches()) {
            email.setError("Valid email required");
            email.requestFocus();
            return;
        }
        if (t_password.isEmpty()) {
            password.setError("Password required");
            password.requestFocus();
            return;
        }
        if (t_password.length() < 6) {
            password.setError("Password length should be 6 characters");
            password.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(t_email, t_password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            Intent intent =new Intent(MainActivity.this,Profile.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                            /*if (user.isEmailVerified()) {
                                startActivity(new Intent(MainActivity.this, Profile.class));
                            } else {
                                user.sendEmailVerification();
                                Toast.makeText(MainActivity.this, "Check your email to verify", Toast.LENGTH_LONG).show();
                            }*/
                        } else {
                            Toast.makeText(MainActivity.this, "Failed to login! Please check your credentials", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
package com.example.tiona;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.example.tiona.Chat;
import com.example.tiona.Model.MessageAdapter;
import com.example.tiona.Model.User;
import com.example.tiona.Model.UserAdapter;
import com.example.tiona.databinding.ChatItemLeftBinding;
import com.example.tiona.databinding.ChatItemRightBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessagingActivity extends AppCompatActivity {
    private Toolbar toolBar;
    private Intent intent;
    private FirebaseUser firebaseUser;
    private DatabaseReference reference;
    private CircleImageView profileImage;
    private TextView title;
    private EditText textSend;
    private ImageButton btnSend;
    private List<Chat> mchat;
    private RecyclerView recyclerView;
    private MessageAdapter messageAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        toolBar = findViewById(R.id.toolBar);
        profileImage = findViewById(R.id.profileImage);
        title = findViewById(R.id.title);
        textSend = findViewById(R.id.textSend);
        btnSend = findViewById(R.id.btnSend);
        recyclerView= (RecyclerView)findViewById(R.id.recyclerVIew);


        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        intent = getIntent();
        final String userID = intent.getStringExtra("id");
        String fullname = intent.getStringExtra("fullname");
        title.setText(fullname);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text_message=textSend.getText().toString();
                if(!text_message.equals("")){
                    sendMessage(firebaseUser.getUid(),userID,text_message);
                } else {
                    Toast.makeText(MessagingActivity.this,"Field cannot be blank",Toast.LENGTH_LONG).show();
                }
                textSend.setText("");
            }
        });
        reference=FirebaseDatabase.getInstance().getReference("Users").child(userID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user =snapshot.getValue(User.class);
                if (user.getImageURL().equals("default")) {
                    profileImage.setImageResource(R.mipmap.ic_launcher);
                } else {
                    Glide.with(MessagingActivity.this).load(user.getImageURL()).into(profileImage);
                }
                readMessages(firebaseUser.getUid(),userID,user.getImageURL());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void sendMessage(String sender, String receiver, String message) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);

        reference.child("Chats").push().setValue(hashMap);
    }
    private void readMessages(String myid,String userid,String imageurl) {

        mchat=new ArrayList<>();
        reference=FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mchat.clear();
                for(DataSnapshot snap:snapshot.getChildren()){
                    Chat chat=snap.getValue(Chat.class);
                    if(chat.getReceiver().equals(myid)&&chat.getSender().equals(userid) ||
                            chat.getReceiver().equals(userid)&&chat.getSender().equals(myid)){
                        mchat.add(chat);
                    }
                    messageAdapter=new MessageAdapter(MessagingActivity.this,mchat,imageurl);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
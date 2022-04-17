package com.example.tiona.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tiona.Model.User;
import com.example.tiona.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileFragment extends Fragment {
    private TextView userName;
    private ImageView profileImage;
    private DatabaseReference reference;
    private FirebaseUser firebaseUser;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
//        userName=view.findViewById(R.id.username);
//        profileImage=view.findViewById(R.id.profileImage);
//
//        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
//        reference=FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                User user =snapshot.getValue(User.class);
//                userName.setText(user.getFullname());
//                if(user.getImageURL().equals("default")){
//                    profileImage.setImageResource(R.mipmap.ic_launcher);
//                }
//                else {
//                    Glide.with(getActivity()).load(user.getImageURL()).into(profileImage);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
        return view;
    }
}
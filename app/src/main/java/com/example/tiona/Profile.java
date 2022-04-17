package com.example.tiona;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.tiona.Fragments.ChatsFragment;
import com.example.tiona.Fragments.ProfileFragment;
import com.example.tiona.Fragments.UsersFragment;
import com.example.tiona.Model.User;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    private String userID;
    private Toolbar toolBar;
    private CircleImageView profileImage;
    private TextView title;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        toolBar=(Toolbar)findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tabLayout=findViewById(R.id.tabLayout);
        viewPager=findViewById(R.id.viewPager);
        title=findViewById(R.id.title);
        profileImage=findViewById(R.id.profileImage);


        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        reference=FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        //userID= firebaseUser.getUid();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile=snapshot.getValue(User.class);

                    //fullName,email,age; string fname=userprofile.fullName etc
                    String t_fullName=userProfile.getFullname();
                    title.setText(t_fullName);
                    if(userProfile.getImageURL().equals("default")){
                        profileImage.setImageResource(R.mipmap.ic_launcher);
                    } else {
                        Glide.with(Profile.this).load(userProfile.getImageURL()).into(profileImage);
                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Profile.this,MainActivity.class));
                finish();
                return true;
            case R.id.profile:
                FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.linear, new ProfileFragment());
                ft.addToBackStack(null);
                ft.commit();
                return true;
        }
        return false;
    }
    private void init(){
            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
            viewPagerAdapter.addFragment(new ChatsFragment(),"Chat");
            viewPagerAdapter.addFragment(new UsersFragment(),"Users");
            viewPager.setAdapter(viewPagerAdapter);
            tabLayout.setupWithViewPager(viewPager);

    }
    class ViewPagerAdapter extends FragmentPagerAdapter{
        private ArrayList<Fragment> fragments;
        private ArrayList<String>titles;
        public ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
            this.fragments=new ArrayList<>();
            this.titles=new ArrayList<>();
        }
        public void addFragment(Fragment fragment, String title){
                fragments.add(fragment);
                titles.add(title);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}
package com.example.tiona.Model;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tiona.MessagingActivity;
import com.example.tiona.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

//public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{
//
//    private Context mContext;
//    private List<User> mUser;
//    FirebaseUser firebaseUser;
//
//    public UserAdapter(Context mContext, List<User> mUser) {
//        this.mContext = mContext;
//        this.mUser = mUser;
//    }
//
//    @NonNull
//    @Override
//    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item, parent, false);
//        return new UserAdapter.ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
//        User user= mUser.get(position);
//        holder.username.setText(user.getFullname());
//        if(user.getImageURL().equals("default")){
//            holder.profileImage.setImageResource(R.mipmap.ic_launcher);
//        } else {
//            Glide.with(mContext).load(user.getImageURL()).into(holder.profileImage);
//        }
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return mUser.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        public TextView username;
//        public ImageView profileImage;
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            username=itemView.findViewById(R.id.username);
//            profileImage=itemView.findViewById(R.id.profile_image);
//        }
//    }
//
//}

public class UserAdapter extends FirebaseRecyclerAdapter<User, UserAdapter.MyViewHolder> {
    //private List<User>mUsers;
    private Context mContext;

    public UserAdapter(@NonNull FirebaseRecyclerOptions<User> options) {
        super(options);
        this.mContext=mContext;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext=parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull User model) {
//User user = mUsers.get(position);
        holder.username.setText(model.getFullname());
        if (model.getImageURL().equals("default")) {
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(holder.profile_image.getContext()).load(model.getImageURL()).into(holder.profile_image);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, MessagingActivity.class);
                intent.putExtra("id",model.getId());
                intent.putExtra("fullname",model.getFullname());
                intent.putExtra("imageURL",model.getImageURL());
                mContext.startActivity(intent);
            }
        });
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView username;
        public ImageView profile_image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            profile_image = itemView.findViewById(R.id.profile_image);
        }
    }
}

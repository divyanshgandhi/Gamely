package com.stimuligaming.gamely.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.stimuligaming.gamely.MessageActivity;
import com.stimuligaming.gamely.R;
import com.stimuligaming.gamely.helperClasses.Buddy;

import java.util.ArrayList;

public class BuddyAdapter extends RecyclerView.Adapter<BuddyAdapter.BuddyHolder> {
    private Context context;
    private ArrayList<Buddy> list;

    public BuddyAdapter(Context context, ArrayList<Buddy> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public BuddyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.buddy_item, parent, false);
        BuddyHolder bh = new BuddyHolder(v);
        return bh;
    }

    @Override
    public void onBindViewHolder(@NonNull BuddyHolder holder, int position) {
        Buddy bud = list.get(position);
        holder.buddyName.setText(bud.getName());
        holder.buddyUsername.setText(bud.getUsername());
        if(bud.getImageURL().equals("default")){
            holder.buddyImage.setImageResource(R.drawable.ic_profile);
        }else{
            Glide.with(context).load(bud.getImageURL()).into(holder.buddyImage);
        }
        holder.messageBtn.setOnClickListener(click ->{
            Intent intent = new Intent(context, MessageActivity.class);
            intent.putExtra("uid", bud.getUid());
            intent.putExtra("name", bud.getName());
            intent.putExtra("image", bud.getImageURL());
            context.startActivity(intent);
        });
        holder.buddyCard.setOnClickListener(click -> {
            //TODO: Add a Buddy's profile
            //Intent intent = new Intent(context, ProfileActivity.class);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class BuddyHolder extends RecyclerView.ViewHolder {
        TextView buddyName, buddyUsername;
        ImageView buddyImage, messageBtn;
        CardView buddyCard;

        BuddyHolder(@NonNull View itemView) {
            super(itemView);
            this.buddyName = itemView.findViewById(R.id.buddy_name);
            this.buddyUsername = itemView.findViewById(R.id.buddy_username);
            this.buddyImage = itemView.findViewById(R.id.buddy_image);
            this.buddyCard = itemView.findViewById(R.id.buddy_card);
            this.messageBtn = itemView.findViewById(R.id.message_button);
        }
    }
}

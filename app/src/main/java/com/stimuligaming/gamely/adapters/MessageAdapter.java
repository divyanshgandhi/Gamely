package com.stimuligaming.gamely.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.stimuligaming.gamely.R;
import com.stimuligaming.gamely.helperClasses.Message;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageHolder> {
    Context mContext;
    ArrayList<Message> list;

    public MessageAdapter(Context context, ArrayList<Message> list){
        this.mContext = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.buddy_item, parent, false);
        MessageHolder mh = new MessageHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position) {
        if(list.get(position).getIsLeft()){
            holder.rightLayout.setVisibility(View.GONE);
            holder.leftContent.setText(list.get(position).getContent());
            if(list.get(position).getImage().equals("default")){
                holder.leftImage.setImageResource(R.drawable.ic_profile);
            }else{
                Glide.with(mContext).load(list.get(position).getImage()).into(holder.leftImage);
            }
        }else{
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightContent.setText(list.get(position).getContent());
            if(list.get(position).getImage().equals("default")){
                holder.rightImage.setImageResource(R.drawable.ic_profile);
            }else{
                Glide.with(mContext).load(list.get(position).getImage()).into(holder.rightImage);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MessageHolder extends RecyclerView.ViewHolder{
        RelativeLayout leftLayout, rightLayout;
        TextView leftContent, rightContent;
        ImageView leftImage, rightImage;

        MessageHolder(@NonNull View itemView) {
            super(itemView);
            leftLayout = itemView.findViewById(R.id.left_layout);
            rightLayout = itemView.findViewById(R.id.right_layout);
            leftContent = itemView.findViewById(R.id.left_content);
            rightContent = itemView.findViewById(R.id.right_content);
            leftImage = itemView.findViewById(R.id.left_image);
            rightImage = itemView.findViewById(R.id.right_image);
        }
    }
}

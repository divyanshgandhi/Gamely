package com.stimuligaming.gamely.adapters;

import android.content.Context;
import android.content.Intent;
import android.icu.text.MeasureFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.stimuligaming.gamely.helperClasses.Application;
import com.stimuligaming.gamely.GameActivity;
import com.stimuligaming.gamely.R;

import java.util.ArrayList;
import java.util.List;

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.ViewHolder> {
    private List<Application> list;
    private Context context;

    public AppAdapter(Context context, ArrayList<Application> customizedListView) {
        this.list = customizedListView;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.application_item, parent, false);
        AppAdapter.ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AppAdapter.ViewHolder holder, int position) {
        holder.textInListView.setText(list.get(position).getName());
        holder.imageInListView.setImageDrawable(list.get(position).getIcon());
        holder.packageInListView.setText(list.get(position).getPackageName());
        holder.playButton.setOnClickListener(v -> {
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(list.get(position).getPackageName());
            if(intent != null){
                context.startActivity(intent);
            }
        });
        holder.mainCard.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), GameActivity.class);
            v.getContext().startActivity(intent);
        });
        if (list.size()<=4){
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            holder.gameHolder.setLayoutParams(lp);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CardView mainCard;
        TextView textInListView;
        ImageView imageInListView;
        TextView packageInListView;
        ImageView playButton;
        LinearLayout gameHolder;

        public ViewHolder(View view){
            super(view);
            textInListView = view.findViewById(R.id.app_name);
            packageInListView = view.findViewById(R.id.app_package);
            imageInListView = view.findViewById(R.id.app_icon);
            playButton = view.findViewById(R.id.app_button);
            mainCard = view.findViewById(R.id.main_card);
            gameHolder = view.findViewById(R.id.game_holder);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), GameActivity.class);
            v.getContext().startActivity(intent);
        }
    }
}

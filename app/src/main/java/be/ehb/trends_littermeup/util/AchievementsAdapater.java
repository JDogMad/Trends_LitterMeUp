package be.ehb.trends_littermeup.util;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

import be.ehb.trends_littermeup.Achievements;
import be.ehb.trends_littermeup.Post;
import be.ehb.trends_littermeup.R;

public class AchievementsAdapater extends RecyclerView.Adapter<AchievementsAdapater.ViewHolder> {
    private ArrayList<Achievements> items;

    public AchievementsAdapater(){
        items = new ArrayList<>();
    }

    public void addItems(List<Achievements> items){
        this.items = new ArrayList<>(items);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_title, txt_description;

        public ViewHolder(View itemView) {
            super(itemView);

            txt_title = itemView.findViewById(R.id.txt_achievement_title);
            txt_description = itemView.findViewById(R.id.txt_achievement_description);
        }
    }

    @NonNull
    @Override
    public AchievementsAdapater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_achievements, parent, false);
        return new AchievementsAdapater.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AchievementsAdapater.ViewHolder holder, int position) {
        Achievements currentItem = items.get(position);
        holder.txt_title.setText(currentItem.getTitle());
        holder.txt_description.setText(currentItem.getDescription());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

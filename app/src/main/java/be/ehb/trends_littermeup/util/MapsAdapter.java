package be.ehb.trends_littermeup.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.GoogleMap;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;

import be.ehb.trends_littermeup.Post;
import be.ehb.trends_littermeup.R;

public class MapsAdapter extends RecyclerView.Adapter<MapsAdapter.ViewHolder> {
    private ArrayList<Post> items;

    public MapsAdapter(){
        items = new ArrayList<>();
    }

    public void addItems(List<Post> items){
        this.items = new ArrayList<>(items);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_title, txt_description;

        public ViewHolder(View itemView) {
            super(itemView);

            txt_title = itemView.findViewById(R.id.txt_history_title);
            txt_description = itemView.findViewById(R.id.txt_category);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post currentItem = items.get(position);
        holder.txt_title.setText(currentItem.getTitel());
        holder.txt_description.setText(currentItem.getDescription());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

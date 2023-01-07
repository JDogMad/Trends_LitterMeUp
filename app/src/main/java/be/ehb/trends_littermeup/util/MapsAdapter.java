package be.ehb.trends_littermeup.util;

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
    private GoogleMap map;

    public void addItems(List<Post> items){
        this.items = new ArrayList<>(items);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);

        }
    }

    @NonNull
    @Override
    public MapsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MapsAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}

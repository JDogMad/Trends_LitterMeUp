package be.ehb.trends_littermeup.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import be.ehb.trends_littermeup.Database;
import be.ehb.trends_littermeup.Post;
import be.ehb.trends_littermeup.R;
import be.ehb.trends_littermeup.User;
import java.util.ArrayList;


import be.ehb.trends_littermeup.R;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {
    // Arraylists for the items in the DataBass blijkbaar
    // Gewoon uncomment
    // private ArrayList<Post> items;


    // Hier moet je niks doen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_userPosted;
        ImageView img_userPosted;

        public ViewHolder(View itemView) {
            super(itemView);

            txt_userPosted = itemView.findViewById(R.id.txt_titlePosted);
            img_userPosted = itemView.findViewById(R.id.img_userPosted_pic);
        }
    }


    // Hier moet je niks doen
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_feed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Voorbeeld: Hier set je de individuele zaken
        // --> txt_userPosted => title
        /* Note currentItem = items.get(position);
        holder.txt_userPosted.setText(currentItem.getTitle()); */
    }

    @Override
    public int getItemCount() {
        // Size van uw arraylist
        // return items.size();
        return 0;
    }

}

package be.ehb.trends_littermeup.util;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;

import java.util.List;

import be.ehb.trends_littermeup.Database;
import be.ehb.trends_littermeup.Post;
import be.ehb.trends_littermeup.R;
import be.ehb.trends_littermeup.User;
import java.util.ArrayList;


import be.ehb.trends_littermeup.R;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {
    private ArrayList<Post> items;

    public FeedAdapter(){
        items = new ArrayList<>();
    }

    public void addItems(List<Post> items){
        this.items = new ArrayList<>(items);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_postTitle, txt_postDescription;
        ImageView img_userPosted, img_feedPost;

        public ViewHolder(View itemView) {
            super(itemView);

            txt_postTitle = itemView.findViewById(R.id.txt_titlePosted);
            txt_postDescription = itemView.findViewById(R.id.txt_descriptionPosted);
            img_feedPost = itemView.findViewById(R.id.img_feedPost);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_feed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post currentItem = items.get(position);
        holder.txt_postTitle.setText(currentItem.getTitel());
        holder.txt_postDescription.setText(currentItem.getDescription());

        System.out.println(currentItem.getNameFile());
        FirebaseStorage.getInstance().getReference().child(currentItem.getNameFile()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String imageUrl = uri.toString();
                // Now you can use Glide library to load image from the url
                Glide.with(holder.itemView.getContext()).load(imageUrl).into(holder.img_feedPost);
            }
        });


        holder.img_feedPost.setImageBitmap(currentItem.getBitmap());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

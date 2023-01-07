package be.ehb.trends_littermeup.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import be.ehb.trends_littermeup.Post;
import be.ehb.trends_littermeup.R;
import be.ehb.trends_littermeup.User;


public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {
    private ArrayList<User> items;

    public FriendsAdapter(){
        items = new ArrayList<>();
    }

    public void addItems(List<User> items){
        this.items = new ArrayList<>(items);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_friendsName, txt_friendsLevel;
        ImageView img_friendsPic;

        public ViewHolder(View itemView) {
            super(itemView);

            txt_friendsName = itemView.findViewById(R.id.txt_username_friend);
            txt_friendsLevel = itemView.findViewById(R.id.txt_level_friend);
            img_friendsPic = itemView.findViewById(R.id.img_friends_pic);
        }
    }


    @NonNull
    @Override
    public FriendsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_friends, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsAdapter.ViewHolder holder, int position) {
        User currentItem = items.get(position);
        holder.txt_friendsName.setText(currentItem.getUsername());
        //holder.txt_friendsLevel.setText(currentItem.getPoints());
    }

    @Override
    public int getItemCount() {
        return items.size();

    }


}

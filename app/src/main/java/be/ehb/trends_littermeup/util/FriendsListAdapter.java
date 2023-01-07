package be.ehb.trends_littermeup.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import be.ehb.trends_littermeup.Post;
import be.ehb.trends_littermeup.R;
import be.ehb.trends_littermeup.User;

public class FriendsListAdapter extends RecyclerView.Adapter<FriendsListAdapter.ViewHolder> {
    private ArrayList<User> users;

    public FriendsListAdapter() {
        users = new ArrayList<>();
    }

    public void addItems(List<User> items){
        this.users = new ArrayList<>(items);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_username_friend;
        private Button btn_add_friend;

        public ViewHolder(View itemView) {
            super(itemView);
            txt_username_friend = itemView.findViewById(R.id.txt_search_username);
            btn_add_friend = itemView.findViewById(R.id.btn_addFriend);
            btn_add_friend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        User user = users.get(position);
                        int friendId = user.getId();
                        System.out.println("FRIENDOOOOOOOO " + friendId);
                        // TODO: DON'T FORGET TO ADD THE FRIEND'S ID TO THE DATABASE
                    }
                }
            });
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_search_friends, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = users.get(position);
        holder.txt_username_friend.setText(user.getUsername());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}

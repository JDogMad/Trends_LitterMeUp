package be.ehb.trends_littermeup.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import be.ehb.trends_littermeup.R;
import be.ehb.trends_littermeup.User;

public class FriendsListAdapter extends ArrayAdapter<User> {
    private Context mContext;
    private List<User> mUsers;


    public FriendsListAdapter(@NonNull Context context, List<User> users) {
        super(context, 0, users);
        mContext = context;
        mUsers = users;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.adapter_search_friends, parent, false);
        }

        User currentUser = mUsers.get(position);

        TextView usernameTextView = listItem.findViewById(R.id.txt_search_friend);
        usernameTextView.setText(currentUser.getUsername());

        return listItem;
    }
}

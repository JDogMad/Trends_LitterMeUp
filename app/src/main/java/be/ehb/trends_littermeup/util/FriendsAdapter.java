package be.ehb.trends_littermeup.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import be.ehb.trends_littermeup.R;

// TODO: THE BINDING WITH THE DATBASE
public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {
    private Context context;
    // private List<YourModel> modelList;


    @NonNull
    @Override
    public FriendsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_friends, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsAdapter.ViewHolder holder, int position) {
        // This needs to get the data from the database
        // Something like
        /* YourModel model = modelList.get(position);
        holder.textView.setText(model.getName());
        holder.imageView.setImageResource(model.getImage()); */
    }

    @Override
    public int getItemCount() {
        // This needs to change
        // It will return someting like return modelList.size();
        return 10;
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

    public FriendsAdapter( /* List<YourModel> modelList , */ Context context) {
        // this.modelList = modelList;
        this.context = context;
    }
}

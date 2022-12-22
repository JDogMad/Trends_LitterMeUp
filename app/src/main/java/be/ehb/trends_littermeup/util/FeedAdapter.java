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

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {
    // private List<YourModel> modelList;
    private Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_feed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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

        TextView txt_userPosted;
        ImageView img_userPosted;

        public ViewHolder(View itemView) {
            super(itemView);

            txt_userPosted = itemView.findViewById(R.id.txt_userPosted);
            img_userPosted = itemView.findViewById(R.id.img_userPosted_pic);
        }
    }

    public FeedAdapter( /* List<YourModel> modelList , */ Context context) {
        // this.modelList = modelList;
        this.context = context;
    }
}

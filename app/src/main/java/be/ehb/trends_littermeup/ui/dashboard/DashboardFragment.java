package be.ehb.trends_littermeup.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import be.ehb.trends_littermeup.Post;
import be.ehb.trends_littermeup.R;
import be.ehb.trends_littermeup.databinding.FragmentDashboardBinding;
import be.ehb.trends_littermeup.ui.feed.NewPostFragment;
import be.ehb.trends_littermeup.util.FeedAdapter;

public class DashboardFragment extends Fragment {
    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //TODO: REDIRECT BIG TRASH and SMALL TRASH => TO NEWPOST
        // REMOVE IN LAYOUT OPEN CAMERA

        binding.btnNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                NewPostFragment newPostFragment = new NewPostFragment();

                Button button1 = getView().findViewById(R.id.btn_camera_smallTrash);
                Button button2 = getView().findViewById(R.id.btn_camera_bigTrash);
                Button button3 = getView().findViewById(R.id.btn_openCamera);
                FloatingActionButton button4 = getView().findViewById(R.id.btn_new_post);

                button1.setVisibility(View.GONE);
                button2.setVisibility(View.GONE);
                button3.setVisibility(View.GONE);
                button4.setVisibility(View.GONE);

                transaction.replace(R.id.const_dashboard, newPostFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        DashboardViewModel viewModel = new ViewModelProvider(getActivity()).get(DashboardViewModel.class);
        FeedAdapter feedAdapter = new FeedAdapter();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        binding.rcFeedPosts.setAdapter(feedAdapter);
        binding.rcFeedPosts.setLayoutManager(layoutManager);
        viewModel.getAllPostsDB().observe(getViewLifecycleOwner(), new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> posts) {
                    feedAdapter.addItems(posts);
                }
        });

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
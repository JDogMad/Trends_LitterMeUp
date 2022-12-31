package be.ehb.trends_littermeup.ui.feed;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.api.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Random;

import be.ehb.trends_littermeup.Database;
import be.ehb.trends_littermeup.Post;
import be.ehb.trends_littermeup.R;
import be.ehb.trends_littermeup.databinding.FragmentNewpostBinding;
import be.ehb.trends_littermeup.ui.dashboard.DashboardFragment;
import be.ehb.trends_littermeup.ui.settings.SettingsFragment;

public class NewPostFragment extends Fragment {
    private static final int CAMERA_REQUEST_CODE = 1;
    private FragmentNewpostBinding binding;
    private Bitmap bitmap;
    private Database db = new Database();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NewPostViewModel newPostViewModel =
                new ViewModelProvider(this).get(NewPostViewModel.class);

        binding = FragmentNewpostBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Button addPic = root.findViewById(R.id.btn_addPic);
        Button newPost = root.findViewById(R.id.btn_post);
        EditText titlePost = root.findViewById(R.id.txt_newPost_title);
        EditText descriptionPost = root.findViewById(R.id.txt_newPost_description);
        addPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                takeAndSavePicture();

            }
        });

        newPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(titlePost.getText().toString().isEmpty()) && !(titlePost.getText().toString().isEmpty()) && bitmap != null){
                    Post post = new Post(bitmap, titlePost.getText().toString(), descriptionPost.getText().toString());
                    post.setNameFile("Image-" + post.getId() + ".jpg");
                    savePicture(post);
                    db.add(post);

                    FragmentManager fragmentManager = getFragmentManager();
                    if (fragmentManager != null) {
                        Button button1 = getView().findViewById(R.id.btn_addPic);
                        Button button2 = getView().findViewById(R.id.btn_post);

                        button1.setVisibility(View.GONE);
                        button2.setVisibility(View.GONE);

                        fragmentManager.beginTransaction()
                                .replace(R.id.const_newpost, new DashboardFragment())
                                .commit();


                    }
                }else{
                    if(titlePost.getText().toString().isEmpty()){
                        int redColorValue = Color.RED;
                        titlePost.setBackground(getResources().getDrawable(R.drawable.app_shape_2_error));
                    }
                    if(descriptionPost.getText().toString().isEmpty()){
                        int redColorValue = Color.RED;
                        descriptionPost.setBackground(getResources().getDrawable(R.drawable.app_shape_5_error));
                    }
                    if(bitmap == null){
                        Toast.makeText(getActivity(), "No Picture included", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return root;
    }


    public void takeAndSavePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST_CODE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            bitmap = imageBitmap;
        }
    }


    public void savePicture(Post post) {
        File myDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "saved_images");
        boolean success = true;
        if (!myDir.exists()) {
            success = myDir.mkdirs();
        }
        if (success) {

            File file = new File(myDir, post.getNameFile());
            if (file.exists())
                file.delete();
            try {
                FileOutputStream out = new FileOutputStream(file);
                post.getBitmap().compress(Bitmap.CompressFormat.JPEG, 90, out);

                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

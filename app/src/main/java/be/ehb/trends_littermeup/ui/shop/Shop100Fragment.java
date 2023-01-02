package be.ehb.trends_littermeup.ui.shop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import be.ehb.trends_littermeup.R;
import be.ehb.trends_littermeup.databinding.FragmentShopValue100Binding;
import be.ehb.trends_littermeup.ui.profile.ProfileFragment;

public class Shop100Fragment extends Fragment {
    private FragmentShopValue100Binding binding;
    Button btn_back;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentShopValue100Binding.inflate(inflater, container, false);
        View root = binding.getRoot();


        btn_back = root.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.const_shop100, new ProfileFragment());

                Button button1 = getView().findViewById(R.id.btn_redeem_100);
                Button button2 = getView().findViewById(R.id.btn_back);

                button1.setVisibility(View.GONE);
                button2.setVisibility(View.GONE);

                fragmentTransaction.commit();
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

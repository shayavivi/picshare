package com.example.picshare_new;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.picshare_new.model.Model;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {
Button logout;

    public Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        logout = v.findViewById(R.id.profileLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Model.instance.logout();
                MyApp.setCurrentUserId(null);
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

//        Button btn = v.findViewById(R.id.profile_back_button);

        return  v;
    }

}

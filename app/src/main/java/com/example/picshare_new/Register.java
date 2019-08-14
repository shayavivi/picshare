package com.example.picshare_new;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.picshare_new.model.Model;


/**
 * A simple {@link Fragment} subclass.
 */
public class Register extends Fragment {

    EditText email, password;
    Button toLogIn, Register;
    ImageView profileImage;

    public Register() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        email = v.findViewById(R.id.registerEmail);
        password = v.findViewById(R.id.registerPassword);
        toLogIn = v.findViewById(R.id.RegisterToLogInBtn);
        profileImage = v.findViewById(R.id.registerProfileImage);
        Register = v.findViewById(R.id.registerBtn);

        toLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email != null && password != null) {
                    String sEmail = email.getText().toString();
                    String sPassword = password.getText().toString();
                    String sImage = "an image";
                    Model.instance.register(sEmail, sPassword, sImage, new Model.basicListener(){

                        @Override
                        public void onSuccess(String id) {
                            MyApp.setCurrentUserId(id);
                            getActivity().finish();
                        }

                        @Override
                        public void onFailure(String e) {
                            showMessage(e);
                        }

                    });
                }
                else
                    showMessage("please enter all parameters");
            }
        });

        return v;
    }
    private void showMessage(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

}

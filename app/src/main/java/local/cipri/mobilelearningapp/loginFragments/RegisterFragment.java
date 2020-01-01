package local.cipri.mobilelearningapp.loginFragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import local.cipri.mobilelearningapp.LoginActivity;
import local.cipri.mobilelearningapp.MainActivity;
import local.cipri.mobilelearningapp.R;


public class RegisterFragment extends Fragment {

    private Button btnRegister;
    private EditText etName;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etPasswordConfirm;

    public RegisterFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        initForm(view);
        return view;
    }

    private void initForm(View view){
        etName = view.findViewById(R.id.etUserName);
        btnRegister = view.findViewById(R.id.btnNewRegister);
        etEmail = view.findViewById(R.id.etNewEmail);
        etPassword = view.findViewById(R.id.etNewPassword);
        etPasswordConfirm = view.findViewById(R.id.etNewPasswordConfirm);
        initBtnRegister();
    }

    private void initBtnRegister(){
        btnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
    }

}

package local.cipri.mobilelearningapp.loginFragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import local.cipri.mobilelearningapp.LoginActivity;
import local.cipri.mobilelearningapp.MainActivity;
import local.cipri.mobilelearningapp.R;


public class RegisterFragment extends Fragment {

    private Button btnRegister;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etPasswordConfirm;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    public RegisterFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        initForm(view);
        return view;
    }

    private void initForm(View view) {
        btnRegister = view.findViewById(R.id.btnNewRegister);
        etEmail = view.findViewById(R.id.etNewEmail);
        etPassword = view.findViewById(R.id.etNewPassword);
        etPasswordConfirm = view.findViewById(R.id.etNewPasswordConfirm);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        initBtnRegister();
    }

    private void initBtnRegister() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String passconf = etPasswordConfirm.getText().toString();
                if (validateCredentials(email, password, passconf)) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                firebaseUser = firebaseAuth.getCurrentUser();
                                Toast.makeText(getContext(), R.string.success_register, Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getContext(), LoginActivity.class));
                            } else {
                                Toast.makeText(getContext(), R.string.error_register_default, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private boolean validateCredentials(String email, String pass, String confirm) {
        if (LoginFragment.validateCredentials(email, pass))
            return true;
        return false;
    }

}

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

import local.cipri.mobilelearningapp.MainActivity;
import local.cipri.mobilelearningapp.R;


public class LoginFragment extends Fragment {
    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnRegister;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private static final String emailRegEx = "[^@]+@[^\\.]+\\..+";
    private static final String LOGGED_USER = "logged_user";


    public LoginFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initForm(view);
        debugLogin();
        return view;
    }

    private void debugLogin() {
        //todo: remove before sending, debug purpose only.
        etEmail.setText("debug@mla.com");
        etPassword.setText("123456");
        btnLogin.performClick();
    }

    private void initForm(View view) {
        btnLogin = view.findViewById(R.id.btnLogin);
        btnRegister = view.findViewById(R.id.btnRegister);
        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        initBtnLogin();
        initBtnRegister();
    }

    private void initBtnLogin() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                if (validateCredentials(email, password))
                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                firebaseUser = firebaseAuth.getCurrentUser();
                                Toast.makeText(getContext(), R.string.success_login, Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getContext(), MainActivity.class);
                                intent.putExtra(LOGGED_USER, firebaseUser);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getContext(), R.string.error_login, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                else {
                    //todo: beter handling, with red underlines etc etc
                    Toast.makeText(getContext(), R.string.error_login, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    protected static boolean validateCredentials(String email, String password) {
        if (email != null
                && password != null
                && !email.isEmpty()
                && !password.isEmpty()
                && password.length() >= 6
                && email.matches(emailRegEx))
            return true;
        return false;
    }

    private void initBtnRegister() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.login_frame_container, new RegisterFragment())
                        .commit();
            }
        });
    }

}

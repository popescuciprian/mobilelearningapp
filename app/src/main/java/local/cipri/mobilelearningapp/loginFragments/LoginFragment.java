package local.cipri.mobilelearningapp.loginFragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    public static final String LOGGED_USER = "logged_user";
    public final static String AUTOLOGIN_PREF = "autologin_pref";
    public final static String PASSWORD = "pass_pref";
    public final static String EMAIL = "email_pref";

    private SharedPreferences preferences;

    public LoginFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        autoLogin();
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initForm(view);
        //debugLogin();
        return view;
    }

    private void debugLogin() {
        //todo: comment before sending, debug purpose only.
        try {
            etEmail.setText("debug@mla.com");
            etPassword.setText("123456");
            btnLogin.performClick();
        } catch
        (Exception e) {
        }
    }

    private void initForm(View view) {
        btnLogin = view.findViewById(R.id.btnLogin);
        btnRegister = view.findViewById(R.id.btnRegister);
        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        initBtnLogin();
        initBtnRegister();
    }

    private void autoLogin() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        preferences = getActivity().getSharedPreferences(AUTOLOGIN_PREF, Context.MODE_PRIVATE);
        String pass = preferences.getString(PASSWORD, "");
        String email = preferences.getString(EMAIL, "");
        if (!pass.isEmpty() && !email.isEmpty()) {
            loginWith(email, pass);
            Toast.makeText(getContext(), R.string.auto_login_toast, Toast.LENGTH_LONG).show();
        }
    }

    private void initBtnLogin() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                loginWith(email, password);
            }
        });
    }

    private void loginWith(String email, String password) {
        if (validateCredentials(email, password))
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), R.string.success_login, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        intent.putExtra(LOGGED_USER, email + ":" + password);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getContext(), R.string.error_login, Toast.LENGTH_LONG).show();
                    }
                }
            });
        else {
            Toast.makeText(getContext(), R.string.error_login, Toast.LENGTH_LONG).show();
        }
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

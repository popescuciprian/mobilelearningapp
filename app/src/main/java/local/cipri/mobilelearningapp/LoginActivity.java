package local.cipri.mobilelearningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import local.cipri.mobilelearningapp.loginFragments.LoginFragment;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.login_frame_container, new LoginFragment())
                .commit();
    }
}

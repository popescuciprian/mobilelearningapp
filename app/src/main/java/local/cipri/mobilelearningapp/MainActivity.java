package local.cipri.mobilelearningapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import local.cipri.mobilelearningapp.coursesFragments.CoursesFragment;
import local.cipri.mobilelearningapp.coursesFragments.QuizViewer;
import local.cipri.mobilelearningapp.coursesFragments.QuizzesFragment;
import local.cipri.mobilelearningapp.loginFragments.LoginFragment;
import local.cipri.mobilelearningapp.network.CourseParser;
import local.cipri.mobilelearningapp.network.DownloadCoursesTask;
import local.cipri.mobilelearningapp.util.Course;
import local.cipri.mobilelearningapp.util.CourseQuizz;
import local.cipri.mobilelearningapp.util.Quizz;

public class MainActivity extends AppCompatActivity {
    private static final String COURSES_SOURCE="https://api.myjson.com/bins/wk1me";
    public static final String REQUIEST_KEY="reqCode";
    public static final int COURSES_REQUEST_CODE = 100;
    public static final int QUIZZES_REQUEST_CODE = 200;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configNavigation();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == COURSES_REQUEST_CODE && resultCode == RESULT_OK && data!=null){
            int coursesCount =data.getParcelableArrayListExtra(CoursesFragment.COURSES_KEY).size();
            Toast.makeText(this, "Courses returned to Main activity:"+coursesCount, Toast.LENGTH_LONG).show();
        }
        if(requestCode == QUIZZES_REQUEST_CODE && resultCode == RESULT_OK && data!=null){
            String testDesc = data.getStringExtra(QuizzesFragment.QUIZZ_CHOOSEN);
            String scoreText = data.getStringExtra(QuizViewer.SCORE_KEY);
            Toast.makeText(this, testDesc +" completed with "+ scoreText, Toast.LENGTH_LONG).show();
        }
    }

    private NavigationView.OnNavigationItemSelectedListener itemSelected() {
        return new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.main_nav_courses) {
                    Intent intent = new Intent(getApplicationContext(), CoursesAndQuizzes.class);
                    intent.putExtra(REQUIEST_KEY,COURSES_REQUEST_CODE);
                    startActivityForResult(intent, COURSES_REQUEST_CODE);
                } else if (item.getItemId() == R.id.main_nav_quizzes) {
                    Intent intent = new Intent(getApplicationContext(), CoursesAndQuizzes.class);
                    intent.putExtra(REQUIEST_KEY,QUIZZES_REQUEST_CODE);
                    startActivityForResult(intent, QUIZZES_REQUEST_CODE);
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        };
    }

    private void configNavigation() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(itemSelected());
    }


}

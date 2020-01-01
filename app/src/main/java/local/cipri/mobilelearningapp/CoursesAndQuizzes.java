package local.cipri.mobilelearningapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import local.cipri.mobilelearningapp.coursesFragments.CourseViewerFragment;
import local.cipri.mobilelearningapp.coursesFragments.CoursesFragment;
import local.cipri.mobilelearningapp.coursesFragments.QuizzesFragment;
import local.cipri.mobilelearningapp.network.CourseParser;
import local.cipri.mobilelearningapp.network.DownloadCoursesTask;
import local.cipri.mobilelearningapp.util.Course;

public class CoursesAndQuizzes extends AppCompatActivity {
    public static final String COURSES_SOURCE = "https://api.myjson.com/bins/ymgmu";  //"https://api.myjson.com/bins/wk1me";
    private List<Course> courses = new ArrayList<>();
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_and_quizzes);
        initFragment(getIntent().getExtras().getInt(MainActivity.REQUIEST_KEY));

    }

    private void initFragment(int reqCode) {
        if (reqCode == MainActivity.COURSES_REQUEST_CODE) {
            Fragment courseFragment = new CoursesFragment();
            bundle = new Bundle();
            courseFragment.setArguments(bundle);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.courses_frame_container,courseFragment)
                    .commit();
        } else if (reqCode == MainActivity.QUIZZES_REQUEST_CODE) {
            Fragment quizzesFragment = new QuizzesFragment();
            bundle = new Bundle();
            quizzesFragment.setArguments(bundle);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.courses_frame_container, quizzesFragment)
                    .commit();
        }
    }

}

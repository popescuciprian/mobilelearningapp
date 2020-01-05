package local.cipri.mobilelearningapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.auth.FirebaseUser;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import local.cipri.mobilelearningapp.coursesFragments.CoursesFragment;
import local.cipri.mobilelearningapp.coursesFragments.QuizViewer;
import local.cipri.mobilelearningapp.coursesFragments.QuizzesFragment;
import local.cipri.mobilelearningapp.database.service.CourseQuizzService;
import local.cipri.mobilelearningapp.database.service.CourseService;
import local.cipri.mobilelearningapp.database.service.QuizzService;
import local.cipri.mobilelearningapp.loginFragments.LoginFragment;
import local.cipri.mobilelearningapp.network.CourseParser;
import local.cipri.mobilelearningapp.network.DownloadCoursesTask;
import local.cipri.mobilelearningapp.util.Course;
import local.cipri.mobilelearningapp.util.CourseQuizz;
import local.cipri.mobilelearningapp.util.ItemMainAdapter;
import local.cipri.mobilelearningapp.util.Quizz;

public class MainActivity extends AppCompatActivity {
    public static final String REQUIEST_KEY = "reqCode";
    public static final int COURSES_REQUEST_CODE = 100;
    public static final int QUIZZES_REQUEST_CODE = 200;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private ListView lvCourses;
    private ListView lvQuizzes;
    private ItemMainAdapter courseAdapter;
    private ItemMainAdapter quizzAdapter;
    private Button btnMainSelect;
    private TextView tvDate;
    private Switch aSwitch;

    private SharedPreferences preferences;
    private final String SWITCH_PREF = "switch_pref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvDate = findViewById(R.id.et_date_select_main);
        initListViews();
        initButton();
        configNavigation();
        setPreferences();
    }

    private void setPreferences() {

    }

    @SuppressLint("StaticFieldLeak")
    private void initButton() {
        btnMainSelect = findViewById(R.id.btn_select_from_db);
        btnMainSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sDate = tvDate.getText().toString();
                if (validateDate(sDate)) {
                    long timestamp = getTimestampFromDate(sDate);
                    new CourseService.getCoursesAfterDate(getApplicationContext(), timestamp) {
                        @Override
                        protected void onPostExecute(List<Course> courses) {
                            List<Object> objectCourses = new ArrayList<>();
                            if (courses != null && courses.size() > 0) {
                                objectCourses.addAll(courses);
                                for (Object course : objectCourses)
                                    new CourseQuizzService.getCourseQuizzesForCourse(getApplicationContext(), ((Course) course).getId()) {
                                        @Override
                                        protected void onPostExecute(List<CourseQuizz> courseQuizzes) {
                                            if (courseQuizzes != null && courseQuizzes.size() == 1) {
                                                ((Course) course).setCourseQuizz(courseQuizzes.get(0));
                                                new QuizzService.getChoicesForCourse(getApplicationContext(), courseQuizzes.get(0).getId()) {
                                                    @Override
                                                    protected void onPostExecute(List<Quizz> quizzes) {
                                                        if (quizzes != null && quizzes.size() > 0) {
                                                            Quizz[] arr = new Quizz[quizzes.size()];
                                                            for (int i = 0; i < arr.length; i++)
                                                                arr[i] = quizzes.get(i);
                                                            courseQuizzes.get(0).setQuizzes(arr);
                                                        }
                                                        if (objectCourses.indexOf(course) == objectCourses.size() - 1) {
                                                            initLvAdapters(objectCourses);
                                                        }
                                                    }
                                                }.execute();
                                            }
                                        }
                                    }.execute();
                            } else {
                                freeListViews();
                                Toast.makeText(getApplicationContext(), R.string.err_select_main, Toast.LENGTH_LONG).show();
                            }
                        }
                    }.execute();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.err_date_Select, Toast.LENGTH_LONG).show();
                }
            }

            private long getTimestampFromDate(String sDate) {
                try {
                    return new SimpleDateFormat("dd-MM-yyyy").parse(sDate).getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                    return 0;
                }
            }

            private boolean validateDate(String sDate) {
                return sDate.matches("^([0-2][0-9]|(3)[0-1])(\\-)(((0)[0-9])|((1)[0-2]))(\\-)\\d{4}");
            }
        });
    }

    private void initListViews() {
        lvCourses = findViewById(R.id.lv_main_courses);
        lvQuizzes = findViewById(R.id.lv_main_quizzes);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.3);
        ViewGroup.LayoutParams quizzLp = lvQuizzes.getLayoutParams();
        ViewGroup.LayoutParams coursLp = lvCourses.getLayoutParams();
        quizzLp.height = height;
        coursLp.height = height;
        lvQuizzes.setLayoutParams(quizzLp);
        lvCourses.setLayoutParams(coursLp);
    }

    private void initLvAdapters(List<Object> items) {
        courseAdapter = new ItemMainAdapter(getApplicationContext(), R.layout.rl_main_item, items, getLayoutInflater());
        lvQuizzes.setAdapter(courseAdapter);
        List<Object> quizzes = new ArrayList<>();
        for (Object item : items) {
            int len = ((Course) item).getCourseQuizz().getQuizzes().length;
            for (int i = 0; i < len; i++) {
                quizzes.add(((Course) item).getCourseQuizz().getQuizzes()[i]);
            }
        }
        quizzAdapter = new ItemMainAdapter(getApplicationContext(), R.layout.rl_main_item, quizzes, getLayoutInflater());
        lvCourses.setAdapter(quizzAdapter);
    }

    private void freeListViews() {
        lvQuizzes.setAdapter(null);
        lvCourses.setAdapter(null);
        //oh well...
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Object> cursuri = new ArrayList<>();
        if (requestCode == COURSES_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            cursuri.addAll(data.getParcelableArrayListExtra(CoursesFragment.COURSES_KEY));
        }
        if (requestCode == QUIZZES_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            String testDesc = data.getStringExtra(QuizzesFragment.QUIZZ_CHOOSEN);
            String scoreText = data.getStringExtra(QuizViewer.SCORE_KEY);
            //todo Ceva cu scorul si userul.
        }
    }

    private NavigationView.OnNavigationItemSelectedListener itemSelected() {
        return new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.main_nav_courses) {
                    Intent intent = new Intent(getApplicationContext(), CoursesAndQuizzes.class);
                    intent.putExtra(REQUIEST_KEY, COURSES_REQUEST_CODE);
                    startActivityForResult(intent, COURSES_REQUEST_CODE);
                } else if (item.getItemId() == R.id.main_nav_quizzes) {
                    Intent intent = new Intent(getApplicationContext(), CoursesAndQuizzes.class);
                    intent.putExtra(REQUIEST_KEY, QUIZZES_REQUEST_CODE);
                    startActivityForResult(intent, QUIZZES_REQUEST_CODE);
                }
//                else if (item.getItemId() == R.id.app_bar_switch) {
//                    initSwitch();
//                    return false;
//                }
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

    private void initSwitch() {
        preferences = getSharedPreferences(LoginFragment.AUTOLOGIN_PREF, Context.MODE_PRIVATE);
        aSwitch = findViewById(R.id.switch_main);
        aSwitch.setChecked(preferences.getBoolean(SWITCH_PREF, false));

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = preferences.edit();
                if (isChecked) {
                    String[] credentials = getIntent().getStringExtra(LoginFragment.LOGGED_USER).split(":");
                    editor.putString(LoginFragment.EMAIL, credentials[0]);
                    editor.putString(LoginFragment.PASSWORD, credentials[1]);
                    editor.putBoolean(SWITCH_PREF, true);
                    editor.apply();
                } else {
                    editor.putString(LoginFragment.EMAIL, "");
                    editor.putString(LoginFragment.PASSWORD, "");
                    editor.putBoolean(SWITCH_PREF, false);
                    editor.apply();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        initSwitch();
        return super.onCreateOptionsMenu(menu);
    }
}

package local.cipri.mobilelearningapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Toast;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import local.cipri.mobilelearningapp.coursesFragments.CourseViewerFragment;
import local.cipri.mobilelearningapp.coursesFragments.CoursesFragment;
import local.cipri.mobilelearningapp.coursesFragments.QuizzesFragment;
import local.cipri.mobilelearningapp.database.service.CourseQuizzService;
import local.cipri.mobilelearningapp.database.service.CourseService;
import local.cipri.mobilelearningapp.database.service.QuizzService;
import local.cipri.mobilelearningapp.network.CourseParser;
import local.cipri.mobilelearningapp.network.DownloadCoursesTask;
import local.cipri.mobilelearningapp.util.Course;
import local.cipri.mobilelearningapp.util.CourseQuizz;
import local.cipri.mobilelearningapp.util.Quizz;

public class CoursesAndQuizzes extends AppCompatActivity {
    public static final String COURSES_SOURCE = "https://api.myjson.com/bins/17r9r8";
    private Bundle bundle;

    private List<Course> dbCourses = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_and_quizzes);
        readCoursesFromDb();
    }
    
    @SuppressLint("StaticFieldLeak")
    private void readCoursesFromDb() {
        new CourseService.getCourses(getApplicationContext()) {
            @Override
            protected void onPostExecute(List<Course> courses) {
                if (courses != null && courses.size() > 0) {
                    dbCourses = new ArrayList<Course>();
                    dbCourses.addAll(courses);
                    for (Course course : dbCourses)
                        new CourseQuizzService.getCourseQuizzesForCourse(getApplicationContext(), course.getId()) {
                            @Override
                            protected void onPostExecute(List<CourseQuizz> courseQuizzes) {
                                if (courseQuizzes != null && courseQuizzes.size() == 1) {
                                    course.setCourseQuizz(courseQuizzes.get(0));
                                    new QuizzService.getChoicesForCourse(getApplicationContext(), courseQuizzes.get(0).getId()) {
                                        @Override
                                        protected void onPostExecute(List<Quizz> quizzes) {
                                            if (quizzes != null && quizzes.size() > 0) {
                                                Quizz[] arr = new Quizz[quizzes.size()];
                                                for (int i = 0; i < arr.length; i++)
                                                    arr[i] = quizzes.get(i);
                                                courseQuizzes.get(0).setQuizzes(arr);
                                            }
                                            if (dbCourses.indexOf(course) == dbCourses.size() - 1) {
                                                initFragment(getIntent().getExtras().getInt(MainActivity.REQUIEST_KEY));
                                            }
                                        }
                                    }.execute();
                                }
                            }
                        }.execute();
                } else {
                    initFragment(getIntent().getExtras().getInt(MainActivity.REQUIEST_KEY));
                }
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public static void insertCoursesToDb(List<Course> courses, Context context) {
        if (courses != null) {
            for (Course course : courses)
                new CourseService.insertCourse(context) {
                    @Override
                    protected void onPostExecute(Course course) {
                        new CourseQuizzService.insertCourseQuizz(context) {
                            @Override
                            protected void onPostExecute(CourseQuizz courseQuizz) {
                                for (Quizz quizz : courseQuizz.getQuizzes())
                                    new QuizzService.insertQuizz(context)
                                            .execute(quizz);
                            }
                        }.execute(course.getCourseQuizz());
                    }
                }.execute(course);
        }
    }

    private void initFragment(int reqCode) {
        if (reqCode == MainActivity.COURSES_REQUEST_CODE) {
            Fragment courseFragment = new CoursesFragment();
            bundle = new Bundle();
            courseFragment.setArguments(bundle);
            bundle.putParcelableArrayList("dbCourses", (ArrayList<? extends Parcelable>) dbCourses);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.courses_frame_container, courseFragment)
                    .commit();
        } else if (reqCode == MainActivity.QUIZZES_REQUEST_CODE) {
            Fragment quizzesFragment = new QuizzesFragment();
            bundle = new Bundle();
            List<CourseQuizz> dbCourseQuizzes = null;
            if (dbCourses != null) {
                dbCourseQuizzes = new ArrayList<>();
                for (Course c : dbCourses)
                    dbCourseQuizzes.add(c.getCourseQuizz());
            }
            bundle.putParcelableArrayList("dbQuizzes", (ArrayList<? extends Parcelable>) dbCourseQuizzes);
            quizzesFragment.setArguments(bundle);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.courses_frame_container, quizzesFragment)
                    .commit();
        }
    }

}

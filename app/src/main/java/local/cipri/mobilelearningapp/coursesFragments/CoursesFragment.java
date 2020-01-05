package local.cipri.mobilelearningapp.coursesFragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import local.cipri.mobilelearningapp.CoursesAndQuizzes;
import local.cipri.mobilelearningapp.MainActivity;
import local.cipri.mobilelearningapp.R;
import local.cipri.mobilelearningapp.database.service.CourseQuizzService;
import local.cipri.mobilelearningapp.database.service.CourseService;
import local.cipri.mobilelearningapp.database.service.QuizzService;
import local.cipri.mobilelearningapp.network.CourseParser;
import local.cipri.mobilelearningapp.network.DownloadCoursesTask;
import local.cipri.mobilelearningapp.util.Course;
import local.cipri.mobilelearningapp.util.CourseAdapter;
import local.cipri.mobilelearningapp.util.CourseQuizz;
import local.cipri.mobilelearningapp.util.Quizz;


public class CoursesFragment extends Fragment {
    public static final String COURSES_KEY = "coursesKey";
    public static final String HTML_COURSE_KEY = "htmlKey";
    private ListView lvCourses;
    private List<Course> courses = new ArrayList<>();

    public CoursesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_courses, container, false);
        initListView(view);
        return view;
    }

    private void initListView(View view) {
        lvCourses = view.findViewById(R.id.lv_courses);
        if (getArguments().get("dbCourses") == null)
            downloadCourses();
        else {
            courses = getArguments().getParcelableArrayList("dbCourses");
            getArguments().putParcelableArrayList(COURSES_KEY, (ArrayList) courses);
        }
        CourseAdapter adapter = new CourseAdapter(getContext(), R.layout.rl_course_item, courses, getLayoutInflater());
        lvCourses.setAdapter(adapter);
        lvCourses.setOnItemClickListener(lvCoursesItemSelected());
    }

    @SuppressLint("StaticFieldLeak")
    private void downloadCourses() {
        try {
            new DownloadCoursesTask() {
                @Override
                protected void onPostExecute(String s) {
                    courses = CourseParser.parseJson(s);
                    CoursesAndQuizzes.insertCoursesToDb(courses, getContext());
                    getArguments().putParcelableArrayList(COURSES_KEY, (ArrayList) courses);
                    Toast.makeText(getActivity().getApplicationContext(), "Download Complete!", Toast.LENGTH_SHORT).show();
                    if (getContext() != null) {
                        CourseAdapter adapter = new CourseAdapter(getContext(), R.layout.rl_course_item, courses, getLayoutInflater());
                        lvCourses.setAdapter(adapter);
                        lvCourses.setOnItemClickListener(lvCoursesItemSelected());
                    }
                }
            }.execute(new URL(CoursesAndQuizzes.COURSES_SOURCE));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    @SuppressLint("StaticFieldLeak")
    private void readCoursesFromDb(Context context) {
        new CourseService.getCourses(context) {
            @Override
            protected void onPostExecute(List<Course> dbCourses) {
                List<Course> lst = new ArrayList<Course>();
                lst.addAll(dbCourses);
            }
        }.execute();
    }

    private AdapterView.OnItemClickListener lvCoursesItemSelected() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment courseViewerFragment = new CourseViewerFragment();
                getArguments().putString(HTML_COURSE_KEY, ((Course) getArguments().getParcelableArrayList(COURSES_KEY).get(position)).getHtmlContent());
                courseViewerFragment.setArguments(getArguments());
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.courses_frame_container, courseViewerFragment)
                        .commit();
                Intent intent = getActivity().getIntent().putParcelableArrayListExtra(COURSES_KEY, (ArrayList<? extends Parcelable>) courses);
                getActivity().setResult(getActivity().RESULT_OK, intent);
            }
        };
    }

}

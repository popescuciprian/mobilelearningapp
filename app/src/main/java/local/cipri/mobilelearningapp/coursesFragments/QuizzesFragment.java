package local.cipri.mobilelearningapp.coursesFragments;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
import local.cipri.mobilelearningapp.R;
import local.cipri.mobilelearningapp.network.CourseParser;
import local.cipri.mobilelearningapp.network.DownloadCoursesTask;
import local.cipri.mobilelearningapp.util.Course;
import local.cipri.mobilelearningapp.util.CourseQuizz;
import local.cipri.mobilelearningapp.util.CourseQuizzAdapter;
import local.cipri.mobilelearningapp.util.Quizz;


public class QuizzesFragment extends Fragment {
    public static final String QUIZZES_KEY = "quizzesKey";
    public static final String QUIZZ_CHOOSEN = "quizzChosenKey";
    private ListView lvQuizzes;
    private List<CourseQuizz> courseQuizzes = new ArrayList<>();

    public QuizzesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quizzes, container, false);
        initListView(view);
        return view;
    }

    private void initListView(View view) {
        lvQuizzes = view.findViewById(R.id.lv_quizzes);
        if (getArguments().get("dbQuizzes") == null)
            downloadQuizzes();
        else
            courseQuizzes = getArguments().getParcelableArrayList("dbQuizzes");
        CourseQuizzAdapter adapter = new CourseQuizzAdapter(getContext(), R.layout.rl_quizz_item, courseQuizzes, getLayoutInflater());
        lvQuizzes.setAdapter(adapter);
        lvQuizzes.setOnItemClickListener(lvQuizzesItemSelected());
    }

    @SuppressLint("StaticFieldLeak")
    private void downloadQuizzes() {
        try {
            new DownloadCoursesTask() {
                @Override
                protected void onPostExecute(String s) {
                    List<Course> courses = CourseParser.parseJson(s);
                    CoursesAndQuizzes.insertCoursesToDb(courses,getContext());
                    for (Course c : courses) courseQuizzes.add(c.getCourseQuizz());
                    getArguments().putParcelableArrayList(QUIZZES_KEY, (ArrayList) courseQuizzes);
                    Toast.makeText(getActivity().getApplicationContext(), "Download Complete!", Toast.LENGTH_SHORT).show();
                    if (getContext() != null) {
                        CourseQuizzAdapter adapter = new CourseQuizzAdapter(getContext(), R.layout.rl_quizz_item, courseQuizzes, getLayoutInflater());
                        lvQuizzes.setAdapter(adapter);
                        lvQuizzes.setOnItemClickListener(lvQuizzesItemSelected());
                    }
                }
            }.execute(new URL(CoursesAndQuizzes.COURSES_SOURCE));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private AdapterView.OnItemClickListener lvQuizzesItemSelected() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment quizViewer = new QuizViewer();
                getArguments().putParcelable(QUIZZES_KEY, courseQuizzes.get(position));
                quizViewer.setArguments(getArguments());
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.courses_frame_container, quizViewer)
                        .commit();
                Intent intent = getActivity().getIntent().putExtra(QUIZZ_CHOOSEN, courseQuizzes.get(position).getDescription());
                getActivity().setResult(getActivity().RESULT_OK, intent);
            }
        };
    }

}

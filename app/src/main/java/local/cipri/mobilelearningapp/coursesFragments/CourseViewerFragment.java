package local.cipri.mobilelearningapp.coursesFragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import local.cipri.mobilelearningapp.R;
import local.cipri.mobilelearningapp.util.Course;

public class CourseViewerFragment extends Fragment {
    private WebView webView;
    private String HTMLcontent;

    public CourseViewerFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_course_viewer, container, false);
        webView = view.findViewById(R.id.course_viewer_webview);
        HTMLcontent = getArguments().getString(CoursesFragment.HTML_COURSE_KEY);
        webView.loadData(HTMLcontent, "text/html", "UTF-8");
        return view;
    }

}

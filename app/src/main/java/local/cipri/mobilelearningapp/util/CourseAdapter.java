package local.cipri.mobilelearningapp.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import local.cipri.mobilelearningapp.R;

public class CourseAdapter extends ArrayAdapter<Course> {
    private Context context;
    private int resource;
    private List<Course> courses;
    private LayoutInflater layoutInflater;

    public CourseAdapter(Context context, int resource, List<Course> courses, LayoutInflater layoutInflater) {
        super(context, resource, courses);
        this.context = context;
        this.resource = resource;
        this.courses = courses;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(resource, parent, false);
        Course course = courses.get(position);
        if (course != null) {
            populateRow(view, course);
        }
        return view;
    }

    private void populateRow(View view, Course course) {
        populateRowItem((TextView) view.findViewById(R.id.tv_course_title), course.getTitle());
        populateRowItem((TextView) view.findViewById(R.id.tv_course_description), course.getDescription());
        populateRowItem((TextView) view.findViewById(R.id.tv_course_author), course.getAuthor());
        populateRowItem((TextView) view.findViewById(R.id.tv_course_date), course.getDateToString());
        populateRowItem((TextView) view.findViewById(R.id.tv_course_quizes), String.valueOf(course.getCourseQuizz().getQuizzes().length));

    }

    private void populateRowItem(TextView textView, String value) {
        if (value != null)
            textView.setText(value);
        else
            textView.setText("-");
    }
}

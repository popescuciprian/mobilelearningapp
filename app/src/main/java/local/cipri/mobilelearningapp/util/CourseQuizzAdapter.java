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

public class CourseQuizzAdapter extends ArrayAdapter<CourseQuizz> {
    private Context context;
    private int resource;
    private List<CourseQuizz> quizzes;
    private LayoutInflater layoutInflater;

    public CourseQuizzAdapter(@NonNull Context context, int resource, List<CourseQuizz> quizzes, LayoutInflater layoutInflater) {
        super(context, resource, quizzes);
        this.context = context;
        this.resource = resource;
        this.quizzes = quizzes;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(resource, parent, false);
        CourseQuizz courseQuizz = quizzes.get(position);
        if (courseQuizz != null) {
            populateRow(view, courseQuizz);
        }
        return view;
    }

    private void populateRow(View view, CourseQuizz courseQuizz) {
        populateRowItem((TextView) view.findViewById(R.id.tv_quizz_desc), courseQuizz.getDescription());
        populateRowItem((TextView) view.findViewById(R.id.tv_quizz_nr_questions), String.valueOf(courseQuizz.getQuizzes().length));
    }

    private void populateRowItem(TextView textView, String value) {
        if (value != null)
            textView.setText(value);
        else
            textView.setText("-");
    }
}

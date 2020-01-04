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

public class ItemMainAdapter extends ArrayAdapter<Object> {
    private Context context;
    private int resource;
    private List<Object> items;
    private LayoutInflater layoutInflater;

    public ItemMainAdapter(@NonNull Context context, int resource, @NonNull List<Object> items, LayoutInflater layoutInflater) {
        super(context, resource, items);
        this.items = items;
        this.context = context;
        this.resource = resource;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(resource, parent, false);
        Object item = items.get(position);
        if (item != null) {
            populateRow(view, item);
        }
        return view;
    }

    private void populateRow(View view, Object item) {
        if (item instanceof Course) {
            populateRowItem((TextView) view.findViewById(R.id.tv_main_title), ((Course) item).getTitle());
            populateRowItem((TextView) view.findViewById(R.id.tv_main_description), ((Course) item).getDescription());
        } else if (item instanceof Quizz) {
            populateRowItem((TextView) view.findViewById(R.id.tv_main_title), ((Quizz) item).getQuestion());
            populateRowItem((TextView) view.findViewById(R.id.tv_main_description), ((Quizz) item).getChoices()[((Quizz) item).getAnswerIndex()]);
        }
    }

    private void populateRowItem(TextView textView, String value) {
        if (value != null)
            textView.setText(value);
        else
            textView.setText("-");
    }
}

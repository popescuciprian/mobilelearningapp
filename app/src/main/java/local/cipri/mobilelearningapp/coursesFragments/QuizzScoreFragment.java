package local.cipri.mobilelearningapp.coursesFragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import local.cipri.mobilelearningapp.R;


public class QuizzScoreFragment extends Fragment {

    private TextView tvScore;

    public QuizzScoreFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quizz_score, container, false);
        tvScore = view.findViewById(R.id.quizz_score_tv);
        tvScore.setText("Score: "+String.valueOf(getArguments().getInt(QuizViewer.SCORE_KEY)));
        Intent intent = getActivity().getIntent();
        intent.putExtra(QuizViewer.SCORE_KEY, tvScore.getText());
        getActivity().setResult(getActivity().RESULT_OK, intent);

        return view;
    }

}

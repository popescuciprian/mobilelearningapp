package local.cipri.mobilelearningapp.coursesFragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import local.cipri.mobilelearningapp.R;
import local.cipri.mobilelearningapp.util.CourseQuizz;
import local.cipri.mobilelearningapp.util.Quizz;


public class QuizViewer extends Fragment {

    private static final String CURRENT_QUIZZ = "currQuizz";
    public static final String SCORE_KEY = "scoreKey";
    private static int QUIZZ_INDEX = -1;
    private TextView question;
    private RadioGroup radioGroup;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private Button nextBtn;
    private static CourseQuizz courseQuizz;

    public QuizViewer() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz_viewer, container, false);
        courseQuizz = getArguments().getParcelable(QuizzesFragment.QUIZZES_KEY);
        initComponents(view);
        if (getArguments().getParcelable(CURRENT_QUIZZ) != null) {
            Quizz quizz = getArguments().getParcelable(CURRENT_QUIZZ);
            initForm(quizz);

            return view;
        }
        nextBtn.performClick();
        return view;
    }


    private void initComponents(View view) {
        question = view.findViewById(R.id.quizz_question);
        radioGroup = view.findViewById(R.id.quizz_radio_group);
        radioButton1 = view.findViewById(R.id.radioButton1);
        radioButton2 = view.findViewById(R.id.radioButton2);
        radioButton3 = view.findViewById(R.id.radioButton3);
        nextBtn = view.findViewById(R.id.button_next_quizz);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            private int score = 0;

            @Override
            public void onClick(View v) {
                if (QUIZZ_INDEX >= 0)
                    validateAnswer();
                if (QUIZZ_INDEX+1 == courseQuizz.getQuizzes().length) {
                    Fragment quizzScoreFragment = new QuizzScoreFragment();
                    quizzScoreFragment.setArguments(getArguments());
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.courses_frame_container, quizzScoreFragment)
                            .commit();
                    QUIZZ_INDEX = -1;
                } else {
                    Fragment quizViewer = new QuizViewer();
                    if (QUIZZ_INDEX + 1 < courseQuizz.getQuizzes().length)
                        getArguments().putParcelable(CURRENT_QUIZZ, courseQuizz.getQuizzes()[++QUIZZ_INDEX]);
                    quizViewer.setArguments(getArguments());
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.courses_frame_container, quizViewer)
                            .commit();
                }
            }

            private void validateAnswer() {
                if (QUIZZ_INDEX < courseQuizz.getQuizzes().length) {
                    int answerIndex = courseQuizz.getQuizzes()[QUIZZ_INDEX].getAnswerIndex();
                    if (answerIndex == getIndexOfCheckedRadioButton()) {
                        score = getArguments().getInt(SCORE_KEY);
                        score++;
                        getArguments().putInt(SCORE_KEY, score);
                    }
                }
            }

            private int getIndexOfCheckedRadioButton() {
                if (radioButton1.isChecked())
                    return 0;
                if (radioButton2.isChecked())
                    return 1;
                if (radioButton3.isChecked())
                    return 2;
                return -1;
            }

        });
    }

    private void initForm(Quizz quizz) {
        question.setText(quizz.getQuestion());
        radioButton1.setText(quizz.getChoices()[0]);
        radioButton2.setText(quizz.getChoices()[1]);
        radioButton3.setText(quizz.getChoices()[2]);
    }

}

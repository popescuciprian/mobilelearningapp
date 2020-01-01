package local.cipri.mobilelearningapp.util;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

public class Quizz implements Parcelable {
    private String question;
    private String[] choices;
    private int answerIndex;

    public Quizz(String question, String[] choices, int answerIndex) {
        this.question = question;
        this.choices = choices;
        this.answerIndex = answerIndex;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getChoices() {
        return choices;
    }

    public void setChoices(String[] choices) {
        this.choices = choices;
    }

    public int getAnswerIndex() {
        return answerIndex;
    }

    public void setAnswerIndex(int answerIndex) {
        this.answerIndex = answerIndex;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeStringArray(choices);
        dest.writeInt(answerIndex);
    }

    @Override
    public String toString() {
        return "Quizz{" +
                "question='" + question + '\'' +
                ", choices=" + Arrays.toString(choices) +
                ", answerIndex=" + answerIndex +
                '}';
    }

    private Quizz(Parcel in) {
        question = in.readString();
        choices = in.createStringArray();
        answerIndex = in.readInt();
    }

    public static Creator<Quizz> CREATOR = new Creator<Quizz>() {
        @Override
        public Quizz createFromParcel(Parcel source) {
            return new Quizz(source);
        }

        @Override
        public Quizz[] newArray(int size) {
            return new Quizz[size];
        }
    };
}

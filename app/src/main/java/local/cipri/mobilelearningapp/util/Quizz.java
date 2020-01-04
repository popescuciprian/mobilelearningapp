package local.cipri.mobilelearningapp.util;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Arrays;

@Entity(tableName = "quizzes", foreignKeys = @ForeignKey(entity = CourseQuizz.class, parentColumns = "id", childColumns = "quizzId", onDelete = ForeignKey.CASCADE), indices = {@Index("quizzId")})
public class Quizz implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;
    @ColumnInfo(name = "question")
    private String question;
    @ColumnInfo(name = "choices")
    private String[] choices;
    @ColumnInfo(name = "answerIndex")
    private int answerIndex;
    @ColumnInfo(name = "quizzId")
    private long quizzId;

    public Quizz(long id, String question, String[] choices, int answerIndex, long quizzId) {
        this.id = id;
        this.question = question;
        this.choices = choices;
        this.answerIndex = answerIndex;
        this.quizzId = quizzId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getQuizzId() {
        return quizzId;
    }

    public void setQuizzId(long quizzId) {
        this.quizzId = quizzId;
    }

    @Ignore
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

package local.cipri.mobilelearningapp.util;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

public class CourseQuizz implements Parcelable {
    private Quizz[] quizzes;
    private String description;

    public CourseQuizz(Quizz[] quizzes, String description) {
        this.quizzes = quizzes;
        this.description = description;
    }

    public Quizz[] getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(Quizz[] quizzes) {
        this.quizzes = quizzes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedArray(quizzes,0);
        dest.writeString(description);
    }

    @Override
    public String toString() {
        return "CourseQuizz{" +
                ", description='" + description + '\'' +
                "quizzes=" + Arrays.toString(quizzes) +
                '}';
    }

    private CourseQuizz(Parcel in) {
        quizzes = in.createTypedArray(Quizz.CREATOR);
        description = in.readString();
    }

    public static Creator<CourseQuizz> CREATOR = new Creator<CourseQuizz>() {
        @Override
        public CourseQuizz createFromParcel(Parcel source) {
            return new CourseQuizz(source);
        }

        @Override
        public CourseQuizz[] newArray(int size) {
            return new CourseQuizz[size];
        }
    };
}

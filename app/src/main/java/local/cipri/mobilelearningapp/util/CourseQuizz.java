package local.cipri.mobilelearningapp.util;

import android.os.Parcel;
import android.os.Parcelable;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Arrays;

@Entity
public class CourseQuizz implements Parcelable {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private long id;
    private Quizz[] quizzes;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "courseId")
    private long courseId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CourseQuizz(long id, Quizz[] quizzes, String description, long courseId) {
        this.id = id;
        this.quizzes = quizzes;
        this.description = description;
        this.courseId = courseId;
    }

    @Ignore
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
        dest.writeTypedArray(quizzes, 0);
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

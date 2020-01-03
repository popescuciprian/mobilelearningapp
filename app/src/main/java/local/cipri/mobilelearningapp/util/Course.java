package local.cipri.mobilelearningapp.util;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "courses")
public class Course implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "author")
    private String author;
    @ColumnInfo(name = "date")
    private Date date;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "htmlContent")
    private String htmlContent;
    @Ignore
    private CourseQuizz courseQuizz;

    public Course(long id, String title, String author, Date date, String description, String htmlContent) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.date = date;
        this.description = description;
        this.htmlContent = htmlContent;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Ignore
    public Course(String title, String author, Date date, String description, String htmlContent, CourseQuizz courseQuizz) {
        this.title = title;
        this.author = author;
        this.date = date;
        this.description = description;
        this.htmlContent = htmlContent;
        this.courseQuizz = courseQuizz;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    public String getDateToString() {
        return new SimpleDateFormat("dd-MM-yyyy", Locale.US).format(date);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public CourseQuizz getCourseQuizz() {
        return courseQuizz;
    }

    public void setCourseQuizz(CourseQuizz courseQuizz) {
        this.courseQuizz = courseQuizz;
    }

    @Override
    public String toString() {
        String courseQuizzesStr = "null";
        if (courseQuizz != null) courseQuizzesStr = courseQuizz.toString();
        String result = "Course{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", date=" + getDateToString() +
                ", description='" + description + '\'' +
                ", htmlContent='" + htmlContent + '\'' +
                ", courseQuizz=" + courseQuizzesStr +
                '}';
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(author);
        String strDate = date != null ?
                getDateToString()
                : "null";//intended. I want a string called "null".
        dest.writeString(strDate);
        dest.writeString(description);
        dest.writeString(htmlContent);
        dest.writeParcelable(courseQuizz, flags);
    }

    private Course(Parcel in) {
        title = in.readString();
        author = in.readString();
        try {
            date = new SimpleDateFormat("dd-MM-yyyy", Locale.US).parse(in.readString());
        } catch (ParseException e) {
            e.printStackTrace();
            date = null;
        }
        description = in.readString();
        htmlContent = in.readString();
        courseQuizz = in.readParcelable(CourseQuizz.class.getClassLoader());

    }

    public static Creator<Course> CREATOR = new Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel source) {
            return new Course(source);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[0];
        }
    };

}



package local.cipri.mobilelearningapp.network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import local.cipri.mobilelearningapp.util.Course;
import local.cipri.mobilelearningapp.util.CourseQuizz;
import local.cipri.mobilelearningapp.util.Quizz;

public class CourseParser {
    public static List<Course> parseJson(String json) {
        List<Course> courses = new ArrayList<>();
        if (json == null) return null;

        try {
            JSONArray jsonCourses = new JSONObject(json).getJSONArray("courses");
            for (int i = 0; i < jsonCourses.length(); i++) {

                JSONObject jsonCourse = jsonCourses.getJSONObject(i);
                courses.add(parseJsonCourse(jsonCourse));
            }
            return courses;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Course parseJsonCourse(JSONObject jsonCourse) throws JSONException {
        JSONObject about = jsonCourse.getJSONObject("about");
        String title = about.getString("title");
        String author = about.getString("author");
        Date date = parseDateFromString(about.getString("date"));

        String description = jsonCourse.getString("description");
        String content = jsonCourse.getString("content");

        JSONArray jsonQuizzes = jsonCourse.getJSONArray("quizz");
        CourseQuizz courseQuizz = parseCourseQuizz(jsonQuizzes, description);

        return new Course(title, author, date, description, content, courseQuizz);
    }

    private static CourseQuizz parseCourseQuizz(JSONArray jsonCourseQuizz, String description) throws JSONException {
        Quizz[] quizzes = new Quizz[jsonCourseQuizz.length()];
        for (int i = 0; i < jsonCourseQuizz.length(); i++) {
            JSONObject jsonQuizz = jsonCourseQuizz.getJSONObject(i);
            quizzes[i] = parseQuizz(jsonQuizz);
        }
        return new CourseQuizz(quizzes, description);
    }

    private static Quizz parseQuizz(JSONObject jsonQuizz) throws JSONException {
        String question = jsonQuizz.getString("question");
        JSONArray jsonChoices = jsonQuizz.getJSONArray("choices");
        String[] choices = new String[jsonChoices.length()];
        for (int j = 0; j < jsonChoices.length(); j++) {
            choices[j] = jsonChoices.getString(j);
        }
        int answerIndex = jsonQuizz.getInt("answerIndex");
        return new Quizz(question, choices, answerIndex);
    }

    private static Date parseDateFromString(String strDate) {
        try {
            return new SimpleDateFormat("dd-MM-yyyy", Locale.US).parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }
}

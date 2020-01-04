package local.cipri.mobilelearningapp.database.service;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import local.cipri.mobilelearningapp.database.DatabaseManager;
import local.cipri.mobilelearningapp.database.dao.CourseDao;
import local.cipri.mobilelearningapp.util.Course;

public class CourseService {
    protected static CourseDao courseDao;

    public static class getCourses extends AsyncTask<Void, Void, List<Course>> {

        @Override
        protected List<Course> doInBackground(Void... voids) {
            return courseDao.selectAll();
        }

        public getCourses(Context context) {
            courseDao = DatabaseManager.getInstance(context).getCourseDao();
        }
    }

    public static class insertCourse extends AsyncTask<Course, Void, Course> {

        @Override
        protected Course doInBackground(Course... courses) {
            if (courses == null || courses.length != 1)
                return null;
            Course course = courses[0];
            long id = courseDao.insert(course);
            if (id != -1) {
                course.setId(id);
                course.getCourseQuizz().setCourseId(id);
                return course;
            }
            return null;
        }

        public insertCourse(Context context) {
            courseDao = DatabaseManager.getInstance(context).getCourseDao();
        }
    }

    public static class updateCourse extends AsyncTask<Course, Void, Integer> {
        @Override
        protected Integer doInBackground(Course... courses) {
            return (courses == null || courses.length != 1) ? -1 : courseDao.update(courses[0]);
        }

        public updateCourse(Context context) {
            courseDao = DatabaseManager.getInstance(context).getCourseDao();
        }
    }

    public static class deleteCourse extends AsyncTask<Course, Void, Integer> {
        @Override
        protected Integer doInBackground(Course... courses) {
            return (courses == null || courses.length != 1) ? -1 : courseDao.delete(courses[0]);
        }

        public deleteCourse(Context context) {
            courseDao = DatabaseManager.getInstance(context).getCourseDao();
        }
    }
}

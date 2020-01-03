package local.cipri.mobilelearningapp.database.service;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import local.cipri.mobilelearningapp.database.DatabaseManager;
import local.cipri.mobilelearningapp.database.dao.CourseQuizzDao;
import local.cipri.mobilelearningapp.util.CourseQuizz;

public class CourseQuizzService {
    private static CourseQuizzDao courseQuizzDao;

    public static class getCourseQuizzes extends AsyncTask<Void, Void, List<CourseQuizz>> {
        @Override
        protected List<CourseQuizz> doInBackground(Void... voids) {
            return courseQuizzDao.selectAll();
        }


        public getCourseQuizzes(Context context) {
            courseQuizzDao = DatabaseManager.getInstance(context).getCourseQuizzDao();
        }
    }

    public static class getCourseQuizzesForCourse extends AsyncTask<Void, Void, List<CourseQuizz>> {
        private long courseId;

        @Override
        protected List<CourseQuizz> doInBackground(Void... voids) {
            return courseQuizzDao.selectAllQuizzesForCourse(courseId);
        }

        public getCourseQuizzesForCourse(Context context, long courseId) {
            courseQuizzDao = DatabaseManager.getInstance(context).getCourseQuizzDao();
            this.courseId = courseId;
        }
    }

    public static class insertCourseQuizz extends AsyncTask<CourseQuizz, Void, CourseQuizz> {
        @Override
        protected CourseQuizz doInBackground(CourseQuizz... courseQuizzes) {
            if (courseQuizzes == null || courseQuizzes.length != 1)
                return null;
            CourseQuizz courseQuizz = courseQuizzes[0];
            long id = courseQuizzDao.insert(courseQuizz);
            if (id != -1) {
                courseQuizz.setId(id);
                return courseQuizz;
            }
            return null;
        }

        public insertCourseQuizz(Context context) {
            courseQuizzDao = DatabaseManager.getInstance(context).getCourseQuizzDao();
        }
    }

    public static class updateCourseQuizz extends AsyncTask<CourseQuizz, Void, Integer> {
        @Override
        protected Integer doInBackground(CourseQuizz... courseQuizzes) {
            return (courseQuizzes == null || courseQuizzes.length != 1) ? -1 : courseQuizzDao.update(courseQuizzes[0]);
        }

        public updateCourseQuizz(Context context) {
            courseQuizzDao = DatabaseManager.getInstance(context).getCourseQuizzDao();
        }
    }

    public static class deleteCourseQuizz extends AsyncTask<CourseQuizz, Void, Integer> {
        @Override
        protected Integer doInBackground(CourseQuizz... courseQuizzes) {
            return (courseQuizzes == null || courseQuizzes.length != 1) ? -1 : courseQuizzDao.delete(courseQuizzes[0]);
        }

        public deleteCourseQuizz(Context context) {
            courseQuizzDao = DatabaseManager.getInstance(context).getCourseQuizzDao();
        }
    }
}

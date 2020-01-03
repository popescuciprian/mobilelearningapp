package local.cipri.mobilelearningapp.database.service;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import local.cipri.mobilelearningapp.database.DatabaseManager;
import local.cipri.mobilelearningapp.database.dao.QuizzDao;
import local.cipri.mobilelearningapp.util.Quizz;

public class QuizzService {
    private static QuizzDao quizzDao;

    public static class getCourseQuizzes extends AsyncTask<Void, Void, List<Quizz>> {
        @Override
        protected List<Quizz> doInBackground(Void... voids) {
            return quizzDao.selectAll();
        }

        public getCourseQuizzes(Context context) {
            quizzDao = DatabaseManager.getInstance(context).getQuizzDao();
        }
    }

    public static class getChoicesForCourse extends AsyncTask<Void, Void, List<Quizz>> {
        private long quizzId;

        @Override
        protected List<Quizz> doInBackground(Void... voids) {
            return quizzDao.selectChoicesForQuizzId(quizzId);
        }

        public getChoicesForCourse(Context context, long quizzId) {
            quizzDao = DatabaseManager.getInstance(context).getQuizzDao();
            this.quizzId = quizzId;
        }
    }


    public static class insertQuizz extends AsyncTask<Quizz, Void, Quizz> {
        @Override
        protected Quizz doInBackground(Quizz... quizzes) {
            if (quizzes == null || quizzes.length != 1)
                return null;
            Quizz quizz = quizzes[0];
            long id = quizzDao.insert(quizz);
            if (id != -1) {
                quizz.setId(id);
                return quizz;
            }
            return null;
        }

        public insertQuizz(Context context) {
            quizzDao = DatabaseManager.getInstance(context).getQuizzDao();
        }
    }

    public static class updateQuizz extends AsyncTask<Quizz, Void, Integer> {
        @Override
        protected Integer doInBackground(Quizz... quizzes) {
            return (quizzes == null || quizzes.length != 1) ? -1 : quizzDao.update(quizzes[0]);
        }

        public updateQuizz(Context context) {
            quizzDao = DatabaseManager.getInstance(context).getQuizzDao();
        }
    }

    public static class deleteQuizz extends AsyncTask<Quizz, Void, Integer> {
        @Override
        protected Integer doInBackground(Quizz... quizzes) {
            return (quizzes == null || quizzes.length != 1) ? -1 : quizzDao.delete(quizzes[0]);
        }

        public deleteQuizz(Context context) {
            quizzDao = DatabaseManager.getInstance(context).getQuizzDao();
        }
    }
}

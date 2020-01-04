package local.cipri.mobilelearningapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import local.cipri.mobilelearningapp.database.dao.CourseDao;
import local.cipri.mobilelearningapp.database.dao.CourseQuizzDao;
import local.cipri.mobilelearningapp.database.dao.QuizzDao;
import local.cipri.mobilelearningapp.util.Course;
import local.cipri.mobilelearningapp.util.CourseQuizz;
import local.cipri.mobilelearningapp.util.Quizz;

@Database(entities = {Course.class, CourseQuizz.class, Quizz.class}, exportSchema = false, version = 2)
@TypeConverters({Converters.class})
public abstract class DatabaseManager extends RoomDatabase {
    private static final String DB_NAME = "mla_db";//MobileLearningApp
    private static DatabaseManager databaseManager;

    public static DatabaseManager getInstance(
            Context context) {
        if (databaseManager == null) {
            synchronized (DatabaseManager.class) {
                if (databaseManager == null) {
                    databaseManager = Room.
                            databaseBuilder(context,
                                    DatabaseManager.class,
                                    DB_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                    return databaseManager;
                }
            }
        }
        return databaseManager;
    }

    public abstract CourseDao getCourseDao();

    public abstract CourseQuizzDao getCourseQuizzDao();

    public abstract QuizzDao getQuizzDao();
}

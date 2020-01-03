package local.cipri.mobilelearningapp.database;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import local.cipri.mobilelearningapp.util.CourseQuizz;

@Dao
public interface CourseQuizzDao {
    @Query("select * from coursequizzes")
    List<CourseQuizz> selectAll();

    @Query("select * from coursequizzes where courseId=:courseId")
    List<CourseQuizz> selectAllQuizzesForCourse(final long courseId);
}

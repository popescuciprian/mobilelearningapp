package local.cipri.mobilelearningapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import local.cipri.mobilelearningapp.util.CourseQuizz;

@Dao
public interface CourseQuizzDao {
    @Query("select * from coursequizzes")
    List<CourseQuizz> selectAll();

    @Query("select * from coursequizzes where courseId=:courseId")
    List<CourseQuizz> selectAllQuizzesForCourse(final long courseId);

    @Insert
    long insert(CourseQuizz courseQuizz);

    @Update
    int update(CourseQuizz courseQuizz);

    @Delete
    int delete(CourseQuizz courseQuizz);
}

package local.cipri.mobilelearningapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import local.cipri.mobilelearningapp.util.Course;

@Dao
public interface CourseDao {
    @Query("select * from courses")
    List<Course> selectAll();
    @Insert
    long insert(Course course);
    @Update
    int update(Course course);
    @Delete
    int delete(Course course);

}

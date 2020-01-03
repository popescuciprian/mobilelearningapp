package local.cipri.mobilelearningapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import local.cipri.mobilelearningapp.util.Quizz;

@Dao
public interface QuizzDao {
    @Query("select * from quizzes")
    List<Quizz> selectAll();

    @Query("select * from quizzes where quizzId=:quizzId")
    List<Quizz> selectChoicesForQuizzId(final long quizzId);


    @Insert
    long insert(Quizz quizz);

    @Update
    int update(Quizz quizz);

    @Delete
    int delete(Quizz quizz);
}

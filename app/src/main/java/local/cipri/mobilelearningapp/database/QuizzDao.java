package local.cipri.mobilelearningapp.database;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import local.cipri.mobilelearningapp.util.Quizz;

@Dao
public interface QuizzDao {
    @Query("select * from quizzes")
    List<Quizz> selectAll();

    @Query("select * from quizzes where quizzId=:quizzId")
    List<Quizz> selectQuizzesForQuizzId(final long quizzId);
}

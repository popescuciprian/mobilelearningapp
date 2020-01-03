package local.cipri.mobilelearningapp.database;

import androidx.room.TypeConverter;

import java.util.Arrays;
import java.util.Date;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long timestamp) {
        return timestamp != null ?
                new Date(timestamp)
                : null;
    }

    @TypeConverter
    public static Long fromDate(Date date) {
        return date != null ? date.getTime()
                : null;
    }

    @TypeConverter
    public static String[] fromDbToQuizz(String choices) {
        return choices != null ?
                choices.replaceAll("[^a-zA-Z^,\\s]", "").split(", ") : null;
    }

    @TypeConverter
    public static String fromQuizToDb(String[] choices) {
        return choices != null ? Arrays.toString(choices) : null;
    }

}

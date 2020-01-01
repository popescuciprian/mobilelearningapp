package local.cipri.mobilelearningapp.network;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadCoursesTask extends AsyncTask<URL, Integer, String> {

    private HttpURLConnection connection;
    private InputStream inputStream;
    private InputStreamReader inputStreamReader;
    private BufferedReader bufferedReader;

    protected String doInBackground(URL... urls) {
        StringBuilder result = new StringBuilder();
        try {
            connection = (HttpURLConnection) urls[0].openConnection();
            inputStream = connection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while((line = bufferedReader.readLine()) != null)result.append(line);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            connection.disconnect();
        }
        return result.toString();
    }


}

package pt.marciopinto.projecto_final;

import android.os.AsyncTask;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Scanner;


public class MyInternetTask extends AsyncTask <Void, Void, HashMap> {

    public interface ConnectionListener {
        void onConnectionSuccess(String contentType, String result);
        void onConnectionError();
    }

    private String url;
    private ConnectionListener connectionListener;

    public MyInternetTask(String url) {
        this.url = url;
    }

    public void setConnectionListener(ConnectionListener connectionListener) {
        this.connectionListener = connectionListener;
    }

    @Override
    protected HashMap doInBackground(Void... params) {

        HashMap<String, String> result = null;

        try {
            URL url = new URL(this.url);

            URLConnection connection = url.openConnection();

            String contentType = connection.getContentType();

            InputStream is = connection.getInputStream();
            Scanner scanner = new Scanner(is);

            String content = "";
            while (scanner.hasNextLine()) {
                content += scanner.nextLine();
            }

            result = new HashMap<>();

            result.put("contentType", contentType);
            result.put("content", content);

        } catch (Exception e) {

            e.printStackTrace();

        }

        return result;
    }

    @Override
    protected void onPostExecute(HashMap r) {
        super.onPostExecute(r);

        if (connectionListener != null) {

            if (r != null) {
                connectionListener.onConnectionSuccess((String)r.get("contentType"), (String)r.get("content"));
            } else {
                connectionListener.onConnectionError();
            }

        }

    }
}

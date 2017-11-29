package com.example.staffonechristian.seeksubstitute.Notification;

import android.os.StrictMode;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

/**
 * Created by staffonechristian on 2017-11-29.
 */

public class NotificationHandling {

    public void SendNotification(String notificationText){
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        JSONObject json = new JSONObject();
        JSONObject info = new JSONObject();
        JSONObject payLoad = new JSONObject();
        Random random = new Random();
        int randomInt = random.nextInt(10000);

        try {
            info.put("title", "SeekSubstitute");   // Notification title
            info.put("body", notificationText); // Notification body
            info.put("sound", "default"); // Notification sound

            payLoad.put("id", randomInt);

            json.put("notification", info);

            json.put("data", payLoad);

            json.put("to", "/topics/common" );
            SendFinal(json,info);
        }catch (Exception ex)
        {


        }

    }

    private void SendFinal(JSONObject json, JSONObject info) {
        try {
            //Connect
            String data = json.toString();
            HttpURLConnection urlConnection;
            urlConnection = (HttpURLConnection) ((new URL("https://fcm.googleapis.com/fcm/send").openConnection()));
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Authorization", "key=AIzaSyDV25QJPMa_iCP-0NEM3OJZrRIgb_I60kg");
            urlConnection.setRequestMethod("POST");
            urlConnection.connect();

            //Write
            OutputStream outputStream = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(data);
            writer.close();
            outputStream.close();

            //Read
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            String line = null;
            StringBuilder sb = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            bufferedReader.close();


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

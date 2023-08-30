package com.isayso.fire2kodi.ui.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendKodi {

    public static Context context;

    public static String kodiIP = read("KodiIP","192.168.");

    public static String kodiPort =read("Kodiport","8080");

    public static  String kodiuser = read("Kodiuser","kodi");

    public  static String kodipwd = read("KodiPwd","");


    public static void sendJson(String jLink)
    {
        jLink = "{ \"jsonrpc\":\"2.0\",\"method\":\"Player.Open\",\"params\":{ \"item\":{ \"file\":\"" + jLink + "\"} },\"id\":0}";

        HttpURLConnection connection;
        OutputStreamWriter request;

        URL url = null;
        String response;
        String parameters = "kodiuser="+kodiuser+"&password="+kodipwd;

      /*  String authorizationString = "Basic " + Base64.encodeToString(
                (kodiuser + ":" + kodipwd).getBytes(),
                Base64.NO_WRAP); //Base64.NO_WRAP flag
        connection.setHeader("Authorization", authorizationString);
*/
        String basicAuth = getB64Auth(kodiuser,kodipwd);


        try
        {
            url = new URL("http://" + kodiIP + ":" + kodiPort +  "/jsonrpc?request=");
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty ("Authorization", basicAuth);
            connection.setRequestMethod("POST");


            request = new OutputStreamWriter(connection.getOutputStream());
            request.write(parameters);
            request.flush();
            request.close();
            String line = "";
            InputStreamReader isr = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null)
            {
                sb.append(line).append("\n");
            }
            // Response from server after login process will be stored in response variable.
            response = sb.toString();
            // You can perform UI operations here
            Toast.makeText(context,"Message from Server: \n"+ response, (int) 10).show();
            isr.close();
            reader.close();

        }
        catch(IOException e)
        {
            // Error
        }
    }

    private static String getB64Auth (String login, String pass) {
        String source=login+":"+pass;
        return "Basic "+Base64.encodeToString(source.getBytes(),Base64.URL_SAFE|Base64.NO_WRAP);
    }


    //read preferences string
    private  static String read(String valueKey, String valueDefault) {

        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        return prefs.getString(valueKey, valueDefault);
    }

}

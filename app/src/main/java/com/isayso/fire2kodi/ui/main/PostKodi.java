package com.isayso.fire2kodi.ui.main;


import android.content.Context;
import android.util.Base64;

import com.isayso.fire2kodi.GlobalApplication;

import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class PostKodi {

    public static Context context = GlobalApplication.getAppContext();
    public static String result = null;


    //https://varunbarad.com/blog/http-network-requests-on-android-pie

    public static String makeRequest(String jLink, boolean queue, String url) {
        HttpURLConnection urlConnection;
        //  String url = "http://" + KodiDevice1.IP + ":" + KodiDevice1.Port +  "/jsonrpc?request=";
        String data;

        if (!queue)
            data = "{ \"jsonrpc\":\"2.0\",\"method\":\"Player.Open\",\"params\":{ \"item\":{ \"file\":\"" + jLink + "\"} },\"id\":0}";

        else
            data = "{ \"id\":0,\"jsonrpc\":\"2.0\",\"method\":\"Playlist.Add\",\"params\": {\"item\":{\"file\":\"" + jLink + "\"},\"playlistid\":1}}";  //OK


        //       "{ \"jsonrpc\":\"2.0\",\"method\":\"Player.Open\",\"params\":{ \"item\":{ \"file\":\"" + jLink + "\"} },\"id\":0}";

        String result = null;

        String basicAuth = getB64Auth(KodiDevice1.User, KodiDevice1.Pwd);

        try {
            //Connect  allow http in settings!!!
            urlConnection = (HttpURLConnection) ((new URL(url).openConnection()));
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("Authorization", basicAuth);
            urlConnection.setRequestMethod("POST");
            urlConnection.setConnectTimeout(5000);
            urlConnection.connect();


            //Write to Kodi
            String jsonInputString = data;

            try (OutputStream os = urlConnection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }


            //Read from Kodi
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                // System.out.println(response.toString());
                result = response.toString();


            }

  /*          //Write
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
            result = sb.toString();
*/
            //   Toast.makeText(context,"Message from Server: \n"+ result, (int) 10).show();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static boolean getStatus(String url) {
        HttpURLConnection urlConnection;
        //  String url = "http://" + KodiDevice1.IP + ":" + KodiDevice1.Port +  "/jsonrpc?request=";
        String data =
                "{ \"jsonrpc\": \"2.0\", \"method\": \"Player.GetActivePlayers\", \"id\": 0}";


        String result;
        boolean ActiveQueue = false;

        String basicAuth = getB64Auth(KodiDevice1.User, KodiDevice1.Pwd);

        try {
            //Connect  allow http in settings!!!
            urlConnection = (HttpURLConnection) ((new URL(url).openConnection()));
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("Authorization", basicAuth);
            urlConnection.setRequestMethod("POST");
            urlConnection.setConnectTimeout(5000);
            urlConnection.connect();


            //Write to Kodi
            String jsonInputString = data;

            try (OutputStream os = urlConnection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }


            //Read from Kodi
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                // System.out.println(response.toString());
                result = response.toString();

                if (result.contains("1") && result.contains("video")) {
                    result = "OK";
                    ActiveQueue = true;
                } else if (result.contains("0")) {
                    //"{\"id\":0,\"jsonrpc\":\"2.0\",\"result\":[]}"
                    result = "OK";
                    ActiveQueue = false;
                }


            }

  /*          //Write
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
            result = sb.toString();
*/
            //   Toast.makeText(context,"Message from Server: \n"+ result, (int) 10).show();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ActiveQueue;
    }

    public static String StartKodi(String pluginString, String deviceUrl, boolean queue) {

        final boolean[] activePlayer = {false};

        if (queue) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    activePlayer[0] = PostKodi.getStatus(deviceUrl);
                }
            });

            t.start(); // spawn thread

            try {
                t.join(); //wait for thread finish
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        //get ActivePlayer

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //Post2Kodi.execute();
                result = PostKodi.makeRequest(pluginString, activePlayer[0], deviceUrl);
            }
        });

        t.start(); // spawn thread

        try {
            t.join(); //wait for thread finish
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String getB64Auth(String login, String pass) {
        String source = login + ":" + pass;
        return "Basic " + Base64.encodeToString(source.getBytes(), Base64.URL_SAFE | Base64.NO_WRAP);
    }


}

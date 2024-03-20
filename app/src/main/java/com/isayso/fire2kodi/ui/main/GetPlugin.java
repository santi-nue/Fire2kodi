package com.isayso.fire2kodi.ui.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.isayso.fire2kodi.GlobalApplication;
import com.isayso.fire2kodi.R;


import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GetPlugin {

   // @SuppressLint("StaticFieldLeak")
    public static Context context = GlobalApplication.getAppContext();
    public static String PLUG = "plugin://", kodiPluginLink ="";

    public static String YTPLUGIN = PLUG + read("YTaddon",context.getString(R.string.youtube)),
    VIPLUGIN = PLUG + read("VIMaddon",context.getString(R.string.vimeo)),
    ODYPLUGIN = PLUG + read("ODYaddon",context.getString(R.string.odysee)),
    RBLPLUGIN = PLUG + read("RMBaddon",context.getString(R.string.rumble)),
    DMPLUGIN1 = PLUG + read("DAYaddon",context.getString(R.string.dailymotion)),
    BCPLUGIN = PLUG + read("BITaddon",context.getString(R.string.bitchute)),
    DMPLUGIN2 = "&mode=playVideo"; //;mode=playVideo&quot"; //plugin.video.dailymotion_com/?url=



    public static String ImportYTLink(String yt_Link) //Youtube
    {

        String videokey = ScrapHtml(yt_Link, "v=(.*?)(&|$)");

        if (videokey.isEmpty())
        {
            videokey = ScrapHtml(yt_Link, "embed\\/(.*?)(\\?|$)");
        }
        if (videokey.isEmpty())
        {
            videokey = ScrapHtml(yt_Link, "shorts\\/(.*?)(\\?|$)");
        }
        if (videokey.isEmpty())
        {
            videokey = ScrapHtml(yt_Link, ".be\\/(.*?)(\\?|$)");
        }
        if (!videokey.isEmpty())
        {
            kodiPluginLink = YTPLUGIN + videokey;
        }
        return kodiPluginLink;

/*
        String url = "";

        if (yt_Link.contains("youtube.com") || yt_Link.contains("www.youtube-nocookie.com") || yt_Link.contains("youtu.be"))
        {
            if ((yt_Link.contains("embed") || yt_Link.contains("youtu.be/")) && !yt_Link.contains("=youtu.be/"))  //variant embed link
            {          //https://youtu.be/rqAqkjE0IbY?t=136
                String[] key_em = yt_Link.split("\\?");
                String[] key_em2 = key_em[0].split("/");
                String[] key2;

                int keylast = key_em2.length -1;
                if (key_em2[keylast].contains("&"))
                    key_em2[keylast] = key_em2[keylast].split("&", 1).toString(); //.First();
                key2 = key_em2[keylast].split("&");

               // ytPluginLink = YTPLUGIN + key_em2[keylast];  //last()
                ytPluginLink = YTPLUGIN + key2[0];  //last()
            }
            //https://www.youtube.com/watch?time_continue=16&v=UaTYYk3HxOc&feature=emb_logo
            else if (yt_Link.contains("time_continue"))
            {
                String[] key = yt_Link.split("=");  //variant normal or YT playlist link
                String[] key2 = null;

                if (key.length > 1)     //if channel has no '='
                {
                    if (key[2].contains("&")){
                        key2 = key[2].split("&"); //.First();
                      //  key[2] = key[2].split("&", 1).toString(); //.First();

                    //  ytPluginLink = YTPLUGIN + key[1];
                        ytPluginLink = YTPLUGIN + key2[0];
                    }
                    else ytPluginLink = YTPLUGIN + key[1];

                }
            }

            else if (yt_Link.contains("music.youtube"))
            {
                String[] key = yt_Link.split("=");  //variant normal or YT playlist link
                String[] key2;
                if (key.length > 1)     //if channel has no '='
                {
                    if (key[1].contains("&")){
                         key2 = key[1].split("&"); //.First();
                      //  key[1] = key2[0].split("&", 1).toString(); //.First();

                        ytPluginLink = YTPLUGIN + key2[0];
                    }
                    else ytPluginLink = YTPLUGIN + key[1];


                }
            }

            else
            {
                String[] key = yt_Link.split("=");  //variant normal or YT playlist link
                String[] key2;

                if (key.length > 1)     //if channel has no '='
                {
                    if (key[1].contains("&")){
                        key2 = key[1].split("&"); //.First();

//                    key[1] = key[1].split("&", 1).toString(); // .First();

                        ytPluginLink = YTPLUGIN + key2[0];
                    }
                    else ytPluginLink = YTPLUGIN + key[1];
                }
            }


        }
        return ytPluginLink;
*/
    }

    public static String GetPartString(String fullstr, String startstr, String endstr)
    {
        int start, end;
        if (fullstr.contains(startstr) && fullstr.contains(endstr))
        {
            start = fullstr.indexOf(startstr) + startstr.length();
            end = fullstr.indexOf(endstr, start);
            return fullstr.substring(start, end - start);
        }
        else
        {
            return "";
        }
    }


    public static String ImportDailyLink(String yt_Link)  //Dailymotion
    {

        String[] key_em = yt_Link.split("/");
        kodiPluginLink = DMPLUGIN1 + key_em[key_em.length - 1] + DMPLUGIN2;
        return kodiPluginLink;

    }


    public static String ImportVimeoLink(String yt_Link)  //Vimeo
    {

        String[] key_em = yt_Link.split("/");
        kodiPluginLink = VIPLUGIN + key_em[key_em.length - 1];
        return kodiPluginLink;

    }

    public static String ImportOdyseeLink(String yt_Link) //Odysee
    {
        String[] key_em = yt_Link.split("/");

        if (yt_Link.contains("embed"))
        {
            kodiPluginLink = ODYPLUGIN + key_em[5];
        }
        else
            kodiPluginLink = ODYPLUGIN + key_em[key_em.length - 1] ;

        return kodiPluginLink;

    }

    public static String ImportRumbleLink(String yt_Link)  //Rumble
    {
        String[] key_em = yt_Link.split("/");
        kodiPluginLink = RBLPLUGIN + key_em[key_em.length - 1] + "&mode=4&play=2";
        return kodiPluginLink;

    }

    public static String ImportBCLink(String url) //bitchute
    {
        url = url.replace("https://", "");
        String[] key = url.split("/");
        kodiPluginLink = BCPLUGIN + key[2];
        return kodiPluginLink;

    }

    //read preferences String

    private static String read(String valueKey, String valueDefault) {

        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);  //todo exception
        return prefs.getString(valueKey, valueDefault);
    }

    public static String ScrapHtml(String source, String regstring){

        String ytkey = "";
        Pattern p = Pattern.compile(regstring);
        Matcher t = p.matcher(source);

        while (t.find()) { // Find each match in turn; String can't do this.
             ytkey = t.group(1); // Access a submatch group; String can't do this.
        }
        return ytkey;

    }

}

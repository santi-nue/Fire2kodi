package com.isayso.fire2kodi.ui.main;

public class QueueKodi {

    static String deviceUrl = "", result ="";


    public static String onIntentQueueKodi(String yt_Link, ValidVideoType videoType, boolean queue) {

        if (yt_Link.isEmpty() || yt_Link == null ) return "Error";

          //  final boolean[] ActivePlayer = {false};

           String pluginString = GetLinktype.GetPluginString(videoType,yt_Link);

            if (Preferences.readBool("ChkD1", true)) {
                deviceUrl = "http://" + KodiDevice1.IP + ":" + KodiDevice1.Port +  "/jsonrpc?request=";
            }
            else if (Preferences.readBool("ChkD2", false)){
                deviceUrl = "http://" + KodiDevice2.IP + ":" + KodiDevice2.Port +  "/jsonrpc?request=";
            }

            return PostKodi.StartKodi(pluginString, deviceUrl, queue);


    }

}

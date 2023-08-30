package com.isayso.fire2kodi.ui.main;

public class GetLinktype {

    //public enum ValidVideoType { Invalid, YT, YList, YMusic, BitC, Html, Rmbl, Vim, Daily, Lbry }


    public static ValidVideoType ValidLinkCheck(String i_Link)
    {

        if (i_Link.contains("youtube.com")
                || i_Link.contains("www.youtube-nocookie.com")
                || i_Link.contains("youtu.be")
                || i_Link.contains("music.youtube"))
        {
            if (i_Link.contains("music.youtube"))
                return ValidVideoType.YMusic;

            if (i_Link.contains("list=") || i_Link.contains("channel"))
                return ValidVideoType.YList;
            else return ValidVideoType.YT;
        }
        else if (i_Link.contains(".bitchute.com/video") || i_Link.contains(".bitchute.com/embed"))
        {
            return ValidVideoType.BitC;
        }
        else if (i_Link.contains(".dailymotion.com/video"))
        {
            return ValidVideoType.Daily;
        }
        else if (i_Link.contains("rumble.com") /*&& !i_Link.Contains("embed")*/)  //for rumble
        {
            return ValidVideoType.Rmbl;
        }
        else if (i_Link.contains("vimeo.com"))  //for vimeo
        {
            return ValidVideoType.Vim;
        }
        else if (i_Link.contains("lbry.tv") || i_Link.contains("odysee.com"))
        {
            return ValidVideoType.Lbry;
        }

        else
        {
            return ValidVideoType.Invalid;
        }
    }

public static String GetPluginString(ValidVideoType videoType, String vlink){

        String pluginString ="";

    switch (videoType)  //todo clean links
    {
        case YT:
        case YList:
        case YMusic:
            pluginString = GetPlugin.ImportYTLink(vlink);
            break;

        case Vim:
            pluginString = GetPlugin.ImportVimeoLink(vlink);
            break;

        case Rmbl:
            pluginString = GetPlugin.ImportRumbleLink(vlink);
            break;

        case Lbry:
            pluginString = GetPlugin.ImportLbryLink(vlink);
            break;

        case Daily:
            pluginString = GetPlugin.ImportDailyLink(vlink);
            break;

        case BitC:
            pluginString = GetPlugin.ImportBCLink(vlink);
            break;

    }



    return pluginString;
}


}

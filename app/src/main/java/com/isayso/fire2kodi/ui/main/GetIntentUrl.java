package com.isayso.fire2kodi.ui.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class GetIntentUrl {


    private String getShareLocalUri(Intent intent) {
        Uri contentUri = intent.getData();

        if (contentUri == null) {
            Bundle bundle = intent.getExtras();
            contentUri = (Uri) bundle.get(Intent.EXTRA_STREAM);
        }
        if (contentUri == null) {
            return null;
        }
        return contentUri.toString();
    }

}

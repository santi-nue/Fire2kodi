package com.isayso.fire2kodi.ui.main;

import static android.content.Context.CLIPBOARD_SERVICE;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.isayso.fire2kodi.BuildConfig;
import com.isayso.fire2kodi.R;
import com.isayso.fire2kodi.databinding.FragmentKodidevBinding;

import java.util.Objects;


public class KodiDeviceFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private FragmentKodidevBinding binding;

    public String pluginString ="", result ="", deviceUrl ="";

    public static KodiDeviceFragment newInstance(int index) {
        KodiDeviceFragment fragment = new KodiDeviceFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }


   @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);


   }


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

       // binding = DataBindingUtil.inflate(inflater, R.layout.fragment_kodidev, container, false);

        binding =  FragmentKodidevBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //kodidevice 1:
        final EditText kodiIP = binding.eTxtKodiIP;
        kodiIP.setText(Preferences.read("KodiIP","192.168."));

        final EditText port = binding.eTxtPort;
        port.setText(Preferences.read("Kodiport","8080"));

        final EditText kodiuser = binding.eTxtUser;
        kodiuser.setText(Preferences.read("Kodiuser","kodi"));

        final EditText kodipwd = binding.eTxtPwd;
        kodipwd.setText(Preferences.read("KodiPwd",""));

        //kodidevice 2:
        final EditText kodiIP2 = binding.eTxtKodiIP2;
        kodiIP2.setText(Preferences.read("KodiIP2","192.168."));

        final EditText port2 = binding.eTxtPort2;
        port2.setText(Preferences.read("Kodiport2","8080"));

        final EditText kodiuser2 = binding.eTxtUser2;
        kodiuser2.setText(Preferences.read("Kodiuser2","kodi"));

        final EditText kodipwd2 = binding.eTxtPwd2;
        kodipwd2.setText(Preferences.read("KodiPwd2",""));

        //String versionName = BuildConfig.VERSION_NAME;
        final TextView version = binding.textVersion;
        version.setText(BuildConfig.VERSION_NAME);


        KodiDevice1.IP = kodiIP.getText().toString();
        KodiDevice1.Port = port.getText().toString();
        KodiDevice1.User = kodiuser.getText().toString();
        KodiDevice1.Pwd = kodipwd.getText().toString();

        KodiDevice2.IP = kodiIP2.getText().toString();
        KodiDevice2.Port = port2.getText().toString();
        KodiDevice2.User = kodiuser2.getText().toString();
        KodiDevice2.Pwd = kodipwd2.getText().toString();

        final Button BtnSave = binding.btnSave;
        final Button BtnSave2 = binding.btnSave2;
        final Button BtnPlay = binding.btnKodiPlay;
        final Button BtnQueue = binding.btnKodiQueue;
        final Switch SwAutoQueue = binding.swAutoQueue;
        SwAutoQueue.setChecked(Preferences.readBool("AutoQ", false));

        final Switch SwAutoClose = binding.swAutoClose;
        SwAutoClose.setChecked(Preferences.readBool("AutoC", false));


        final CheckBox ChkKodiDevice1 = binding.checkBox1;
//        if (KodiDevice1.IP.isEmpty()) ChkKodiDevice1.setChecked(false);
//        else
            ChkKodiDevice1.setChecked(Preferences.readBool("ChkD1", true));

        final CheckBox ChkKodiDevice2 = binding.checkBox2;

        if (KodiDevice2.IP.isEmpty()) ChkKodiDevice1.setChecked(false);
        else
            ChkKodiDevice2.setChecked(Preferences.readBool("ChkD2", false));

        final  TextView TxtClipboard = binding.txtClipboard;


        SwAutoQueue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Preferences.saveBool("AutoQ",SwAutoQueue.isChecked());

                if (SwAutoClose.isChecked() && !SwAutoQueue.isChecked()){
                    SwAutoClose.setChecked(false);
                }

            }
        });

        SwAutoClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Preferences.saveBool("AutoC",SwAutoClose.isChecked());

                if (!SwAutoQueue.isChecked()) SwAutoQueue.performClick();

            }
        });


        ChkKodiDevice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ChkKodiDevice2.toggle();

                Preferences.saveBool("ChkD1",ChkKodiDevice1.isChecked());
                Preferences.saveBool("ChkD2",ChkKodiDevice2.isChecked());

            }
        });

        ChkKodiDevice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ChkKodiDevice1.toggle();

                Preferences.saveBool("ChkD1",ChkKodiDevice1.isChecked());
                Preferences.saveBool("ChkD2",ChkKodiDevice2.isChecked());

            }
        });

        BtnSave.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

                KodiDevice1.IP = kodiIP.getText().toString();
                Preferences.save("KodiIP",KodiDevice1.IP);

                KodiDevice1.Port = port.getText().toString();
                Preferences.save("Kodiport",KodiDevice1.Port);

                KodiDevice1.User = kodiuser.getText().toString();
                Preferences.save("Kodiuser",KodiDevice1.User);

                KodiDevice1.Pwd = kodipwd.getText().toString();
                Preferences.save("KodiPwd",KodiDevice1.Pwd);

                Preferences.saveBool("ChkD1",ChkKodiDevice1.isChecked());
                Preferences.saveBool("AutoQ",SwAutoQueue.isChecked());
                Preferences.saveBool("AutoC",SwAutoClose.isChecked());

            Toast.makeText(getContext(),getString(R.string.SetSaved), (int) 10).show();

            }
        });

        BtnSave2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

                KodiDevice2.IP = kodiIP2.getText().toString();
                Preferences.save("KodiIP2",KodiDevice2.IP);

                KodiDevice2.Port = port2.getText().toString();
                Preferences.save("Kodiport2",KodiDevice2.Port);

                KodiDevice2.User = kodiuser2.getText().toString();
                Preferences.save("Kodiuser2",KodiDevice2.User);

                KodiDevice2.Pwd = kodipwd2.getText().toString();
                Preferences.save("KodiPwd2",KodiDevice2.Pwd);

                Preferences.saveBool("ChkD2",ChkKodiDevice2.isChecked());
                Preferences.saveBool("AutoQ",SwAutoQueue.isChecked());
                Preferences.saveBool("AutoC",SwAutoClose.isChecked());


            Toast.makeText(getContext(),getString(R.string.SetSaved), (int) 10).show();

            }
        });

        TxtClipboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClipboardManager clipBoard = (ClipboardManager) v.getContext().getSystemService(CLIPBOARD_SERVICE);

                ClipData mydata= clipBoard.getPrimaryClip();
                ClipData.Item item= mydata.getItemAt(0);
                String mytext= item.getText().toString();

                ValidVideoType  videoType = GetLinktype.ValidLinkCheck(mytext);
                if (videoType != ValidVideoType.Invalid) {
                    TxtClipboard.setText(mytext);
                    Toast.makeText(getContext(), getString(R.string.ValidLink), (int) 10).show();
                }
            }
        });



        BtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

 //String yt_Link = "https://www.youtube.com/watch?v=UjzENV7rhhI";
          //  String yt_Link = " https://www.bitchute.com/video/s9zHpNjSf6xs/";

            String yt_Link = TxtClipboard.getText().toString();

            if (yt_Link.isEmpty() || yt_Link == null ) return;

            ValidVideoType  videoType = GetLinktype.ValidLinkCheck(yt_Link);

            if (videoType != ValidVideoType.Invalid) {

                result = QueueKodi.onIntentQueueKodi(yt_Link, videoType, false);

/*
                pluginString = GetLinktype.GetPluginString(videoType,yt_Link);

                if (ChkKodiDevice1.isChecked()) {
                    deviceUrl = "http://" + KodiDevice1.IP + ":" + KodiDevice1.Port +  "/jsonrpc?request=";
                }
                else if (ChkKodiDevice2.isChecked()){
                    deviceUrl = "http://" + KodiDevice2.IP + ":" + KodiDevice2.Port +  "/jsonrpc?request=";
                }

                  //  new Post2Kodi().execute();

                result = PostKodi.StartKodi(pluginString, deviceUrl,false);
*/

/*

                Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //Post2Kodi.execute();
                            result = PostKodi.makeRequest(pluginString, false, deviceUrl);
                        }
                });

                t.start(); // spawn thread

                try {
                        t.join(); //wait for thread finish

                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
*/

                //assert result != null;
                if (result.contains("OK"))
                    Toast.makeText(getContext(),"Play with Kodi", (int) 10).show();

                else Toast.makeText(getContext(),"Message from Kodi: \n"+ result, (int) 10).show();


/*
                    public void onClick(View v) {
                        new Post2Kodi().execute();
                    }
*/
                }
            else {
                Toast.makeText(getContext(),"Video not supported", (int) 10).show();
            }


            }
        });

        BtnQueue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String yt_Link = TxtClipboard.getText().toString();
                if (yt_Link.isEmpty() /*|| yt_Link == null*/ ) return;

                ValidVideoType  videoType = GetLinktype.ValidLinkCheck(yt_Link);

                if (videoType != ValidVideoType.Invalid) {
                    String result = QueueKodi.onIntentQueueKodi(yt_Link, videoType, true);

 /*                   final boolean[] ActivePlayer = {true};

                    pluginString = GetLinktype.GetPluginString(videoType,yt_Link);

                    if (ChkKodiDevice1.isChecked()) {
                        deviceUrl = "http://" + KodiDevice1.IP + ":" + KodiDevice1.Port +  "/jsonrpc?request=";
                    }
                    else if (ChkKodiDevice2.isChecked()){
                        deviceUrl = "http://" + KodiDevice2.IP + ":" + KodiDevice2.Port +  "/jsonrpc?request=";
                    }

                    //get ActivePlayer
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            ActivePlayer[0] = PostKodi.getStatus(deviceUrl);
                        }
                    });

                    t.start(); // spawn thread

                    try {
                        t.join(); //wait for thread finish

                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }



                    Thread t2 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //Post2Kodi.execute();
                            result = PostKodi.makeRequest(pluginString, ActivePlayer[0] , deviceUrl);
                        }
                    });

                    t2.start(); // spawn thread

                    try {
                        t2.join(); //wait for thread finish

                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
*/
                    //assert result != null;
                    if (result.contains("OK")){
                        Toast.makeText(getContext(),"Queued to Kodi", (int) 10).show();

                    }
                    else Toast.makeText(getContext(),"Message from Kodi: \n"+ result, (int) 10).show();

                }
                else {
                    Toast.makeText(getContext(),"Video not supported", (int) 10).show();
                }


            }
        });


        // from Intent start up
        if (KodiDevice1.URI != null && !KodiDevice1.URI.isEmpty() && Objects.equals(KodiDevice1.Intent, "1")){
            ValidVideoType  videoType = GetLinktype.ValidLinkCheck(KodiDevice1.URI);

            if (videoType != ValidVideoType.Invalid) {
                TxtClipboard.setText(KodiDevice1.URI);

                if (SwAutoQueue.isChecked()) {
                    String result = QueueKodi.onIntentQueueKodi(KodiDevice1.URI, videoType, true);

//                    BtnQueue.performClick();
                    if (SwAutoClose.isChecked()) {
                        requireActivity().onBackPressed();  //back to last activity
                    }

                    assert result != null;
                    if (result.contains("OK")){
                        Toast.makeText(getContext(),"Queued to Kodi", (int) 10).show();
                    }
                    else Toast.makeText(getContext(),"Message from Kodi: \n"+ result, (int) 10).show();

/*
                    Intent startMain = new Intent(Intent.ACTION_MAIN);
                    startMain.addCategory(Intent.CATEGORY_HOME);
                    startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(startMain);
*/
                }
            }
            else {
                Toast.makeText(getContext(),"Video not supported", (int) 10).show();
            }

            KodiDevice1.Intent = "0";
        }


/*
        final TextView textView = binding.sectionLabel;
        pageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
*/

      //  TxtClipboard.performClick();  //todo  weiss nicht
      //  TxtClipboard.callOnClick();  //todo  weiss nicht



        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}

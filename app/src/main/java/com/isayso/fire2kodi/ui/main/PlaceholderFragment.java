package com.isayso.fire2kodi.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.isayso.fire2kodi.databinding.FragmentMainBinding;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private FragmentMainBinding binding;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentMainBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final EditText textYT = binding.editTextYT;
        textYT.setText(Preferences.read("YTaddon","plugin.video.youtube/play/?video_id="));

        final EditText textRMB = binding.editTextRmb;
        textRMB.setText(Preferences.read("RMBaddon","plugin.video.rumble.matrix/?url=https://rumble.com/"));

        final EditText textBit = binding.editTextRmb;
        textBit.setText(Preferences.read("BITaddon","plugin.video.bitchute/play_now/"));

        final EditText textOdy = binding.editTextRmb;
        textOdy.setText(Preferences.read("ODYaddon","plugin.video.lbry/play/"));

        final EditText textVim = binding.editTextVim;
        textVim.setText(Preferences.read("VIMaddon","plugin.video.vimeo/play/?video_id="));

        final EditText textDay = binding.editTextDay;
        textDay.setText(Preferences.read("DAYaddon","plugin.video.dailymotion_com/?url="));

        final EditText textBan = binding.editTextBanned;
        textDay.setText(Preferences.read("BANaddon","plugin.video.banned.video/?url=https://assets.infowarsmedia.com/"));

        final Button BtnSave = binding.buttonSave;
        final Button BtnCancel = binding.buttonCancel;




        BtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Preferences.save("YTaddon",textYT.getText().toString());
                Preferences.save("RMBaddon",textRMB.getText().toString());
                Preferences.save("BITaddon",textBit.getText().toString());
                Preferences.save("ODYaddon",textOdy.getText().toString());
                Preferences.save("VIMaddon",textVim.getText().toString());
                Preferences.save("DAYaddon",textDay.getText().toString());
                Preferences.save("BANaddon",textBan.getText().toString());

                Toast.makeText(getContext(),"Saved, please restart App", (int) 15).show();


            }
        });

        BtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textYT.setText(Preferences.read("YTaddon","plugin.video.youtube/play/?video_id="));
                textRMB.setText(Preferences.read("RMBaddon","plugin.video.rumble.matrix/?url=https://rumble.com/"));
                textBit.setText(Preferences.read("BITaddon","plugin.video.bitchute/play_now/"));
                textOdy.setText(Preferences.read("ODYaddon","plugin.video.lbry/play/"));
                textVim.setText(Preferences.read("VIMaddon","plugin.video.vimeo/play/?video_id="));
                textDay.setText(Preferences.read("DAYaddon","plugin.video.dailymotion_com/?url="));
                textBan.setText(Preferences.read("BANaddon","plugin.video.banned.video/?url=https://assets.infowarsmedia.com/"));

            }
        });


        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
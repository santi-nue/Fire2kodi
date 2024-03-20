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

import com.isayso.fire2kodi.R;
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
        textYT.setText(Preferences.read("YTaddon",getResources().getString(R.string.youtube)));

        final EditText textRMB = binding.editTextRmb;
        textRMB.setText(Preferences.read("RMBaddon",getResources().getString(R.string.rumble)));

        final EditText textBit = binding.editTextBit;
        textBit.setText(Preferences.read("BITaddon",getResources().getString(R.string.bitchute)));

        final EditText textOdy = binding.editTextOdy;
        textOdy.setText(Preferences.read("ODYaddon",getResources().getString(R.string.odysee)));

        final EditText textVim = binding.editTextVim;
        textVim.setText(Preferences.read("VIMaddon",getResources().getString(R.string.vimeo)));

        final EditText textDay = binding.editTextDay;
        textDay.setText(Preferences.read("DAYaddon",getResources().getString(R.string.dailymotion)));

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

                Toast.makeText(getContext(),"Saved, please restart App", (int) 15).show();


            }
        });

        BtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textYT.setText(Preferences.read("YTaddon",getResources().getString(R.string.youtube)));
                textRMB.setText(Preferences.read("RMBaddon",getResources().getString(R.string.rumble)));
                textBit.setText(Preferences.read("BITaddon",getResources().getString(R.string.bitchute)));
                textOdy.setText(Preferences.read("ODYaddon",getResources().getString(R.string.odysee)));
                textVim.setText(Preferences.read("VIMaddon",getResources().getString(R.string.vimeo)));
                textDay.setText(Preferences.read("DAYaddon",getResources().getString(R.string.dailymotion)));

            }
        });

       BtnCancel.setOnLongClickListener(new View.OnLongClickListener() {
           @Override
           public boolean onLongClick(View view) {
               Preferences.reset();
               Toast.makeText(getContext(),"Values reset", (int) 10).show();
               return true;
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
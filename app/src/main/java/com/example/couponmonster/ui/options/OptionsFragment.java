package com.example.couponmonster.ui.options;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.couponmonster.AppState;
import com.example.couponmonster.MainActivity;
import com.example.couponmonster.R;

public class OptionsFragment extends Fragment {

    private OptionsViewModel optionsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final AppState appState = AppState.getInstance();
        optionsViewModel =
                ViewModelProviders.of(this).get(OptionsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_options, container, false);

        Button connectButton = root.findViewById(R.id.connect_button);
        Button changeUserdataButton = root.findViewById(R.id.change_userdata_button);

        final EditText nameEditText = root.findViewById(R.id.name);
        final EditText usernameEditText = root.findViewById(R.id.username);
        TextView scoreText = root.findViewById(R.id.score);
        if(appState.user != null){
            nameEditText.setText(appState.user.name);
            usernameEditText.setText(appState.user.username);
            scoreText.setText("Score: " + appState.user.score);
        }
        changeUserdataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppState.getInstance().listener.addMessage("8" + nameEditText.getText().toString() + "|" + usernameEditText.getText().toString());
            }
        });
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getContext()).connect();
            }
        });
        return root;
    }
}

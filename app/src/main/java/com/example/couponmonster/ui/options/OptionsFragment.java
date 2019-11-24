package com.example.couponmonster.ui.options;

import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.couponmonster.AppState;
import com.example.couponmonster.MainActivity;
import com.example.couponmonster.R;
import com.example.couponmonster.UsernameChangeThread;

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


        AppState.getInstance().listener.addMessage("6");
        changeUserdataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsernameChangeThread t = new UsernameChangeThread(nameEditText.getText().toString(),usernameEditText.getText().toString());
                Thread runner = new Thread(t);
                runner.start();
                try {
                    runner.join();
                    if(t.result){
                        Toast.makeText(getContext(),"Username changed", Toast.LENGTH_LONG).show();
                        nameEditText.setText(t.name);
                        usernameEditText.setText(t.username);
                    }else{
                        Toast.makeText(getContext(),"Username taken", Toast.LENGTH_LONG).show();
                    }
                }catch (InterruptedException e){
                    Toast.makeText(getContext(),"Cannot change", Toast.LENGTH_LONG).show();
                }
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

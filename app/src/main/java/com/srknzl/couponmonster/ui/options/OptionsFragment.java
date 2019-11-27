package com.srknzl.couponmonster.ui.options;

import android.annotation.SuppressLint;
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

import com.srknzl.couponmonster.AppState;
import com.srknzl.couponmonster.MainActivity;
import com.srknzl.couponmonster.R;
import com.srknzl.couponmonster.UsernameChangeThread;

public class OptionsFragment extends Fragment {

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final AppState appState = AppState.getInstance();
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
                UsernameChangeThread t = new UsernameChangeThread(nameEditText.getText().toString(),usernameEditText.getText().toString(),AppState.getInstance().user.username);
                Thread runner = new Thread(t);
                runner.start();
                try {
                    runner.join();
                    if(t.result){
                        Toast.makeText(getContext(),"Username and name changed", Toast.LENGTH_LONG).show();
                        nameEditText.setText(t.name);
                        usernameEditText.setText(t.username);
                        AppState.getInstance().user.username = t.username;
                        AppState.getInstance().user.name = t.name;
                    }else{
                        Toast.makeText(getContext(),"Username taken, name updated", Toast.LENGTH_LONG).show();
                        AppState.getInstance().user.name = t.name;
                    }
                }catch (InterruptedException e){
                    Toast.makeText(getContext(),"Cannot change", Toast.LENGTH_LONG).show();
                }
            }
        });
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AppState.getInstance().listener == null){
                    if(getContext()!=null)((MainActivity)getContext()).connect();
                }else{
                    Toast.makeText(getContext(),"Already connected",Toast.LENGTH_LONG).show();
                }
            }
        });
        return root;
    }
}

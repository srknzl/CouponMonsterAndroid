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
        final EditText addressEditText = root.findViewById(R.id.address);
        final EditText portEditText = root.findViewById(R.id.port);

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ((MainActivity)getContext()).connect(addressEditText.getText().toString(),Integer.parseInt(portEditText.getText().toString()));
                }catch (NumberFormatException e){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Please enter a valid port. You entered: " + portEditText.getText().toString());
                    builder.setTitle("Not connected");
                    builder.create().show();
                }
            }
        });
        return root;
    }
}

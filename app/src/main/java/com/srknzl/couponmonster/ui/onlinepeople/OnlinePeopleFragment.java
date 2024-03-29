package com.srknzl.couponmonster.ui.onlinepeople;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.srknzl.couponmonster.AppState;
import com.srknzl.couponmonster.R;
import com.srknzl.couponmonster.ui.OnlineGridAdapter;


public class OnlinePeopleFragment extends Fragment {

    private GridView gridView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AppState.getInstance().listener.addMessage("7");
        Toast.makeText(getContext(),"Refreshing...",Toast.LENGTH_LONG).show();
        return inflater.inflate(R.layout.fragment_online, container, false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        OnlinePeopleViewModel onlinePeopleViewModel = ViewModelProviders.of(this).get(OnlinePeopleViewModel.class);
        super.onActivityCreated(savedInstanceState);
        if(this.getView() != null){
            this.gridView = this.getView().findViewById(R.id.online_people);
            final TextView textView = this.getView().findViewById(R.id.text_online);
            onlinePeopleViewModel.getText().observe(this, new Observer<String>() {
                @Override
                public void onChanged(@Nullable String s) {
                    textView.setText(s);
                    gridView.invalidateViews();
                }
            });
            OnlineGridAdapter onlineGridAdapter = new OnlineGridAdapter( this.getContext());
            gridView.setAdapter(onlineGridAdapter);
        }


    }
}
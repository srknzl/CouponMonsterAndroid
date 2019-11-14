package com.example.couponmonster.ui.onlinepeople;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.couponmonster.OnlinePerson;
import com.example.couponmonster.R;
import com.example.couponmonster.ui.OnlineGridAdapter;

import java.util.Vector;

public class OnlinePeopleFragment extends Fragment {

    private OnlinePeopleViewModel onlinePeopleViewModel;
    private GridView gridView;
    private Vector<OnlinePerson> onlinePeople;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        onlinePeopleViewModel =
                ViewModelProviders.of(this).get(OnlinePeopleViewModel.class);
        View root = inflater.inflate(R.layout.fragment_online, container, false);
        final TextView textView = root.findViewById(R.id.text_online);
        gridView = root.findViewById(R.id.online_people);
        onlinePeopleViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        onlinePeopleViewModel.getOnlinePeople().observe(this,new Observer<Vector<OnlinePerson>>(){
            @Override
            public void onChanged(Vector<OnlinePerson> onlinePersonVector) {
                onlinePeople = onlinePersonVector;
                createGridView(onlinePeople);
            }
        });

        return root;
    }
    private void createGridView(Vector<OnlinePerson> onlinePeopleVec){
        OnlineGridAdapter onlineGridAdapter = new OnlineGridAdapter( this.getContext(), onlinePeopleVec);
        gridView.setAdapter(onlineGridAdapter);
    }
}
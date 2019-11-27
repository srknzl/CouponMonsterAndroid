package com.srknzl.couponmonster.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.srknzl.couponmonster.AppState;
import com.srknzl.couponmonster.Data.OnlinePerson;
import com.srknzl.couponmonster.R;

import java.util.Collections;
import java.util.Vector;

public class OnlineGridAdapter extends BaseAdapter {
    private final android.content.Context context;
    private final Vector<OnlinePerson> onlinePeople;

    public OnlineGridAdapter(Context context) {
        this.context = context;
        this.onlinePeople = AppState.getInstance().onlinePeople;
    }

    @Override
    public int getCount() {
        return this.onlinePeople.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public OnlinePerson getItem(int position)  {
        OnlinePerson onlinePerson = new OnlinePerson();
        try {
            Collections.sort(onlinePeople);
            onlinePerson = this.onlinePeople.elementAt(position);
        } catch( Exception e ){
            e.printStackTrace();
        }
        return onlinePerson;
    }

    @SuppressLint({"SetTextI18n", "InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent)  {
        TextView PersonName = null;
        TextView NickName = null;
        TextView Score = null;
        TextView Rank = null;
        OnlinePerson onlinePerson = getItem(position);
        if(convertView != null){
            PersonName = convertView.findViewById(R.id.person_name);
            NickName = convertView.findViewById(R.id.person_nickname);
            Score = convertView.findViewById(R.id.person_score);
            Rank = convertView.findViewById(R.id.rank);
            PersonName.setText("Name: " + onlinePerson.name);
            NickName.setText("Username: " + onlinePerson.username);
            Score.setText("Score: " + onlinePerson.score);
            Rank.setText("Rank: " + (position+1));
            return convertView;
        }else{
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            View gridView = null;
            if(inflater != null){
                gridView = inflater.inflate(R.layout.online_grid_adapter, null);
                PersonName = gridView.findViewById(R.id.person_name);
                NickName = gridView.findViewById(R.id.person_nickname);
                Score = gridView.findViewById(R.id.person_score);
                Rank = gridView.findViewById(R.id.rank);
            }
            if(PersonName != null && NickName != null && Score != null && Rank != null){

                PersonName.setText("Name: " + onlinePerson.name);
                NickName.setText("Username: " + onlinePerson.username);
                Score.setText("Score: " + onlinePerson.score);
                Rank.setText("Rank: " + (position+1));
            }
            return gridView;
        }
    }
}

package com.example.couponmonster.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.couponmonster.Data.OnlinePerson;
import com.example.couponmonster.R;

import java.util.Vector;

public class OnlineGridAdapter extends BaseAdapter {
    private final android.content.Context context;
    private final Vector<OnlinePerson> onlinePeople;

    public OnlineGridAdapter(Context context,Vector<OnlinePerson> onlinePeople) {
        this.context = context;
        this.onlinePeople = onlinePeople;
    }

    @Override
    public int getCount() {
        return this.onlinePeople.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public OnlinePerson getItem(int position)  {
        OnlinePerson onlinePerson = new OnlinePerson();
        try {
            onlinePerson = this.onlinePeople.elementAt(position);
        } catch( Exception e ){
            e.printStackTrace();
        }
        return onlinePerson;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)  {
        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View gridView = inflater.inflate(R.layout.online_grid_adapter, null);
        OnlinePerson onlinePerson = getItem(position);

        TextView PersonName = (TextView) gridView.findViewById(R.id.person_name);
        TextView NickName = (TextView) gridView.findViewById(R.id.person_nickname);
        TextView LastLogin = (TextView) gridView.findViewById(R.id.person_lastlogin);
        try {
            PersonName.setText(onlinePerson.getName());
            NickName.setText(onlinePerson.getNickName());
            LastLogin.setText(onlinePerson.getLastLogin());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridView;
    }
}

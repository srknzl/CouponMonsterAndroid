package com.example.couponmonster.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.couponmonster.AppState;
import com.example.couponmonster.R;
import com.example.couponmonster.ui.CouponAdapter;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;



public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    public static RecyclerView recyclerView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        return root;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        // TODO: Use the ViewModel
        final TextView textView = this.getView().findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        recyclerView = this.getView().findViewById(R.id.coupons);
        CouponAdapter couponAdapter = new CouponAdapter(this.getContext(), AppState.getInstance().coupons);
        recyclerView.setAdapter(couponAdapter);
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(this.getContext());
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        flexboxLayoutManager.setAlignItems(AlignItems.CENTER);
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);
        flexboxLayoutManager.setJustifyContent(JustifyContent.SPACE_EVENLY);
        recyclerView.setLayoutManager(flexboxLayoutManager);
        /*Button b = this.getView().findViewById(R.id.add_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppState.getInstance().coupons.add(new Coupon("hash",new Date(),"asdasd","sadsd",1,2));
                recyclerView.getAdapter().notifyItemInserted(recyclerView.getAdapter().getItemCount()-1);
                *//*AppState.getInstance().coupons.add(new Coupon("hash",new Date(),"asdasd","sadsd",1,2));
                AppState.getInstance().coupons.add(new Coupon("hash",new Date(),"asdasd","sadsd",1,2));
                AppState.getInstance().coupons.add(new Coupon("hash",new Date(),"asdasd","sadsd",1,2));
                AppState.getInstance().coupons.add(new Coupon("hash",new Date(),"asdasd","sadsd",1,2));
                AppState.getInstance().coupons.add(new Coupon("hash",new Date(),"asdasd","sadsd",1,2));
                recyclerView.getAdapter().notifyDataSetChanged();*//*
            }
        });*/
    }

}
package com.example.couponmonster.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.couponmonster.Coupon;
import com.example.couponmonster.R;
import com.example.couponmonster.ui.CouponGridAdapter;

import java.util.Vector;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private GridView gridView;
    private Vector<Coupon> coupons;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        gridView = root.findViewById(R.id.coupons);
        homeViewModel.getCoupons().observe(this,new Observer<Vector<Coupon>>(){
            @Override
            public void onChanged(Vector<Coupon> couponsVec) {
                coupons = couponsVec;
                createGridView(coupons);
            }
        });

        return root;
    }

    private void createGridView(Vector<Coupon> couponsVec){
        CouponGridAdapter couponGridAdapter = new CouponGridAdapter( this.getContext(), couponsVec);
        gridView.setAdapter(couponGridAdapter);
    }
}
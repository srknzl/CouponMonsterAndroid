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

import static com.example.couponmonster.R.layout.fragment_home;


public class HomeFragment extends Fragment {

    public static RecyclerView recyclerView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(fragment_home, container, false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        HomeViewModel homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        if(this.getView() != null){
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
        }

    }

}
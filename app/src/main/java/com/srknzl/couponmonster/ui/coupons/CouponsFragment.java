package com.srknzl.couponmonster.ui.coupons;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.srknzl.couponmonster.AppState;
import com.srknzl.couponmonster.R;
import com.srknzl.couponmonster.ui.GainedCouponAdapter;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;


public class CouponsFragment extends Fragment {
    public static RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_coupons, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(this.getView() != null){
            recyclerView = this.getView().findViewById(R.id.gained_coupons);
            GainedCouponAdapter couponAdapter = new GainedCouponAdapter(this.getContext(), AppState.getInstance().gainedCoupons);
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

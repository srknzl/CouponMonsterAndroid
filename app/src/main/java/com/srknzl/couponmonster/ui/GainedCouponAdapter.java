package com.srknzl.couponmonster.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.srknzl.couponmonster.Data.Coupon;
import com.srknzl.couponmonster.Data.CouponViewHolder;
import com.srknzl.couponmonster.R;

import java.util.Locale;
import java.util.Vector;

public class GainedCouponAdapter extends RecyclerView.Adapter<CouponViewHolder> {
    private final android.content.Context context;
    private Vector<Coupon> coupons;
    public GainedCouponAdapter(Context context, Vector<Coupon> coupons) {
        this.context = context;
        this.coupons = coupons;
    }
    @Override
    public int getItemCount() {
        return coupons.size();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final CouponViewHolder holder, int position) {
        final Coupon SingleCoupon = coupons.get(position);
        TextView CouponNumber = holder.itemView.findViewById(R.id.gained_coupon_number);
        TextView CouponReward = holder.itemView.findViewById(R.id.gained_coupon_reward);
        TextView CouponScore = holder.itemView.findViewById(R.id.gained_coupon_score);
        CouponNumber.setText(String.format(Locale.ENGLISH,"%d",position+1));
        String reward = SingleCoupon.getReward();
        int indexOfFirstTL = reward.indexOf("TL");
        int difficulty = 0;
        try{
            difficulty = Integer.parseInt(reward.substring(0, indexOfFirstTL)) / 10;

        }catch (NumberFormatException e){}
        CouponReward.setText(reward);
        CouponScore.setText("Gained Score: " + difficulty);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Congratulations!!", Toast.LENGTH_LONG).show();
            }
        });
    }

    @NonNull
    @Override
    public CouponViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View couponView = inflater.inflate(R.layout.gained_coupon, parent,false);
        return new CouponViewHolder(couponView);
    }
}

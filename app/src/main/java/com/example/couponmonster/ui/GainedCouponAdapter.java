package com.example.couponmonster.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.couponmonster.Data.Coupon;
import com.example.couponmonster.Data.CouponViewHolder;
import com.example.couponmonster.R;

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

    @Override
    public void onBindViewHolder(@NonNull final CouponViewHolder holder, int position) {
        final Coupon SingleCoupon = coupons.get(position);
        TextView CouponHash = holder.itemView.findViewById(R.id.gained_coupon_hash);
        TextView CouponNumber = holder.itemView.findViewById(R.id.gained_coupon_number);
        TextView CouponReward = holder.itemView.findViewById(R.id.gained_coupon_reward);

        CouponHash.setText(SingleCoupon.getHash());
        CouponNumber.setText(String.format(Locale.ENGLISH,"%d",position+1));
        CouponReward.setText(SingleCoupon.getReward());

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

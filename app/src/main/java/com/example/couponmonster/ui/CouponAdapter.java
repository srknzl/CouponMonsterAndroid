package com.example.couponmonster.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.couponmonster.AppState;
import com.example.couponmonster.Data.Coupon;
import com.example.couponmonster.Data.CouponViewHolder;
import com.example.couponmonster.R;

import java.util.Vector;

public class CouponAdapter extends RecyclerView.Adapter<CouponViewHolder> {
    private final android.content.Context context;
    public Vector<Coupon> coupons;
    public CouponAdapter(Context context, Vector<Coupon> coupons) {
        this.context = context;
        this.coupons = coupons;
    }
    @Override
    public int getItemCount() {
        return coupons.size();
    }

    @Override
    public void onBindViewHolder(@NonNull CouponViewHolder holder, int position) {
        final Coupon SingleCoupon = coupons.get(position);
        Button CouponButton = holder.itemView.findViewById(R.id.coupon_button);
        TextView CouponHash = holder.itemView.findViewById(R.id.coupon_hash);
        TextView CouponNumber = holder.itemView.findViewById(R.id.coupon_number);
        TextView CouponReward = holder.itemView.findViewById(R.id.coupon_reward);

        try {
            CouponHash.setText(SingleCoupon.getHash());
            CouponNumber.setText(Integer.toString(position+1));
            CouponReward.setText(SingleCoupon.getReward());
            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppState.getInstance().listener.addMessage("4" + SingleCoupon.getHash());
                }
            };
            CouponButton.setOnClickListener(onClickListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public CouponViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View couponView = inflater.inflate(R.layout.coupon_grid_adapter, parent,false);
        return new CouponViewHolder(couponView);
    }
}

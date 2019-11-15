package com.example.couponmonster.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.couponmonster.Data.Coupon;
import com.example.couponmonster.R;

import java.util.Vector;

public class CouponGridAdapter extends BaseAdapter {
    private final android.content.Context context;
    private final Vector<Coupon> coupons;

    public CouponGridAdapter(Context context,Vector<Coupon> coupons) {
        this.context = context;
        this.coupons = coupons;
    }

    @Override
    public int getCount() {
        return this.coupons.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Coupon getItem(int position)  {
        Coupon curCoupon = new Coupon();
        try {
            curCoupon = this.coupons.elementAt(position);
        } catch( Exception e ){
            e.printStackTrace();
        }
        return curCoupon;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)  {
        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View gridView = inflater.inflate(R.layout.coupon_grid_adapter, null);
        Coupon SingleCoupon = getItem(position);
        Button CouponButton = (Button) gridView.findViewById(R.id.coupon_button);
        TextView CouponHash = (TextView) gridView.findViewById(R.id.coupon_hash);
        TextView CouponNumber = (TextView) gridView.findViewById(R.id.coupon_number);
        TextView CouponReward = (TextView) gridView.findViewById(R.id.coupon_reward);

        try {
            CouponHash.setText(SingleCoupon.getHash());
            CouponNumber.setText(Integer.toString(position+1));
            CouponReward.setText(SingleCoupon.getReward());
            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Button click")
                            .setTitle("Button");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            };
            CouponButton.setOnClickListener(onClickListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridView;
    }
}

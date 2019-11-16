package com.example.couponmonster.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.couponmonster.Data.Coupon;
import com.example.couponmonster.R;

import java.util.Random;
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
        final LayoutInflater inflater = (LayoutInflater) this.context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View gridView = inflater.inflate(R.layout.coupon_grid_adapter, null);
        final Coupon SingleCoupon = getItem(position);

        Button CouponButton = gridView.findViewById(R.id.coupon_button);
        TextView CouponHash = gridView.findViewById(R.id.coupon_hash);
        TextView CouponNumber = gridView.findViewById(R.id.coupon_number);
        TextView CouponReward = gridView.findViewById(R.id.coupon_reward);

        try {
            CouponHash.setText(SingleCoupon.getHash());
            CouponNumber.setText(Integer.toString(position+1));
            CouponReward.setText(SingleCoupon.getReward());
            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(new Random().nextInt(10) < 5){
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        final View questionView = inflater.inflate(R.layout.question_dialog, null);
                        TextView question = questionView.findViewById(R.id.question);
                        question.setText(SingleCoupon.getProblem());
                        builder.setView(questionView);
                        final AlertDialog dialog = builder.create();
                        dialog.show();
                        new CountDownTimer(SingleCoupon.getSolveTime()*1000, 1000) {
                            Resources res = context.getResources();
                            public void onTick(long millisUntilFinished) {
                                ((TextView)questionView.findViewById(R.id.remaining_time)).setText(String.format(res.getString(R.string.question_remaining),millisUntilFinished/1000));
                            }
                            public void onFinish() {
                                dialog.dismiss();
                            }
                        }.start();
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.AlertDialogTheme);
                        builder.setMessage("Sorry someone else solving the coupon's question!");
                        builder.setTitle("Busy!");
                        builder.setPositiveButton(R.string.ok,null);
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }


                }
            };
            CouponButton.setOnClickListener(onClickListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridView;
    }
}

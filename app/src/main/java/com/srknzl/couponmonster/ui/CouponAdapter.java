package com.srknzl.couponmonster.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.srknzl.couponmonster.AppState;
import com.srknzl.couponmonster.Data.Coupon;
import com.srknzl.couponmonster.Data.CouponViewHolder;
import com.srknzl.couponmonster.R;
import com.srknzl.couponmonster.SelectionThread;

import java.util.Locale;
import java.util.Vector;

public class CouponAdapter extends RecyclerView.Adapter<CouponViewHolder> {
    private final android.content.Context context;
    private Vector<Coupon> coupons;
    public CouponAdapter(Context context, Vector<Coupon> coupons) {
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
        Button CouponButton = holder.itemView.findViewById(R.id.coupon_button);
        TextView CouponNumber = holder.itemView.findViewById(R.id.coupon_number);
        TextView CouponReward = holder.itemView.findViewById(R.id.coupon_reward);
        TextView CouponDifficulty = holder.itemView.findViewById(R.id.coupon_difficulty);
        try {
            String reward = SingleCoupon.getReward();
            int indexOfFirstTL = reward.indexOf("TL");
            int difficulty = Integer.parseInt(reward.substring(0, indexOfFirstTL)) / 10;
            CouponNumber.setText(String.format(Locale.ENGLISH,"%d",position+1));
            CouponReward.setText(reward);
            CouponDifficulty.setText("Difficulty: " + difficulty);
            final LayoutInflater inflater = (LayoutInflater) this.context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(inflater != null){
                        if(!AppState.getInstance().attempting){
                            AppState.getInstance().attempting = true;
                            Toast.makeText(context, "Selecting..", Toast.LENGTH_LONG).show();
                            SelectionThread s = new SelectionThread(SingleCoupon.getHash());
                            Thread st = new Thread(s);
                            st.start();
                            try {
                                st.join();
                                if (s.result) {

                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    final View questionView = inflater.inflate(R.layout.question_dialog, null);
                                    final TextView question = questionView.findViewById(R.id.question);
                                    question.setText(SingleCoupon.getProblem());
                                    builder.setView(questionView);
                                    final AlertDialog dialog = builder.create();
                                    final EditText answerView = questionView.findViewById(R.id.answer);

                                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                        @Override
                                        public void onDismiss(DialogInterface dialog) {
                                            AppState.getInstance().attempting = false;
                                            if(AppState.getInstance().listener != null){
                                                AppState.getInstance().listener.addMessage("5" + SingleCoupon.getHash());
                                            }
                                            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                                            if(imm != null){
                                                imm.hideSoftInputFromWindow(answerView.getWindowToken(),InputMethodManager.HIDE_IMPLICIT_ONLY);
                                            }

                                        }
                                    });
                                    dialog.setCancelable(false);

                                    dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                                        @Override
                                        public void onShow(DialogInterface dialog) {
                                            answerView.setFocusableInTouchMode(true);
                                            answerView.setFocusable(true);
                                            answerView.requestFocus();
                                            InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
                                            if(imm != null){
                                                imm.toggleSoftInputFromWindow(answerView.getWindowToken(),
                                                        InputMethodManager.SHOW_IMPLICIT, 0);
                                            }
                                        }
                                    });

                                    dialog.show();



                                    new CountDownTimer(SingleCoupon.getSolveTime() * 1000, 1000) {
                                        Resources res = context.getResources();

                                        public void onTick(long millisUntilFinished) {
                                            ((TextView) questionView.findViewById(R.id.remaining_time)).setText(String.format(res.getString(R.string.question_remaining), millisUntilFinished / 1000));
                                        }

                                        public void onFinish() {
                                            dialog.dismiss();
                                        }
                                    }.start();
                                    Button qb = questionView.findViewById(R.id.question_button);
                                    Button cb = questionView.findViewById(R.id.cancel_button);
                                    qb.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (AppState.getInstance().listener != null && AppState.getInstance().listener.out != null) {
                                                AppState.getInstance().listener.addMessage("3" + SingleCoupon.getHash() + "|" + answerView.getText().toString());
                                                Toast.makeText(context, "Submitted...", Toast.LENGTH_LONG).show();
                                                dialog.dismiss();
                                            } else {
                                                Toast.makeText(context, "You cannot submit because you are not connected anymore.", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                    cb.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                        }
                                    });
                                } else {
                                    AppState.getInstance().attempting = false;
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
                                    builder.setMessage("Sorry someone else solving the coupon's question!");
                                    builder.setTitle("Busy!");
                                    builder.setPositiveButton(R.string.ok, null);
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();
                                }
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        }else {
                            Toast.makeText(context, "Please attempt one coupon at a time!", Toast.LENGTH_LONG).show();

                        }
                    }else {
                        Toast.makeText(context,"Sorry view cannot be generated",Toast.LENGTH_LONG).show();
                    }

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

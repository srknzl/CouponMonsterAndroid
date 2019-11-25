package com.example.couponmonster;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.couponmonster.Data.Coupon;
import com.example.couponmonster.Data.OnlinePerson;
import com.example.couponmonster.ui.CouponAdapter;
import com.example.couponmonster.ui.OnlineGridAdapter;
import com.example.couponmonster.ui.home.HomeFragment;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Vector;

public class Listener implements Runnable {
    private Activity context;
    private Socket socket;
    public PrintWriter out;
    private LinkedList<String> messageQueue;
    private int pulseCounter = 0;


    Listener(Context context) {
        this.context = (Activity) context;
        this.socket = new Socket();
        this.messageQueue = new LinkedList<>();
    }
    void close(){
        try{
            socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public void run(){
        AppState appState = AppState.getInstance();
        try {
            InetAddress inetAddress = InetAddress.getByName("104.248.43.186");
            SocketAddress socketAddress = new InetSocketAddress(inetAddress,6000);
            socket.connect(socketAddress);
            appState.connected = true;
            Scanner in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(),true);
            out.println("0" + appState.user.name + "|" + appState.user.username);
            AppState.getInstance().listener.addMessage("7");
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ((TextView)context.findViewById(R.id.connectionStatus)).setText(R.string.appbar_connected);
                }
            });
            while(true){
                pulseCounter = (pulseCounter+1)%10;
                if(pulseCounter==0)out.println("9");

                if(out.checkError() || Thread.interrupted()){
                    appState.connected = false;
                    appState.coupons.clear();
                    appState.listener = null;
                    appState.listenerThread = null;
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(HomeFragment.recyclerView != null && HomeFragment.recyclerView.getAdapter() != null){
                                HomeFragment.recyclerView.getAdapter().notifyDataSetChanged();
                            }
                        }
                    });
                    appState.onlinePeople = new Vector<>();
                    appState.user = new OnlinePerson();
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((TextView)context.findViewById(R.id.connectionStatus)).setText(R.string.appbar_notconnected);
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Connection Lost");
                            builder.setMessage("Your connection to the server is lost");
                            builder.setNeutralButton("Ok",null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                            Button b = dialog.getButton(DialogInterface.BUTTON_NEUTRAL);
                            b.setTextColor(Color.BLUE);
                        }
                    });
                    try{
                        socket.close();
                    }catch (IOException ee){
                        ee.printStackTrace();
                    }
                    return;
                }

                if(socket.getInputStream().available()>0 && in.hasNextLine()){
                    String read = in.nextLine();
                    processMessages(read);
                }

                if( messageQueue.size() > 0){
                    Log.e("Sending","");
                    out.println(messageQueue.removeFirst());
                }
                try{
                    Thread.sleep(100);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }

        }catch (IOException e){
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context,"Could not connect, server may be down.",Toast.LENGTH_LONG).show();
                }
            });
            appState.connected = false;
        }
    }

    /*
         0 Send: Hello -> Get: Welcome + initial coupons
         1 Get: New Coupon
         2 Get: Clear coupon
         3 Send: Answer -> Get: True/False
         4 Send: Selection -> Get:True/False
         5 Send: Dismiss
         6 Get: User data
         7 Get: All users
         8 Send: New name and username Get: Yes/No
         9 Get: Pulse Send: Pulse
     */
    private void processMessages(String message){
        if(message.charAt(0)!='9')Log.e("Listener: ", message);
        if(message.charAt(0)=='0') {
            final Vector<Coupon> initialCoupons = new Vector<>();
            message = message.substring(1);
            String[] coupons = message.split("[;]");
            for (String coupon : coupons) {
                String[] fields = coupon.split("\\|");
                if (fields.length != 5) continue;
                final String hash = fields[0];
                final String problem = fields[1];
                final String reward = fields[2];
                final int solveTime;
                try {
                    solveTime = Integer.parseInt(fields[3]);
                } catch (NumberFormatException e) {
                    continue;
                }
                initialCoupons.add(new Coupon(hash, problem, reward, solveTime));
            }
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AppState.getInstance().coupons = initialCoupons;
                    final CouponAdapter couponAdapter = new CouponAdapter( context, AppState.getInstance().coupons);
                    HomeFragment.recyclerView.setAdapter(couponAdapter);
                    //Log.e("Enter",AppState.getInstance().coupons.toString());
                }
            });

        }
        else if(message.charAt(0)=='1'){
            message = message.substring(1);
            String[] fields = message.split("\\|");
            if(fields.length != 5)return;
            final String hash = fields[0];
            final String problem = fields[1];
            final String reward = fields[2];
            final int solveTime;
            try {
                solveTime = Integer.parseInt(fields[3]);
            }catch (NumberFormatException e) {
                return;
            }
            AppState.getInstance().coupons.add(new Coupon(hash,problem,reward,solveTime));
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(HomeFragment.recyclerView !=null && HomeFragment.recyclerView.getAdapter() != null){
                        HomeFragment.recyclerView.getAdapter().notifyItemInserted(AppState.getInstance().coupons.size()-1);
                    }
                }
            });
        }else if(message.charAt(0)=='2'){
            String[] tokens = message.substring(1).split("\\|");
            final String username = tokens[2];
            final String difficulty = tokens[3];
            final int ret = AppState.getInstance().removeCoupon(tokens[0]);
            if(ret >= 0){
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(HomeFragment.recyclerView !=null && HomeFragment.recyclerView.getAdapter() != null){
                            HomeFragment.recyclerView.getAdapter().notifyItemRemoved(ret);
                        }
                        Toast.makeText(context,username+" has won a coupon with " + difficulty + " difficulty!",Toast.LENGTH_LONG).show();
                    }
                });
                if(username.equals(AppState.getInstance().user.username)){
                    AppState.getInstance().user.score += Integer.parseInt(difficulty);
                }
            }
        }else if(message.charAt(0)=='3'){
            String[] tokens = message.substring(1).split("\\|");
            if(tokens[0].equals("Yes")){
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Success!", Toast.LENGTH_LONG).show();
                    }
                });
            }else{
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Not correct answer!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }else if(message.charAt(0)=='4'){
            /*
            String[] tokens = message.substring(1).split("\\|");
            String result = tokens[0];
            final String hash = tokens[1];
            if(result.equals("Yes")){
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        final View questionView = inflater.inflate(R.layout.question_dialog, null);
                        TextView question = questionView.findViewById(R.id.question);

                        Coupon temp = null;
                        for (Coupon c : AppState.getInstance().coupons) {
                            if (c.getHash().equals(hash)) {
                                temp = c;
                            }
                        }
                        final Coupon SingleCoupon = temp;
                        if (SingleCoupon == null) {
                            Toast.makeText(context, "Something wrong happened", Toast.LENGTH_LONG).show();
                            return;
                        }
                        question.setText(SingleCoupon.getProblem());
                        builder.setView(questionView);
                        final AlertDialog dialog = builder.create();
                        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                if (AppState.getInstance().listener != null)
                                    AppState.getInstance().listener.addMessage("5" + SingleCoupon.getHash());
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
                        final EditText answerView = questionView.findViewById(R.id.answer);
                        final Button minusButton = questionView.findViewById(R.id.minus_button);
                        minusButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                answerView.setText("-" + answerView.getText().toString());
                            }
                        });
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
                    }
                });
            }else{
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.AlertDialogTheme);
                        builder.setMessage("Sorry someone else solving the coupon's question!");
                        builder.setTitle("Busy!");
                        builder.setPositiveButton(R.string.ok,null);
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                });
            }
            */
        } else if(message.charAt(0)=='6'){
            String[] tokens = message.substring(1).split("\\|");
            final String name = tokens[0];
            final String username = tokens[1];
            final String score = tokens[2];
            AppState.getInstance().user.name = name;
            AppState.getInstance().user.username = username;
            AppState.getInstance().user.score = Integer.parseInt(score);
        }else if(message.charAt(0)=='7'){
            Vector<OnlinePerson> onlinePeople = new Vector<>();
            message = message.substring(1);
            String[] users = message.split("[;]");
            //Log.e("Users",users[0].toString());
            for (String user : users) {
                String[] fields = user.split("\\|");
                if (fields.length != 3) continue;
                final String name = fields[0];
                final String username = fields[1];
                final int score = Integer.parseInt(fields[2]);
                //Log.e("adding",name+","+username+","+score);
                onlinePeople.add(new OnlinePerson(name, username, score));
            }
            AppState.getInstance().onlinePeople.clear();
            for (OnlinePerson p: onlinePeople) {
                AppState.getInstance().onlinePeople.add(p);
            }
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    GridView onlinePeople = context.findViewById(R.id.online_people);
                    if(onlinePeople != null){
                        ((OnlineGridAdapter)onlinePeople.getAdapter()).notifyDataSetChanged();
                    }
                }
            });
        }else if(message.charAt(0)=='8'){
            /*
            String[] tokens = message.substring(1).split("\\|");
            String name = tokens[1];
            String username = tokens[2];
            if(tokens[0].equals("Yes")){
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context,"Username and name changed", Toast.LENGTH_LONG).show();
                    }
                });
                AppState.getInstance().user.username = username;
                AppState.getInstance().user.name = name;

            }else{
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context,"Username taken, name is updated", Toast.LENGTH_LONG).show();
                    }
                });
                AppState.getInstance().user.name = name;
            }
        */
        }

    }
    public void addMessage(String message){
        messageQueue.offer(message);
    }
}


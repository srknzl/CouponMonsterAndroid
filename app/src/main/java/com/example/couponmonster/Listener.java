package com.example.couponmonster;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.couponmonster.Data.Coupon;
import com.example.couponmonster.ui.CouponAdapter;
import com.example.couponmonster.ui.home.HomeFragment;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Date;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Vector;

public class Listener implements Runnable {
    public static Activity context;
    public Socket socket;
    public Scanner in;
    public PrintWriter out;
    public LinkedList<String> messageQueue;

    public Listener(Context context) {
        Listener.context = (Activity) context;
        this.socket = new Socket();
        this.messageQueue = new LinkedList<String>();
    }
    public void close(){
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
            InetAddress inetAddress = InetAddress.getByName("104.248.31.36");
            SocketAddress socketAddress = new InetSocketAddress(inetAddress,22000);
            socket.connect(socketAddress);
            appState.connected = true;
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(),true);
            out.println("Hello");
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ((TextView)context.findViewById(R.id.connectionStatus)).setText(R.string.appbar_connected);
                }
            });
            while(true){
                if(Thread.interrupted()){
                    return;
                }
                try{
                    if(socket.getInputStream().available()>0){
                        String read = in.nextLine();
                        processMessages(read);
                    }

                }catch (NoSuchElementException e){
                    Log.e("No such el","sdasd");
                    e.printStackTrace();
                    appState.connected = false;
                    appState.coupons.clear();
                    appState.listener = null;
                    appState.listenerThread = null;
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            HomeFragment.recyclerView.getAdapter().notifyDataSetChanged();
                        }
                    });
                    appState.onlinePeople = new Vector<>();
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
                    return;
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
            appState.connected = false;
        }
    }
    public void processMessages(String message){
        Log.e("Listener: ", message);
        if(message.charAt(0)=='1'){
            message = message.substring(1);
            String[] fields = message.split("\\|");
            if(fields.length != 5)return;
            final String hash = fields[0];
            final String problem = fields[1];
            final int answer;
            try {
                answer = Integer.parseInt(fields[2]);
            }catch (NumberFormatException e) {
                return;
            }
            final String reward = fields[3];
            final int solveTime;
            try {
                solveTime = Integer.parseInt(fields[4]);
            }catch (NumberFormatException e) {
                return;
            }
            AppState.getInstance().coupons.add(new Coupon(hash,new Date(),problem,reward,answer,solveTime));
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    HomeFragment.recyclerView.getAdapter().notifyItemInserted(AppState.getInstance().coupons.size()-1);
                }
            });
        }else if(message.charAt(0)=='2') {
            final Vector<Coupon> initialCoupons = new Vector<>();
            message = message.substring(1);
            String[] coupons = message.split("[;]");
            for (int i =0;i<coupons.length;i++){
                String[] fields = coupons[i].split("\\|");
                if(fields.length != 5)continue;
                final String hash = fields[0];
                final String problem = fields[1];
                final int answer;
                try {
                    answer = Integer.parseInt(fields[2]);
                }catch (NumberFormatException e) {
                    continue;
                }
                final String reward = fields[3];
                final int solveTime;
                try {
                    solveTime = Integer.parseInt(fields[4]);
                }catch (NumberFormatException e) {
                    continue;
                }
                initialCoupons.add(new Coupon(hash,new Date(),problem,reward,answer,solveTime));
            }
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AppState.getInstance().coupons = initialCoupons;
                    final CouponAdapter couponAdapter = new CouponAdapter( context, AppState.getInstance().coupons);
                    HomeFragment.recyclerView.setAdapter(couponAdapter);
                    Log.e("Enter",AppState.getInstance().coupons.toString());
                }
            });

        }else if(message.charAt(0)=='3'){
            String[] tokens = message.substring(1).split("\\|");
            if(tokens[0].equals("Success")){
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Success!", Toast.LENGTH_LONG).show();
                    }
                });
                AppState.getInstance().removeCoupon(tokens[1]);
            }
        }
    }
    public void addMessage(String message){
        Log.e("Message offer: ",Boolean.toString(messageQueue.offer(message)));
        Log.e("messages: ", messageQueue.toString());
    }
}

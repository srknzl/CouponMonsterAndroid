package com.example.couponmonster;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.example.couponmonster.Data.Coupon;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Date;
import java.util.Scanner;

class Listener implements Runnable {
    static Activity context;
    static String address;
    static int port;
    static Socket socket;
    static Scanner in;
    static PrintWriter out;

    public Listener(Context context,String address,int port) {
        Listener.context = (Activity) context;
        Listener.address = address;
        Listener.port = port;
        Listener.socket = new Socket();
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
            InetAddress inetAddress = InetAddress.getByName(address);
            SocketAddress socketAddress = new InetSocketAddress(inetAddress,port);
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
                while(in.hasNextLine()){
                    processMessages(in.nextLine());
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
            String hash = fields[0];
            String problem = fields[1];
            int answer;
            try {
                answer = Integer.parseInt(fields[2]);
            }catch (NumberFormatException e) {
                return;
            }
            String reward = fields[3];
            int solveTime;
            try {
                solveTime = Integer.parseInt(fields[4]);
            }catch (NumberFormatException e) {
                return;
            }
            AppState.getInstance().coupons.add(new Coupon(hash,new Date(),problem,reward,answer,solveTime));
        }
    }
}

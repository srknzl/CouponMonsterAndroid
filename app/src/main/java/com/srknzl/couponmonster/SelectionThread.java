package com.srknzl.couponmonster;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class SelectionThread implements Runnable {

    private String hash;
    private Socket socket;
    public boolean result = false;
    public SelectionThread(String hash){
        this.hash = hash;
    }

    @Override
    public void run() {
        PrintWriter out;
        Scanner in;
        try{
            socket = new Socket();
            InetAddress inetAddress = InetAddress.getByName("104.248.43.186");
            SocketAddress socketAddress = new InetSocketAddress(inetAddress,6000);
            socket.connect(socketAddress);
            out = new PrintWriter(new BufferedWriter( new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8)),true);
            in = new Scanner(socket.getInputStream(),"UTF-8");
        }catch (IOException e){
            try{
                socket.close();
            }catch (IOException ee){
                ee.printStackTrace();
            }
            e.printStackTrace();
            return;
        }
        out.println("4" + hash);
        while (true){
            out.println("9");

            if(out.checkError() || Thread.interrupted()){
                try{
                    socket.close();
                }catch (IOException ee){
                    ee.printStackTrace();
                }
                return;
            }
            try{
                if(socket.getInputStream().available() > 0 && in.hasNextLine()){
                    try {
                        String message = in.nextLine();
                        if(message.charAt(0)=='4')
                        {
                            String[] tokens = message.substring(1).split("\\|");
                            if(tokens[0].equals("Yes")){
                                result = true;
                                try{
                                    socket.close();
                                }catch (IOException ee){
                                    ee.printStackTrace();
                                }
                                return;
                            }
                            else{
                                try{
                                    socket.close();
                                }catch (IOException ee){
                                    ee.printStackTrace();
                                }
                                return;
                            }
                        }
                    }catch (NoSuchElementException e){
                        try{
                            socket.close();
                        }catch (IOException ee){
                            ee.printStackTrace();
                        }
                        return;
                    }
                }
            }catch (IOException e){
                try{
                    socket.close();
                }catch (IOException ee){
                    ee.printStackTrace();
                }
                e.printStackTrace();
                return;
            }

            try {
                Thread.sleep(100);
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
                try{
                    socket.close();
                }catch (IOException ee){
                    ee.printStackTrace();
                }
                return;
            }
        }




    }

}
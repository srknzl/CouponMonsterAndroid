package com.example.couponmonster;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class UsernameChangeThread implements Runnable {

    private Socket socket;
    public String name;
    public String username;
    public boolean result = false;
    public UsernameChangeThread(String name,String username){
        this.name = name;
        this.username = username;
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
            out = new PrintWriter(socket.getOutputStream(),true);
            in = new Scanner(socket.getInputStream());
        }catch (IOException e){
            try{
                socket.close();
            }catch (IOException ee){
                ee.printStackTrace();
            }
            e.printStackTrace();
            return;
        }
        out.println("8" + this.name + "|" + this.username);
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
                        if(message.charAt(0)=='8')
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
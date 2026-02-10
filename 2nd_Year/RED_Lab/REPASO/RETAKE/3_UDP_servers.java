import java.util.*;
import java.io.*;
import java.net.*;

public class UDP_servers{
    public static void main(String[]args) throws IOException{
        int port = 7770;
        for(int i = 1; i <=2; i++){
            Hilo h = new Hilo(i, port);
            h.start();
        }

        DatagramSocket dss = new DatagramSocket(7770);
        String s = "Hello from port " + port + "\r\n";
        System.out.println(s);
        byte[] buff = s.getBytes();
        DatagramPacket dp = new DatagramPacket(buff, buff.length, InetAddress.getByName("localhost"), 7771);
        dss.send(dp);
        dss.receive(dp);

        String response = new String(dp.getData(), 0, dp.getLength());
        System.out.println(response);

        System.out.println("Bye");

    }

}

class Hilo extends Thread{
    int id;
    int port;
    public Hilo(int i, int p){
        id = i;
        port = p + i;
    }

    public void run(){
        try{
            DatagramSocket dss = new DatagramSocket(port);
            byte[] buffer = new byte[1000];
            DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
            dss.receive(dp);

            String response = "Hello from port: " + port + "\r\n";
            System.out.println(response);
            buffer = response.getBytes();

            if(id <= 1){
                DatagramPacket dp2 = new DatagramPacket(buffer,buffer.length, InetAddress.getByName("localhost"), 7772);
                dss.send(dp2);
                System.out.println("Bye");
            }
            else{
                DatagramPacket dp2 = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("localhost"), 7770);
                dss.send(dp2);
                System.out.println("Bye");
            }
        }
        catch(Exception e){System.out.println(e);}
    }

}

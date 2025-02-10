import java.net.*;
import java.util.*;
import java.io.*;

public class ServerUDP{
    public static void main(String[]args) throws IOException{
        DatagramSocket dss = new DatagramSocket(7777);
        byte[] buff = new byte[256];
        DatagramPacket dp = new DatagramPacket(buff, buff.length);

        while(true){
            dss.receive(dp);
            String response = new String(dp.getData(), 0, dp.getLength());
            System.out.println(response);
            dss.send(dp);
        }

    }
}

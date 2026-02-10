import java.util.*;
import java.io.*;
import java.net.*;

public class ClientUDP{
    public static void main(String[]args) throws IOException{
        DatagramSocket s = new DatagramSocket();
        InetAddress dirIP = InetAddress.getByName("localhost");
        String msg = "Hola, esto es un mensaje";
        byte[] buff = msg.getBytes();

        DatagramPacket dp = new DatagramPacket(buff, buff.length, dirIP, 7777);
        s.send(dp);
        s.receive(dp);

        String response = new String(dp.getData(), 0, dp.getLength());
        System.out.println(response);
        s.close();

    }
}

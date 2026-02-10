import java.net.*;
import java.io.*;
import java.util.*;

public class UDP_server{

    public static void main(String[]args) throws IOException, SocketException{
        DatagramSocket dss = new DatagramSocket(7777);

        byte[] buf = new byte[1000];

        DatagramPacket dp = new DatagramPacket(buf, buf.length);
        String response;

        do{
            dss.receive(dp);
            System.out.println(dp.getPort());
            System.out.println(dp.getAddress());

            response = new String(dp.getData(), 0, dp.getLength());

            System.out.println(dp.getLength());

            buf = response.getBytes();
            dp.setData(buf);
            dp.setLength(1000);

            dss.send(dp);

        }while(!response.equalsIgnoreCase("quit\r\n"));



    }

}


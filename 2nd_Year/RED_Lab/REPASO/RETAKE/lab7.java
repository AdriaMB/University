import java.net.*;
import java.util.*;
import java.io.*;

public class UDP_Client{
    public static void main(String[]args) throws SocketException, IOException{

    //Creation of the Client Socket
        DatagramSocket ds = new DatagramSocket();
    //Creation of the datagram packet to send
        Scanner kbd = new Scanner(System.in);
        String message = kbd.nextLine() + "\r\n";
        byte[] ms = message.getBytes();

        InetAddress lchost = InetAddress.getByName("localhost");
        DatagramPacket dp = new DatagramPacket(ms, ms.length, lchost, 7777);


        String response;
        do{
        //Sending of datagram packet to the server
            ds.send(dp);
        //Receiving of datagram packet from the server and extract info
            ds.receive(dp);
            response = new String(dp.getData(), 0, dp.getLength());
            System.out.println(response);

            message = kbd.nextLine();
            ms = message.getBytes();
            dp.setData(ms);
            dp.setLength(ms.length);


        }while(!response.equalsIgnoreCase("quit\r\n"));


    }

}



/**
class dnslookup
{
    public static void main(String[]args)throws UnknownHostException, IOException

    {
        Scanner kbd = new Scanner(System.in);
        InetAddress addr = InetAddress.getByName("www.upv.es");
        System.out.println(addr);

    }

}
*/

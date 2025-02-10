import java.net.*;
import java.util.*;
import java.io.*;

public class ClienteTCP1{
    public static void main(String[]args){

        String serverHost = "www.upv.es";
        int serverPort = 80;
        try{
            InetAddress dirIP = InetAddress.getByName(serverHost);
            System.out.println(dirIP);
            Socket s = new Socket(dirIP, serverPort);
            System.out.println("Connected");

            System.out.println("Server Port: " + s.getPort());
            System.out.println("Server address: " + s.getInetAddress());
            System.out.println("Local Port: " + s.getLocalPort());
            System.out.println("Local server address: " + s.getLocalAddress());

            s.close();
        }catch(UnknownHostException e){
            System.out.println("Couldn't find the host");
        }catch(IOException e){
            System.out.println("IO exception");
        }

    }

}

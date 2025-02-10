import java.net.*;
import java.util.*;
import java.io.*;

public class ClienteTCP1{
    public static void main(String[]args){

        String serverHost = "www.upv.es";
        int serverPort = 80;
        try{
            Socket s = new Socket(serverHost, serverPort);
            System.out.println("Connected");
            s.close();
        }catch(UnknownHostException e){
            System.out.println("Couldn't find the host");
        }catch(IOException e){
            System.out.println("IO exception");
        }

    }

}

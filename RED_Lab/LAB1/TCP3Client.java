import java.util.*;
import java.net.*;
import java.io.*;

public class TCP1Client{
    public static void main(String[]args)
                                throws UnknownHostException, NoRouteToHostException, IOException{
        Socket s;
        int i = 0;
        try{
          //  while(i < 4){
                s = new Socket("www.upv.es", 80);
                System.out.println(s.getInetAddress());
                System.out.println(s.getLocalAddress());
                System.out.println(s.getPort());
                System.out.println(s.getLocalPort());
                s.close();
           //     i++;
         //  }


        }catch(UnknownHostException e){
            System.out.println("Unknown server name");
            System.out.println(e);
        }catch(NoRouteToHostException e){
            System.out.println("Connection is not possible");
            System.out.println(e);
        }catch(IOException e){
            System.out.println("Nanai");
            System.out.println(e);
        }
    }
}

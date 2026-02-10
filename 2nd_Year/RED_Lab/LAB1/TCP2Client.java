import java.util.*;
import java.net.*;
import java.io.*;

public class TCP1Client{
    public static void main(String[]args)
                                throws UnknownHostException, NoRouteToHostException, IOException{
        Socket s;
        try{
            s = new Socket("www.upv.es", 81);
            System.out.println("Connected again!");
            s.close();
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

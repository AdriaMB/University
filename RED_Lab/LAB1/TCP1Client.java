import java.util.*;
import java.net.*;
import java.io.*;

public class TCP1Client{
    public static void main(String[]args)
                                throws UnknownHostException, IOException{
        Socket s;
        try{
            s = new Socket("www.upv.es", 81);
            System.out.println("Connected");
            s.close();
        }catch(UnknownHostException e){
            System.out.println("Ups, not found");
        }catch(IOException e){
            System.out.println("Nanai");
        }
    }
}

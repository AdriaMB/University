import java.net.*;
import java.io.*;

public class TCP1Client{
    public static void main(String[]args){
        try{
            Socket socket = new Socket("www.upv.es", 80);
            System.out.println("Succeded!");
            socket.close();
        }
        catch(){

        }

    }
}

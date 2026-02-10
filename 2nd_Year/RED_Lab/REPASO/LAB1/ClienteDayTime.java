import java.net.*;
import java.util.*;
import java.io.*;

public class ClienteTCP1{
    public static void main(String[]args){

        try{
            Socket s = new Socket("zoltar.redes.upv.es", 13);
            Scanner in = new Scanner(s.getInputStream());
            System.out.println("Connected");
            System.out.println(in.nextLine());

            s.close();
        }catch(UnknownHostException e)
        {
            System.out.println(e);
        }
        catch(IOException e)
        {
            System.out.println(e);
        }

    }

}

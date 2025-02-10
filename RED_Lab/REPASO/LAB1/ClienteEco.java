import java.net.*;
import java.util.*;
import java.io.*;

public class ClienteEco{
    public static void main(String[]args){

        try{
            Socket s = new Socket("zoltar.redes.upv.es", 7);
            Scanner in = new Scanner(s.getInputStream());
            PrintWriter out = new PrintWriter(s.getOutputStream());

            System.out.println("Connected");
            out.println("Hello world");
            out.flush();
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

import java.net.*;
import java.util.*;
import java.io.*;

public class ClienteSMTP{
    public static void main(String[]args){

        try{
            Socket s = new Socket("smtp.upv.es", 25);
            System.out.println("Connected");

            Scanner in = new Scanner(s.getInputStream());
            System.out.println(in.nextLine());

            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            out.print("HELO GALILEO.redes.upv.es \r\n ");
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

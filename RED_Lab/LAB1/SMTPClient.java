import java.util.*;
import java.io.*;
import java.net.*;

public class SMTPClient{
    public static void main(String[]args)
                            throws UnknownHostException, NoRouteToHostException, IOException{
        try{
            Socket s = new Socket("smtp.upv.es", 25);
            PrintWriter prw = new PrintWriter (s.getOutputStream(), true);
            Scanner input = new Scanner(s.getInputStream());

            prw.println("HELO rdcXX.redes.upv.es");
            prw.flush();
            System.out.println(input.nextLine());

            s.close();
            input.close();
        }catch(UnknownHostException e){
            System.out.println(e);
        }catch(NoRouteToHostException e){
            System.out.println(e);
        }catch(IOException e){
            System.out.println(e);
        }

    }
}

import java.util.*;
import java.net.*;
import java.io.*;

public class ClientHTTP{

    public static void main(String[]args)
                            throws UnknownHostException, NoRouteToHostException, IOException{
        Socket s;
        PrintWriter write;
        Scanner read;
        try{
            s = new Socket("www.upv.es", 80);
            write = new PrintWriter(s.getOutputStream(), true);
            read = new Scanner(s.getInputStream());

            write.println("GET / HTTP/1.0\r\n\r\n");
            write.flush();
            while(read.hasNext()) {System.out.println(read.nextLine());}

            s.close();
            write.close();
            read.close();


        }catch(UnknownHostException e){

            System.out.println(e);

        }catch(NoRouteToHostException e){

            System.out.println(e);

        }catch(IOException e){

            System.out.println(e);

        }
    }
}

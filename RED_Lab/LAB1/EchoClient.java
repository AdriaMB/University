import java.util.*;
import java.io.*;
import java.net.*;

public class EchoClient{
    public static void main(String[]args)
                            throws UnknownHostException, NoRouteToHostException, IOException{
        Socket s = new Socket("zoltar.redes.upv.es", 7);
        PrintWriter prw = new PrintWriter (s.getOutputStream(), true);
        Scanner input = new Scanner(s.getInputStream());

        prw.println("Hello world!!");
       prw.flush();
        System.out.println(input.nextLine());



        s.close();
        input.close();
    }
}

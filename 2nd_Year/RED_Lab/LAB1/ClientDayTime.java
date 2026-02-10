import java.util.*;
import java.io.*;
import java.net.*;

public class ClientDayTime{
    public static void main(String[]args)
                            throws UnknownHostException, NoRouteToHostException, IOException{
        Socket s = new Socket("zoltar.redes.upv.es", 13);
        Scanner input = new Scanner(s.getInputStream());
        System.out.println(input.nextLine());

        s.close();
        input.close();
    }
}


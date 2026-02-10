import java.util.*;
import java.io.*;
import java.net.*;

public class Echo_Server{
    public static void main(String[]args) throws UnknownHostException, IOException{
        ServerSocket ss = new ServerSocket(7777);
        int counter = 0;

        while(true){
            Socket s = ss.accept();
            counter++;
            Service echoServ = new Service(s);
            echoServ.setName("Client"+counter);
            echoServ.start();
        }
    }
}

class Service extends Thread{
    Socket s;
    public Service(Socket sock){
        s = sock;
    }
    public static void main(String[]args){
        try{
            Scanner in = new Scanner(s.getInputStream());
            PrintWriter out = new PrintWriter(s.getOutputStream());
            String receive;
            do{
                receive = in.nextLine();
                System.out.println(receive);
                out.printf(receive);
                out.flush();

            }while(!receive.equalsIgnoreCase("END"));

            s.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}

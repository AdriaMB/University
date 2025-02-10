import java.io.*;
import java.util.*;
import java.net.*;

public class ConcurrentServer{
    public static void main(String[]argv) throws UnknownHostException, IOException{
        int port = 8000;
        int counter = 0;
        ServerSocket server = new ServerSocket(7777);
        while(true){
            Socket client = server.accept();
            counter++;
            Service Cl = new Service(client);
            Cl.setName("Client"+counter);
            Cl.start();
        }

    }
}

class Service extends Thread{
    Socket client;
    public Service(Socket s){
        client = s;
    }

    public void run(){
        try{
            System.out.println(this.getName());
            Scanner in = new Scanner(client.getInputStream());
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);
            String aux = in.nextLine();
            while(!aux.equalsIgnoreCase("End")){
                out.printf(aux + "\r\n");
                aux = in.nextLine();
            }
            client.close();
        }
        catch(Exception e){ System.out.println(e);}

    }

}

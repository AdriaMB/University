import java.util.*;
import java.io.*;
import java.net.*;

public class Client_chat throws UnknownHostException, IOException{
    public static void main(String[]args){
        try{
            Socket s = new Socket("localhost", 7777)        //Socket that connects with the server
            Hilo chatServ = new Hile(s);
            s.start();                                      //Init. of the thread that writes

            Scanner in = new Scanner(s.getInputStream());  //Thread that reads
            String line = in.nextLine();
            while(!line.equalsIgnoreCase("quit")){
                System.out.println(line);
                line = in.nextLine();
            }

        }

    }
}

class Hilo extends Thread{
    Socket client;
    public Server(Socket s){
        Hilo = s;
    }

    public void run(){
       try{
            PrintWriter out = new PrintWriter(client.getOutputStream());
            Scanner kbd = new Scanner(System.in);
            String aux = kbd.nextLine();
            while(!aux.equalsIgnoreCase("quit")){
                out.printf(aux + "\r\n");
                out.flush();
                aux = kbd.nextLine();
            }
            client.close();
       }
       catch(Exception e){System.out.println(e);}
    }

}

import java.net.*;
import java.io.*;
import java.util.*;

class TCP_server{
    public static void main(String[]args){
        try{
            ServerSocket ss = new ServerSocket(8080);
            while(true){
                Socket s = ss.accept();
                System.out.println("Connection established");
                Scanner in = new Scanner(s.getInputStream());
                PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                String aux = in.nextLine();
                String request = aux + "\r\n";
                while(!aux.isEmpty()){
                    aux = in.nextLine();
                    request += aux + "\r\n";
                }
                request += "\r\n";

                System.out.println(request);
                System.out.println("HTTP/1.1 200 OK\r\n" +
                            "Content-Type: text/plain\r\n" +
                            "\r\n");

                out.printf( "HTTP/1.1 200 OK\r\n" +
                            "Content-Type: text/plain\r\n" +
                            "\r\n");
                out.printf(request);
                s.close();
            }

        }
        catch(IOException e){System.out.println(e);}
        catch(Exception e){System.out.println(e);}
    }
}

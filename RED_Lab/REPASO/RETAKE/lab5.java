import java.net.*;
import java.io.*;
import java.util.*;

class TCP_server{
    public static void main(String[]args){
        try{
            ServerSocket ss = new ServerSocket(7777);
            while(true){
                Socket s = ss.accept();
                Scanner in = new Scanner(s.getInputStream());
                PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                System.out.println("A client has connected to the server");
                boolean stay = true;
                while(stay){
                    out.printf( "1) Echo server\r\n" +
                                "2) Get port and IP numbers\r\n" +
                                "0) QUIT\r\n");
                    int option = in.nextInt();

                    switch(option){
                        case 0:
                            stay = false;
                            break;
                        case 1:
                            Scanner echo = new Scanner(s.getInputStream());
                            out.printf(echo.nextLine() + "\r\n");
                            break;
                        case 2:
                            out.printf( "Local Port:" + s.getLocalPort() + "\r\n" +
                                        "Dst.  Port:" + s.getPort() + "\r\n" +
                                        "Local IP:" + s.getLocalAddress() + "\r\n" +
                                        "Dst.  IP:" + s.getInetAddress() + "\r\n");
                            break;
                        default:
                            out.printf("Try again\r\n");
                            break;
                    }

                }

                s.close();
            }
        }
        catch(IOException e){System.out.println(e);}
        catch(Exception e){System.out.println(e);}
    }
}

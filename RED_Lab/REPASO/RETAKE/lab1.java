import java.net.*;
import java.io.*;
import java.util.*;

class TCP1client{

    public static void main(String[]args){
        try{
            Scanner kbd = new Scanner(System.in);
            Scanner user = new Scanner(System.in);
            System.out.println( "1) Connect to the inputed server and port number" + '\n'
                            +   "2) Check the date" + '\n'
                            +   "3) Echo server");
            switch(kbd.nextInt()){
                case 1:
                    System.out.println("Introduce the host");
                    String host = user.nextLine();
                    System.out.println("Introduce the port number");
                    String port = user.nextLine();
                    Methods.tcp_Client(host, port);
                    break;

                case 2:
                    Methods.clientDayTime();
                    break;

                case 3:
                    Methods.echoClient();
                    break;

                default:
                    System.out.println("Input not recognized");
                    break;

            }

        }
        catch(Exception e){

            if(e instanceof UnknownHostException){
                System.out.println("Unknown host");
            }
            else if( e instanceof IOException){
                System.out.println("Wrong Port");
            }
            else{
                System.out.println(e);
            }
        }
    }
}

class Methods{

    public static void tcp_Client(String host, String port) throws UnknownHostException, IOException{
        InetAddress dirIP = InetAddress.getByName(host);
        int portNumber = Integer.parseInt(port);
        Socket s = new Socket(dirIP, portNumber);
        System.out.println("Connected");
        s.close();

    }

    public static void clientDayTime() throws UnknownHostException, IOException{
        Socket s = new Socket("zoltar.redes.upv.es", 13);
        Scanner in = new Scanner(s.getInputStream());
        System.out.println(in.nextLine());
        s.close();
    }

    public static void echoClient()throws UnknownHostException, IOException{
        Socket s = new Socket("zoltar.redes.upv.es", 7);
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        out.printf("Hello World\r\n");

        Scanner in = new Scanner(s.getInputStream());
        System.out.println(in.nextLine());
        s.close();

    }

}

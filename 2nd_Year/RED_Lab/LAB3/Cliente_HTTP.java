import java.net.*;
import java.io.*;

class ClienteHTTP {

    static String nombre_servidor;
    static Socket s;
    static ScannerRedes entrada;
    static PrintWriter salida;


    public static void envia_peticion(String objeto) {
        salida.printf("GET " + objeto + " HTTP/1.1\r\n");
        salida.printf("Host: " + nombre_servidor + "\r\n");
        salida.printf("Connection: Close\r\n");
        salida.printf("\r\n");
        salida.flush();
    }

    public static void lee_linea_estado() {
        System.out.println(">>>>>>>>>>>>>>> LINEA DE ESTADO <<<<<<<<<<<<<<<");
        System.out.printf(entrada.nextLine() + "\r\n");
    }

    public static void lee_cabeceras(){
        System.out.println(">>>>>>>>>>>>>>>    CABECERAS    <<<<<<<<<<<<<<<");
        String line = entrada.nextLine();
        while(!line.isEmpty()){
            System.out.printf(line +"\r\n");
            line = entrada.nextLine();
        }
    }

    public static void lee_cuerpo_texto() {
        System.out.println(">>>>>>>>>>>>>>>   CUERPO TEXTO  <<<<<<<<<<<<<<<");
        while(entrada.hasNext()){
            System.out.println(entrada.nextLine());
        }

    }

    public static void lee_cuerpo_binario(String nombre_fichero) throws IOException{
        System.out.println(">>>>>>>>>>>>>>>  CUERPO BINARIO <<<<<<<<<<<<<<<");
        int charByte = entrada.read();
        int counter = 0;
        FileOutputStream file = new FileOutputStream(nombre_fichero);
        while(charByte != -1){
          //  System.out.printf("%d", charByte);
            file.write(charByte);
            counter++;
            charByte = entrada.read();
        }
        System.out.println(counter);
        file.close();
    }



    public static void main(String args[]) throws Exception {
        nombre_servidor = "zoltar.redes.upv.es";
        s = new Socket(nombre_servidor, 80);
        entrada = new ScannerRedes(s.getInputStream());
        salida = new PrintWriter(s.getOutputStream());

        //envia_peticion("/");
        envia_peticion("/oto1.jpg");
        lee_linea_estado();
        lee_cabeceras();
        //lee_cuerpo_texto();
        lee_cuerpo_binario("oto1.jpg");

        s.close();
    }

}

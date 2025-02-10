import java.net.*;
import java.io.*;
import java.util.*;


class ClienteHTTP {

  static String nombre_servidor;
  static Socket s;
  static ScannerRedes entrada;
  static PrintWriter salida;


  public static void envia_peticion(String objeto) {
        salida.printf("GET " + objeto +" HTTP/1.0" +"\r\n");
        salida.printf("Host: " + nombre_servidor + "\r\n");
        salida.printf("Connection: close \r\n");
        salida.printf("\r\n");
        salida.flush();
  }

  public static void lee_linea_estado() {
    System.out.println(">>>>>>>>>>>>>>> LINEA DE ESTADO <<<<<<<<<<<<<<<");
    System.out.println(entrada.nextLine());
  }

  public static void lee_cabeceras() {
    System.out.println(">>>>>>>>>>>>>>>    CABECERAS    <<<<<<<<<<<<<<<");
    String aux = entrada.nextLine();
    while(!aux.isEmpty()){
        System.out.println(aux);
        aux =entrada.nextLine();
    }

  }

  public static void lee_cuerpo_texto() {
    System.out.println(">>>>>>>>>>>>>>>   CUERPO TEXTO  <<<<<<<<<<<<<<<");
    while(entrada.hasNext()){
        System.out.println(entrada.nextLine());
    }
  }

  public static void lee_cuerpo_binario(String nombre_fichero) {
    System.out.println(">>>>>>>>>>>>>>>  CUERPO BINARIO <<<<<<<<<<<<<<<");
    try{
        FileOutputStream file = new FileOutputStream(nombre_fichero);
        int character = entrada.read();
        int counter = 0;
        while(character != -1){
            //System.out.println(character);
            file.write(character);
            counter++;
            character = entrada.read();
        }
        System.out.println("The length is: " + counter);

    }catch(IOException e){
        System.out.println(e);
    }catch(Exception e){
        System.out.println(e);
    }
  }



  public static void main(String args[]) throws Exception {
    nombre_servidor = "zoltar.redes.upv.es";
    s = new Socket(nombre_servidor, 80);
    entrada = new ScannerRedes(s.getInputStream());
    salida = new PrintWriter(s.getOutputStream());

    envia_peticion("/");
    //envia_peticion("/oto2.jpg");
    lee_linea_estado();
    lee_cabeceras();
    lee_cuerpo_texto();
    //lee_cuerpo_binario("oto2.jpg");

    s.close();


    s = new Socket(nombre_servidor, 80);
    entrada = new ScannerRedes(s.getInputStream());
    salida = new PrintWriter(s.getOutputStream());


    //envia_peticion("/");
    envia_peticion("/oto1.jpg");
    lee_linea_estado();
    lee_cabeceras();
  //  lee_cuerpo_texto();
    lee_cuerpo_binario("oto1.jpg");

    s.close();


    s = new Socket(nombre_servidor, 80);
    entrada = new ScannerRedes(s.getInputStream());
    salida = new PrintWriter(s.getOutputStream());


    //envia_peticion("/");
    envia_peticion("/oto2.jpg");
    lee_linea_estado();
    lee_cabeceras();
  //  lee_cuerpo_texto();
    lee_cuerpo_binario("oto2.jpg");

    s.close();
  }

}













class ScannerRedes {

   InputStream Stream;
   int ultimaLectura;
   boolean usarUltimaLectura = false;



   public ScannerRedes(InputStream istr) {
      Stream = istr;
   }

	public int read() throws IOException {  return Stream.read(); }


   public String nextLine() {
     String cadena = "";
     int byte1;
     try{
       if (usarUltimaLectura == true)
          {byte1= ultimaLectura;
           usarUltimaLectura = false;
       }
       else {
           byte1 = Stream.read();
           if (byte1 == -1) return cadena;
       }

       int byte2 = Stream.read();
       if (byte2 == -1) {
           // byte1 puede ser /n o ser diferente de /n, pero en ambos casos
           // no se ha completado una nueva linea y por tanto devolvermos
           //una cadena vacia
           return cadena;
         }

       while (byte2 != 10) { //el codigo 10 es /n
         cadena = cadena + Character.toString((char)byte1);
         byte1 = byte2;
         byte2 = Stream.read();
         if (byte2 == -1) {return "";} //no se ha completado una linea
       }
       if (byte1 != 13) {cadena = cadena + Character.toString((char)byte1);} //el codigo 13 es /r
     }
     catch (IOException e) {System.out.println("Error procesando la linea");}
     return cadena;
   }



   public boolean hasNext() {
     if (usarUltimaLectura == true) return true;
     try {
       ultimaLectura = Stream.read();
     }
     catch (IOException e) {System.out.println("Error en hasNext");}
     if (ultimaLectura != -1)
        {usarUltimaLectura = true;
         return true;
        }
     return false;
   }


}


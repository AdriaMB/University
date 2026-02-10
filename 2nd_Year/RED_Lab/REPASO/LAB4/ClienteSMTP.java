import java.net.*;
import java.io.*;
import java.util.Scanner;

public class ClienteSMTP {

        static void error(String cadena) {
		System.out.println(cadena);
		System.exit(0);
	}

	public static void main(String args[]) {
	try{
		Socket s=new Socket("serveis-rdc.redes.upv.es", 25);
		System.out.println("Conectado al servidor SMTP de serveis-rdc");
		PrintWriter salida = new PrintWriter(s.getOutputStream());
		Scanner entrada=new Scanner(s.getInputStream());
		String respuesta = entrada.nextLine();
		System.out.println(respuesta);
		if (!respuesta.startsWith("200")) {error(respuesta);}

		salida.print("HELO [ip_address]\r\n");
		salida.flush();
		respuesta = entrada.nextLine();
		System.out.println(respuesta);
		if (!respuesta.startsWith("250")) {error(respuesta);}

		salida.print("MAIL FROM: <amarbay@upv.edu.es>\r\n");
		salida.flush();
		respuesta = entrada.nextLine();
		System.out.println(respuesta);
		if (!respuesta.startsWith("250")) {error(respuesta);}

		salida.print("RCPT TO: <amarbay@upv.edu.es>\r\n");
		salida.flush();
		respuesta = entrada.nextLine();
		System.out.println(respuesta);
		if (!respuesta.startsWith("250")) {error(respuesta);}

		salida.print("DATA\r\n");
		salida.flush();
		respuesta = entrada.nextLine();
		System.out.println(respuesta);
		if (!respuesta.startsWith("354")) {error(respuesta);}

		// **completar**
		// Aqui van varios print con todo el correo
		// electronico incluidas las cabeceras
		salida.print("From: amarbay@upv.edu.es\r\n");
		salida.print("To: amarbay@upv.edu.es\r\n");
		salida.print("Subject: \r\n");
		salida.print("Guten Tag, Herr Marín Bayarri\r\n");
		salida.print("Auf Wiedersehen \r\n");
		salida.print("\r\n");
        salida.print(".\r\n");
		salida.flush();
		respuesta = entrada.nextLine();
		System.out.println(respuesta);
		if (!respuesta.startsWith("250")) {error(respuesta);}

		salida.print("QUIT\r\n");
		salida.flush();
		respuesta = entrada.nextLine();
		System.out.println(respuesta);
		if (!respuesta.startsWith("500")) {error(respuesta);}

		s.close();
		System.out.println("Desconectado");

	} catch (UnknownHostException e) {
		System.out.println("Host desconocido");
		System.out.println(e);
	} catch (IOException e) {
		System.out.println("No se puede conectar");
		System.out.println(e);
	}
	}
}

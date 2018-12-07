package Chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Servidor {
	
	private static int valor = 0;

	public static void main(String[] args) throws IOException{
		
		ServerSocket servidor = null;
		try {
			System.out.println("startando o servidor");
			servidor = new ServerSocket(9999);
			System.out.println("servidor startado");
			
			while(true){
				Socket cliente = servidor.accept();
				new GerenciadorDeClientes(cliente);
			}
			
		} catch (IOException e) {
			
			try {
				if(servidor != null)
					servidor.close();
			} catch (IOException e1) {}
			
			System.err.println("a porta está ocupada ou servidor foi fechado");
			e.printStackTrace();
		}
	}//main
	
	public static void atualizaDado1(int valor2) {
		
		valor = valor2;
		System.out.println(valor);
	
	}
	

}
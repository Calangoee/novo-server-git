package Chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class ClienteSocket {
	
	private static volatile boolean flagResponder = true;

	public static void main(String[] args) {
		
		BufferedReader leitor;
		PrintWriter escritor;
		
		try {
			final Socket cliente = new Socket("127.0.0.1", 9999);
			
			leitor = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
			escritor = new PrintWriter(cliente.getOutputStream(), true);
			BufferedReader leitorTerminal = new BufferedReader(new InputStreamReader(System.in));
			
			// lendo mensagens do servidor
			new Thread(){
				@Override
				public void run() {
					try {
						
						while(true){
							String mensagem = leitor.readLine();
							if(mensagem != null || mensagem.length() != 0) {   //evita mensagens nulas com erro  mensagem.isEmpty()
								if(mensagem.equalsIgnoreCase("::check") && flagResponder) {
									escritor.println("checkado");
								}
								System.out.println("server: " + mensagem);
							}
						}
						
					} catch (IOException e) {
						System.out.println("impossivel ler a mensagem do servidor");
						e.printStackTrace();
					}
				}
			}.start();
			
			// escrenvendo para o servidor
			
			
			String mensagemTerminal = "";
			while( true ){
				mensagemTerminal = leitorTerminal.readLine();
//				if(mensagemTerminal == null || mensagemTerminal.length() == 0){
//					continue;
//				}
				if(mensagemTerminal.equalsIgnoreCase("::SAIR")){
					System.exit(0);
				}else if(mensagemTerminal.equalsIgnoreCase("::NRESP")){
					flagResponder = false;
				}else{
					escritor.println(mensagemTerminal);
				}
			}
			
		} catch (UnknownHostException e) {
			System.out.println("o endereço passado é inválido");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("o servidor pode estar fora ar");
			e.printStackTrace();
		}
	}
}
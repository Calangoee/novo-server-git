package Chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Login {

	private BufferedReader leitor;
	private PrintWriter escritor;
	private long t1, t2;
	private static volatile boolean flagLogin = false;
	private boolean finaliza = false;
	private String retorno;

	public Login(BufferedReader leitor, PrintWriter escritor) {   //construtor
		this.leitor = leitor;
		this.escritor = escritor;
	}

	public void main() {

		//envia para o cliente uma chave

		t1 = System.currentTimeMillis();

		this.escritor.println("123456");

		while(!finaliza) {

			//chamar thread de leitura do socket
			new Thread(){
				@Override
				public void run() {
					try {
						while(true){
							String mensagem = leitor.readLine();
							if(mensagem == null || mensagem.isEmpty())
								continue;

							System.out.println("o servidor disse: " + mensagem);
						}

					} catch (IOException e) {
						System.out.println("impossivel ler a mensagem do servidor");
						e.printStackTrace();
					}
				}
			}.start();

			t2 = System.currentTimeMillis();
		}


	}

	public void attRetorno(String msg){
		retorno = msg;

		if(retorno.equalsIgnoreCase("654321"))

			flagLogin = true;
	}

}

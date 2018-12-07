package Chat;

import java.io.BufferedReader;
import java.io.IOException;

public class ServerRecebedor implements Runnable{

	BufferedReader leitor;
	String msg;

	public ServerRecebedor(BufferedReader leitor) {   //construtor
		this.leitor = leitor;
		Thread thread1 = new Thread(this);
		thread1.start();
	}

	@Override
	public void run() {

		try {
			while(true){
				msg = leitor.readLine();
				GerenciadorDeClientes.indicaRetorno(msg);
			}//while
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

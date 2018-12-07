package Chat;

import java.io.BufferedReader;

public class MyTimer implements Runnable {

	boolean encerrar = false;
	
	public MyTimer() {   //construtor
		Thread thread1 = new Thread(this);
		thread1.start();
	}
	@Override
	public void run() {
		try {
			Thread.sleep(10000);
			GerenciadorDeClientes.attFlagTimer();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

package Chat;

import java.io.BufferedReader;

public class MyTimer_02 implements Runnable {

	boolean encerrar = false;
	long tempo;
	
	public MyTimer_02(long tempo) {   //construtor
		this.tempo = tempo;
		Thread thread1 = new Thread(this);
		thread1.start();
	}
	@Override
	public void run() {
		try {
			Thread.sleep(tempo);
			//GerenciadorDeClientes.attFlagTimer();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
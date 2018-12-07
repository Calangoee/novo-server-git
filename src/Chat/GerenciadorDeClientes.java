package Chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class GerenciadorDeClientes extends Thread {

	private Socket cliente;
	private String nomeCliente;
	private BufferedReader leitor;
	private PrintWriter escritor;
	//private static final Map<String,GerenciadorDeClientes> clientes = new HashMap<String,GerenciadorDeClientes>();
	private static volatile  boolean respostaCliente = false, flagTimer = false;
	private boolean clienteNotificado = false;
	private boolean finalizado = false;
	private long tempo1, tempo2;
	private String msg;
	MyTimer t1;

	public GerenciadorDeClientes(Socket cliente) {
		this.cliente = cliente;
		start();
	}

	@Override
	public void run() {

		try {
			leitor = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
			escritor = new PrintWriter(cliente.getOutputStream(), true);

			String dado = ".";
			int valor2;

			tempo1 = System.currentTimeMillis();
			ServerRecebedor r1 = new ServerRecebedor (leitor);   //inicia o leitor
			t1 = new MyTimer();   //inicia um timer de 30 segundos
			System.out.println("novo cliente");

			while(!finalizado){

				if(flagTimer && !respostaCliente){   //quando nao tem resposta do 
					if(!clienteNotificado) {   //nao teve nenhuma resposta e o cliente ainda nao foi notificado
						clienteNotificado = true;
						escritor.println("::check");
						flagTimer = false;
						t1 = new MyTimer();
						System.out.println(".timer com check");
					}else {   //cliente ja foi notificado da ausencia de msg
						//encerra a cominucacao
						this.cliente.shutdownInput();
						this.cliente.shutdownOutput();
						this.cliente.close();
						finalizado = true;
						System.out.println("socket finalizado");
					}
				}

				if(flagTimer && respostaCliente){   //quando nao tem resposta do 
					System.out.println(".timer com resp");
					respostaCliente = false;
					flagTimer = false;
					clienteNotificado = false;
					t1 = new MyTimer();
				}

			}//while
		} catch (IOException e) {
			System.err.println("o cliente fechou a conexao");
			e.printStackTrace();

		}
	}

	public static void indicaRetorno(String msg) {
		respostaCliente = true;
		System.out.println(msg);
	}

	public static void attFlagTimer() {
		flagTimer = true;
	}

}//class

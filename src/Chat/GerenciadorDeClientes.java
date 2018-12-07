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
	private static volatile  boolean respostaCliente = false;
	private static volatile  boolean flagTimer = false;
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
			ServerRecebedor r1 = new ServerRecebedor (leitor);
			t1 = new MyTimer();
			System.out.println("novo cliente");

			while(true){

				if(flagTimer && !respostaCliente){   //quando nao tem resposta do 
					System.out.println("timer");
					flagTimer = false;
					t1 = new MyTimer();
				}

				if(flagTimer && respostaCliente){   //quando nao tem resposta do 
					System.out.println("resp");
					respostaCliente = false;
					flagTimer = false;
					t1 = new MyTimer();
				}

				//				if(!flagTimer){
				//					System.out.println("no");
				//					flagTimer = false;
				//				}

				//				if(!respostaCliente){
				//					respostaCliente = true;
				//				}//if
				//
				//				Thread t = new Thread(new Runnable() {
				//					@Override
				//					public void run() {
				//						msg = leitor.readLine();
				//					}
				//				}, "t");
				//				t.start();

				//if(temo)
				//				msg = leitor.readLine();
				//				if(msg.equalsIgnoreCase("::sair")){
				//					this.cliente.close();
				//				}else if(msg.startsWith("::D") && msg.length() > 3){
				//					dado = msg.substring(3, msg.length());
				//					valor2 = Integer.parseInt(dado);
				//					Servidor.atualizaDado1(valor2);
				//					//System.out.println(dado);
				//				}else{
				//					//System.out.println(msg);
				//				}
				//	


				//		        new Thread(new Runnable() {
				//		            @Override
				//		            public void run() {
				//		                for (int i = 0; i < QUANTIDADE; i++) {
				//		                    boolean novo = VALORES.add(++varCompartilhada);
				//		                    if (!novo) {
				//		                        System.out.println("Já existe: " + varCompartilhada);
				//		                    }
				//		                }
				//		            }
				//		        }).start();


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

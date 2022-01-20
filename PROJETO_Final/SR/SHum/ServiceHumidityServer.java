import java.util.*;
import java.net.*;
import java.io.*;

public class ServiceHumidityServer {
	static int DEFAULT_PORT=2000;

	//private void connect()
	
	public static void main(String[] args) {
		int port=DEFAULT_PORT;
		Sources source = new Sources();
		source.loadData();
		ServerSocket servidor = null; 


		String myIPadress="127.0.0.1";

		
		String ipServicoT="127.0.0.1";
		int portoST=3122;
		int indice=1;
		while(indice==1){
		try {
			InetAddress serverAddress = InetAddress.getByName(ipServicoT);

			Socket ligacao = null;

			ligacao = new Socket(serverAddress, portoST);

			BufferedReader in = new BufferedReader(new InputStreamReader(ligacao.getInputStream()));

			PrintWriter out = new PrintWriter(ligacao.getOutputStream(), true);

			String request = "post" + " " + "Humidity" + " " + "SocketTCP"+ " " + myIPadress + " " + port + " ";

			out.println(request);

			ligacao.close();
			indice=0;
						//System.out.println("Terminou a ligacao!");
		} catch (IOException e) {
			System.out.println("Erro ao comunicar com o servidor: " + e);
		}}

	
		try	{ 
			servidor = new ServerSocket(port);
		} catch (Exception e) { 
			System.err.println("erro ao criar socket servidor...");
			e.printStackTrace();
			System.exit(-1);
		}
				
		while(true) {
			try {
				
				Socket ligacao = servidor.accept();
				
				GetServicesRequestHandler t = new GetServicesRequestHandler(ligacao, source);
				t.start();
				
			} catch (IOException e) {
				System.out.println("Erro na execucao do servidor: "+e);
				System.exit(1);
			}
		}
	}
}

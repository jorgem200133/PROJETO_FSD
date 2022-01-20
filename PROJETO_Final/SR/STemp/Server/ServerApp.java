import java.util.*;
import java.net.*;
import java.io.*;

public class ServerApp {
	
	public static void main(String[] args) {
		
		String myIPadress="127.0.0.1";
		String nameSR="/TemperatureService";
		int port=1099;
		
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

			String request = "post" + " " + "Temperature" + " " + "JavaRMI"+ " " + myIPadress + " " + port + " "+ nameSR +" ";

			out.println(request);

			ligacao.close();
			indice=0;
						//System.out.println("Terminou a ligacao!");
		} catch (IOException e) {
			System.out.println("Erro ao comunicar com o servidor: " + e);
		}}

		ServicesServer serv = new ServicesServer();
		serv.createServices();
	}
}

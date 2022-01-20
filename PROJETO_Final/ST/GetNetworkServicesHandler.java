import java.net.*;
import java.io.*;
import java.util.*;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.Instant;

public class GetNetworkServicesHandler extends Thread {
	Socket ligacao;
	NetworkServices services;
	BufferedReader in;
	PrintWriter out;


	static final int ST_PORTO = 3122;
	static final String ST_HOST = "127.0.0.1"; // IP e PORTO do Serviço de Ticketing
	
	//private static final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'SSSZZZZ");
	
	public GetNetworkServicesHandler(Socket ligacao, NetworkServices services) {
		this.ligacao = ligacao;
		this.services = services;
		try {
			this.in = new BufferedReader(new InputStreamReader(ligacao.getInputStream()));

			this.out = new PrintWriter(ligacao.getOutputStream());
		} catch (IOException e) {
			System.out.println("Erro na execucao do servidor: " + e);
			System.exit(1);
		}
	}

	String readFile(String fileName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
	
			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			return sb.toString();
		} finally {
			br.close();
		}
	}
   

	public void run() {
		try {
			System.out.println("Aceitou ligacao de cliente no endereco " + ligacao.getInetAddress() + " na porta "
					+ ligacao.getPort());
			//String response;
			//String response2;
			int length = 0;
			String msg = in.readLine();
			System.out.println("Request=" + msg);

			StringTokenizer tokens = new StringTokenizer(msg);
			String metodo = tokens.nextToken();
			if (metodo.equals("get")) {
				String identificador = tokens.nextToken();
				String hashSI = tokens.nextToken();
				String tecSR = tokens.nextToken();
				//response = "101\n"; 
				try{
            	MessageDigest md = MessageDigest.getInstance("MD5");

            	byte[] messageDigest = md.digest(identificador.getBytes());

	            BigInteger no = new BigInteger(1, messageDigest);

    	        String hashtext = no.toString(16);

        	    while (hashtext.length() < 32) {
        	        hashtext = "0" + hashtext;
        	    }
        	    if(hashtext.equals(hashSI)) {
					if (tecSR.equals("1")){
						out.println(readFile("JavaRMI.txt"));
					}
					else if (tecSR.equals("2")){
						out.println(readFile("SocketTCP.txt"));
					}
				}
        	    else {out.println("Não foi possivel Conectar");}
        	    

        		}
            	catch (NoSuchAlgorithmException e) {
       				 System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
    			}

			} 
			else{
				if (metodo.equals("post")){
					String descricao = tokens.nextToken();
					String tecnologia = tokens.nextToken();
					String ipSR = tokens.nextToken();
					String portoSR = tokens.nextToken();
					Timestamp timestamp = new Timestamp(System.currentTimeMillis());
					Instant instant = timestamp.toInstant();
					if (tecnologia.equals("SocketTCP")){
						PrintWriter printsocket;
						String registo=readFile("SocketTCP.txt");
						try {
    						printsocket = new PrintWriter("SocketTCP.txt");
    						
    						printsocket.print(descricao + " " + ipSR +"," + portoSR + " " + ipSR + " " + portoSR+ " "+ instant);
    						printsocket.print(registo);
    						printsocket.close();
							} catch (FileNotFoundException e) {
    							System.err.println("File doesn't exist");
    							e.printStackTrace();
						}
					}
					if (tecnologia.equals("JavaRMI")){
						String name = tokens.nextToken();
						PrintWriter printjavarmi;
						String registo=readFile("JavaRMI.txt");
						try {
    						printjavarmi = new PrintWriter("JavaRMI.txt");
    						
    						printjavarmi.print(descricao + " " + ipSR +","+ portoSR +"," + name + " " + ipSR + " " + portoSR + " " + name + " "+ instant);
    						printjavarmi.print(registo);
    						printjavarmi.close();
							} catch (FileNotFoundException e) {
    							System.err.println("File doesn't exist");
    							e.printStackTrace();
						}
					}
				}
			}
				

			out.flush();
			in.close();
			out.close();
			ligacao.close();
		} catch (IOException e) {
			System.out.println("Erro na execucao do servidor: " + e);
			System.exit(1);
		}
	}
}

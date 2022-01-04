import java.net.*;
import java.io.*;
import java.util.*;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class GetPresencesRequestHandler extends Thread {
	Socket ligacao;
	Presences presences;
	BufferedReader in;
	PrintWriter out;


	static final int ST_PORTO = 3122;
	static final String ST_HOST = "127.0.0.1"; // IP e PORTO do Serviço de Ticketing

	public GetPresencesRequestHandler(Socket ligacao, Presences presences) {
		this.ligacao = ligacao;
		this.presences = presences;
		try {
			this.in = new BufferedReader(new InputStreamReader(ligacao.getInputStream()));

			this.out = new PrintWriter(ligacao.getOutputStream());
		} catch (IOException e) {
			System.out.println("Erro na execucao do servidor: " + e);
			System.exit(1);
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
        	    if(hashtext.equals(hashSI)) {out.println("Conexao efetuada co Sucesso!");}
        	    else {out.println("Não foi possivel Conectar");}

        		}
            	catch (NoSuchAlgorithmException e) {
       				 System.err.println("I'm sorry, but MD5 is not a valid message digest algorithm");
    			}

			} else
				out.println("201;method not found");

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

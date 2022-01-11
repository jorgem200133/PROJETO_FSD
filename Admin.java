
import java.util.*;
import java.net.*;
import java.io.*;

public class Admin {



	static final int ST_PORTO = 3122;
	static final String ST_HOST = "127.0.0.1"; //IP e PORTO do Serviço de Identificação

	public static int menu() {

   		int selection;
        Scanner input = new Scanner(System.in);

        /***************************************************/
        System.out.println("\n************************************************************");
        System.out.println("*	Menu Administrador                                            ");
        System.out.println("*	----------------------------------------------------    ");
        System.out.println("*	1 - Registar Serviço de Rede SocketTCP  ");
        System.out.println("*	2 - Registar Serviço de Rede RMI  ");
        System.out.println("*	3 - Quit                                                ");
   		System.out.print("?-> ");
        selection = input.nextInt();
        return selection;    
    }

	public static void main(String[] args) throws IOException {

		int choice;
		do {

    		
    		String servidor = ST_HOST;
			int porto = ST_PORTO;
					

			InetAddress serverAddress = InetAddress.getByName(servidor);

			Socket ligacao = null;

			ligacao = new Socket(serverAddress, porto);
					
			System.out.println("+*********************************************************************************+");
			System.out.println("*	                                          ");
			System.out.println("*	BEM VINDO AO SISTEMA DE TICKETING (ADMINISTRADOR)     ");
			System.out.println("*	                                          ");
			
			choice = menu();
   			switch (choice){
    		    case 1:{
    		    	System.out.print("\nIntroduza os seguintes dados acerca do Servico ed Rede a introduzir\nTecnologia: SocketTCP\n");
					System.out.print("->Descricao: ");
    		    	Scanner scanner = new Scanner(System.in);
    		    	String descricao = scanner.nextLine();
    		    	String tecnologia = "SocketTCP";
    		    	System.out.print(" * Ip: ");
    		    	String ipSR = scanner.nextLine();
    		  		System.out.print(" * Porto: ");
    		    	String portoSR = scanner.nextLine();


					try {
						BufferedReader in = new BufferedReader(new InputStreamReader(ligacao.getInputStream()));

						PrintWriter out = new PrintWriter(ligacao.getOutputStream(), true);

						String request = "post" + " " + descricao + " " + tecnologia+ " " + ipSR + " " + portoSR + " ";

						out.println(request);

						ligacao.close();

						//System.out.println("Terminou a ligacao!");
					} catch (IOException e) {
						System.out.println("Erro ao comunicar com o servidor: " + e);
						System.exit(1);
					}
					break;
    		    }
            		
        		case 2:{
    				System.out.print("\nIntroduza os seguintes dados acerca do Servico ed Rede a introduzir\nTecnologia: SocketTCP\n");
					System.out.print("->Descricao: ");
    		    	Scanner scanner = new Scanner(System.in);
    		    	String descricao = scanner.nextLine();
    		    	String tecnologia = "JavaRMI";
    		    	System.out.print(" * Ip: ");
    		    	String ipSR = scanner.nextLine();
    		  		System.out.print(" * Porto: ");
    		    	String portoSR = scanner.nextLine();
    		    	System.out.print(" * Nome: ");
    		    	String nameSR = scanner.nextLine();


					try {
						BufferedReader in = new BufferedReader(new InputStreamReader(ligacao.getInputStream()));

						PrintWriter out = new PrintWriter(ligacao.getOutputStream(), true);

						String request = "post" + " " + descricao  + " " + tecnologia + " " + ipSR+ " " + portoSR+ " " + nameSR+ " ";

						out.println(request);

						ligacao.close();

						//System.out.println("Terminou a ligacao!");
					} catch (IOException e) {
						System.out.println("Erro ao comunicar com o servidor: " + e);
						System.exit(1);
					}
					break;
    		    }

            		
        		case 3:
            		// Perform "decrypt number" case.
        		    break;
        		default:
        		    // The user input an unexpected choice.
    		}

		} while(choice != 3);


	}
}

import java.util.*;
import java.net.*;
import java.io.*;

public class Client {



	static final int SI_PORTO = 2000; 
	static final String SI_HOST = "127.0.0.1"; //IP e PORTO do Serviço de Identificação

	public static int menu() {

   		int selection;
        Scanner input = new Scanner(System.in);

        /***************************************************/
        System.out.println("\n************************************************************");
        System.out.println("*	Menu Cliente                                            ");
        System.out.println("*	----------------------------------------------------    ");
        System.out.println("*	1 - Obter Informacao do Servico de Tickting disponivel  ");
        System.out.println("*	2 - Aceder ao servico de Ticketing         ");
        System.out.println("*	3 - Quit                                                ");
   		System.out.print("?-> ");
        selection = input.nextInt();
        return selection;    
    }
    public static int menu2(int tecn) {
    	String tecnologia;
    	if(tecn==1) tecnologia = "JavaRMI";
    	else tecnologia = "SocketTCP";

   		int selection;
        Scanner input = new Scanner(System.in);

        /***************************************************/
        System.out.println("\n************************************************************");
        System.out.println("*	Menu ST                                                 ");
        System.out.println("*	----------------------------------------------------    ");
        System.out.println("*	1 - Consultar Servicos de rede " + tecnologia + " ");
        System.out.println("*	2 - Quit                                                ");
   		System.out.print("ST-> ");
        selection = input.nextInt();
        return selection;    
    }

	public static void main(String[] args) throws IOException {

		int choice;
		do {

    		choice = menu();

   			switch (choice){
    		    case 1:{
    		    	String servidor = SI_HOST;
					int porto = SI_PORTO;
					
					System.out.println("+*************************************************+");
					System.out.println("*	                                          ");
					System.out.println("*	BEM VINDO AO SISTEMA DE IDENTIFICACAO     ");
					System.out.println("*	                                          ");

    		    	System.out.print(" *Introduza o seu Ip: ");
    		    	Scanner scanner = new Scanner(System.in);
    		    	String ip = scanner.nextLine();
    		    	System.out.print(" * Introduza um Identificador Unico seu (Ex.: nº CC, NIF,...): ");
    		    	String id = scanner.nextLine();

					InetAddress serverAddress = InetAddress.getByName(servidor);

					Socket ligacao = null;

					ligacao = new Socket(serverAddress, porto);


					try {
						BufferedReader in = new BufferedReader(new InputStreamReader(ligacao.getInputStream()));

						PrintWriter out = new PrintWriter(ligacao.getOutputStream(), true);

						String request = "get" + " " + ip + " " + id;

						out.println(request);

						String msg;

						int length=0;
						while((msg = in.readLine())!= null){
						if(length == 0) {System.out.println("\n *		Chave de Acesso: " +msg +";\n");}
						if(length == 1) {System.out.println(" 	*		Ip do Servico de Tickting: " +msg +";\n");}
						else {if (length ==2) {System.out.println("*		Porto do Servico de Ticketing: " + msg +";\n");}
						}
						length+=1;
  						  }

						ligacao.close();

						//System.out.println("Terminou a ligacao!");
					} catch (IOException e) {
						System.out.println("Erro ao comunicar com o servidor: " + e);
						System.exit(1);
					}
					break;
    		    }
            		
        		case 2:
        		    {
        		    System.out.println("		E importante referir que para se conectar ao\nServico de Tickting e necessario passar pelo passo 1!\n");	
        		    System.out.print("Pretende continuar (S/N:) ");
        		    Scanner scanner = new Scanner(System.in);
        		    char r = scanner.next().charAt(0);
        		    if(r=='S' || r=='s'){
        		    	System.out.println("K! Introduz os seguintes dados para iniciar conexão:\n");
        		    	System.out.print("		->Ip do Servico de Ticketing: ");
        		    	Scanner scanner1 = new Scanner(System.in);
    		    		String ipST = scanner1.nextLine();
    		    		System.out.print("		->Porto do Serviço de Ticketing: ");
    		    		int portoST = scanner1.nextInt();

    		    		InetAddress serverAddress = InetAddress.getByName(ipST);

						Socket ligacao = null;

						ligacao = new Socket(serverAddress, portoST);
						System.out.println("+*******************************************+");
						System.out.println("*	                                    ");
						System.out.println("*	BEM VINDO AO SISTEMA DE TICKTING    ");
						System.out.println("*	                                    ");

						Scanner scanner2 = new Scanner(System.in);
						System.out.println("\nPara iniciar, introduza os seguintes dados:");
						System.out.print(" *		Chave gerada no Servico de Identificacao: ");
    		    		String hashC = scanner2.nextLine();	
    		    		System.out.print(" *		Identificador Unico utilizado: ");
    		    		String idC = scanner2.nextLine();
						System.out.print(" *		Tipo de tecnologia dos SR que pretende consultar (1-RMI ou 2-SocketTCP): ");
    		    		int tcnSR = scanner2.nextInt();	
    		    		do{

    		    		choice = menu2(tcnSR);

    		    		switch(choice){
    		    			case 1:
    		    			{
    		    				try {
									BufferedReader in = new BufferedReader(new InputStreamReader(ligacao.getInputStream()));

									PrintWriter out = new PrintWriter(ligacao.getOutputStream(), true);

									String request = "get" + " " + idC + " " + hashC +" " + tcnSR ;

									out.println(request);

									String msg=in.readLine();
									System.out.println(msg);

						//System.out.println("Terminou a ligacao!");
								} catch (IOException e) {
									System.out.println("Erro ao comunicar com o servidor: " + e);
									System.exit(1);
								}	    		

    		    			}
    		    			case 2: break;
    		    			default: break;
    		    		}

						
						}while(choice!=2);

						ligacao.close();
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
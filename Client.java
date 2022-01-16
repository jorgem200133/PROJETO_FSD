
import java.util.*;
import java.net.*;
import java.io.*;
//import java.util.StringTokenizer; 
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
//import java.util.Date;
import java.time.Instant;

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
        System.out.println("*	1 - Consultar Servicos de rede " + tecnologia + " disponiveis");
        System.out.println("*	2 - Aceder a um Servico de rede " + tecnologia + " ");
        System.out.println("*	3 - Quit                                                ");
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
        		    	System.out.println("OK! Introduz os seguintes dados para iniciar conexão:\n");
        		    	System.out.print(" *		Ip do Servico de Ticketing: ");
        		    	Scanner scanner1 = new Scanner(System.in);
    		    		String ipST = scanner1.nextLine();
    		    		System.out.print(" *		Porto do Serviço de Ticketing: ");
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
						System.out.print(" *		Tipo de tecnologia dos SR que pretende consultar (1-JavaRMI ou 2-SocketTCP): ");
    		    		int tcnSR = scanner2.nextInt();	
    		    		
    		    		do{
    		    		String msg="";
    		    		choice = menu2(tcnSR);
    		    		int accessKeylength=0;
    		    		String ipSR=null;
    		    		String nomeSR=null;
    		    		String chaveAcesso=null;
    		    		int portoSR=0;
    		    		String portoSRS;
    		    		String descricao=null;
    		    		String timestamp=null;

    		    		switch(choice){
    		    			case 1:
    		    			{
    		    				try {
									BufferedReader in = new BufferedReader(new InputStreamReader(ligacao.getInputStream()));

									PrintWriter out = new PrintWriter(ligacao.getOutputStream(), true);

									String request = "get" + " " + idC + " " + hashC +" " + tcnSR ;

									out.println(request);

									msg=in.readLine();
									System.out.println(msg);


								} catch (IOException e) {
									System.out.println("Erro ao comunicar com o servidor: " + e);
								}	  
								break;  		

    		    			}
    		    			case 2:
    		    			{
    		    				    System.out.print("Insira a Chave de Acesso: ");
    		    					Scanner infoSR = new Scanner(System.in);
    		    					String accessKey = infoSR.nextLine();
    		    					StringTokenizer sr = new StringTokenizer(accessKey,",");
    		    				//---------------------------------------------SOCKETTCP---------------------------------
    		    				if(tcnSR==2){

    								while (sr.hasMoreElements() && accessKeylength<2)   
    								{    
    									if(accessKeylength == 0) {ipSR=sr.nextToken();System.out.println(ipSR+"\n");}
										if(accessKeylength == 1) {portoSR=Integer.parseInt(sr.nextToken());System.out.println(portoSR+"\n");}
										accessKeylength++;
    								    //System.out.println(sr.nextToken());    
    								}  
    								System.out.print("Introduza o nome do Serviço: ");
    								descricao = infoSR.nextLine();
    								System.out.print("Introduza o Timestamp do Serviço: ");
    								timestamp = infoSR.nextLine();
    		    					
    		    				

    		    					InetAddress serverAddress1 = InetAddress.getByName(ipSR);

									Socket ligacao1 = null;

									ligacao1 = new Socket(serverAddress1,(int) portoSR);

    		    					try {
										BufferedReader in = new BufferedReader(new InputStreamReader(ligacao.getInputStream()));

										PrintWriter out = new PrintWriter(ligacao.getOutputStream(), true);
	
										String request = "get" + descricao + " " + timestamp ;

										out.println(request);
										String msgSR=in.readLine();
										//while(msgSR=in.readLine()){
											System.out.println(msgSR);
										//}
										

									//System.out.println("Terminou a ligacao!");
									} catch (IOException e) {
										System.out.println("Erro ao comunicar com o servidor: " + e);
									}	    		

    		    				}

    		    				//---------------------------------------------JAVARMI---------------------------------
    		    				if(tcnSR==1){
    								while (sr.hasMoreElements() && accessKeylength<3)   
    								{    
    									if(accessKeylength == 0) {ipSR=sr.nextToken();}
										if(accessKeylength == 1) {portoSR=Integer.parseInt(sr.nextToken());}
										if(accessKeylength == 2) {nomeSR=sr.nextToken();}
										accessKeylength++;
    								    //System.out.println(sr.nextToken());    
    								}  

    								BufferedReader bufReader = new BufferedReader(new StringReader(msg));
    								String line=null;
    								while( (line=bufReader.readLine()) != null ){
    									StringTokenizer tokens = new StringTokenizer(line);
    									descricao = tokens.nextToken();
    									if(accessKey.equals(tokens.nextToken())){
    										tokens.nextToken();
    										tokens.nextToken();
    										tokens.nextToken();
    										timestamp= tokens.nextToken();
    										break;
    									}

    		    					}
    		    					if(timestamp.length()==0) {
    		    						System.out.println("ERRO: Não Se encontra nenhum Servico com a Chave "+ accessKey+ " para consulta!\nAceda a opcao 1 para ver serviços disponiveis.");
    		    						break;}
    		    				
    		    				}
    		    			}
    		    			case 3: break;
    		    			default: break;
    		    		}

						
						}while(choice!=3);

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


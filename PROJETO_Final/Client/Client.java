
import java.util.*;
import java.net.*;
import java.io.*;
//import java.util.StringTokenizer; 
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
//import java.util.Date;
import java.time.Instant;
import java.net.SocketTimeoutException;

public class Client {



	static final int SI_PORTO = 2000; 
	static final String SI_HOST = "127.0.0.1"; //IP e PORTO do Serviço de Identificação

	public static void connectServiceRMI(String ip_servico, String nome_servico, String timestamp) {
    	getTemperature handler;
        handler = new getTemperature(ip_servico, nome_servico, Instant.parse(timestamp));
        handler.putTemperature();
    }

    public static String readFile(String fileName) throws IOException {
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
		PrintWriter writer1 = new PrintWriter("SocketTCP.txt");
		writer1.print("");
		writer1.close();
		PrintWriter writer2 = new PrintWriter("JavaRMI.txt");
		writer2.print("");
		writer2.close();

		int choice;
		do {

    		choice = menu();

   			switch (choice){
    		    case 1:{
    		    	String servidor = SI_HOST;
					int porto = SI_PORTO;

					try{//só um teste
						InetAddress serverAddress = InetAddress.getByName(servidor);

						Socket ligacao = null;

						ligacao = new Socket(serverAddress, porto);

						ligacao.close();

					}
					 catch (IOException e) {
						System.out.println("Erro ao comunicar com o Servico de Identificacao: " + e);
						break;
					}
					
					System.out.println("+*************************************************+");
					System.out.println("*	                                          ");
					System.out.println("*	BEM VINDO AO SISTEMA DE IDENTIFICACAO     ");
					System.out.println("*	                                          ");

    		    	System.out.print(" *Introduza o seu Ip: ");
    		    	Scanner scanner = new Scanner(System.in);
    		    	String ip = scanner.nextLine();
    		    	System.out.print(" * Introduza um Identificador Unico seu (Ex.: CC, NIF,...): ");
    		    	String id = scanner.nextLine();
    		    	try{

					InetAddress serverAddress = InetAddress.getByName(servidor);

					Socket ligacao = null;

					ligacao = new Socket(serverAddress, porto);

					BufferedReader in = new BufferedReader(new InputStreamReader(ligacao.getInputStream()));

					PrintWriter out = new PrintWriter(ligacao.getOutputStream(), true);

					String request = "get" + " " + ip + " " + id;

					out.println(request);

					String msg;

					int length=0;
					while((msg = in.readLine())!= null){
						if(length == 0) {System.out.println("\n *	Chave de Acesso: " +msg +";\n");}
						if(length == 1) {System.out.println(" *	Ip do Servico de Tickting: " +msg +";\n");}
						else {if (length ==2) {System.out.println(" *	Porto do Servico de Ticketing: " + msg +";\n");}
						}
					length+=1;
  					}

					ligacao.close();

						//System.out.println("Terminou a ligacao!");
					} catch (IOException e) {
						System.out.println("Erro ao comunicar com o Servico de Identificacao: " + e);
					}
					
					break;
    		    }
            		
        		case 2:
        		    {
        		    System.out.println("E importante referir que para se conectar ao Servico de Tickting e necessario passar pelo passo 1!\n");	
        		    System.out.print("Pretende continuar (S/N:) ");
        		    Scanner scanner = new Scanner(System.in);
        		    char r = scanner.next().charAt(0);
        		    if(r=='S' || r=='s'){
        		    	System.out.println("OK! Introduza os seguintes dados para iniciar conexão:\n");
        		    	System.out.print(" *	Ip do Servico de Ticketing: ");
        		    	Scanner scanner1 = new Scanner(System.in);
    		    		String ipST = scanner1.nextLine();
    		    		System.out.print(" *	Porto do Serviço de Ticketing: ");
    		    		int portoST = scanner1.nextInt();

    		    		try{//só um teste
							InetAddress serverAddress = InetAddress.getByName(ipST);

							Socket ligacao = null;

							ligacao = new Socket(serverAddress, portoST);

							ligacao.close();

						}
						 catch (IOException e) {
							System.out.println("Erro ao comunicar com o de Servico de Ticketing: " + e);
							break;
						}

						System.out.println("+*******************************************+");
						System.out.println("*	                                    ");
						System.out.println("*	BEM VINDO AO SERVICO DE TICKETING    ");
						System.out.println("*	                                    ");

						Scanner scanner2 = new Scanner(System.in);
						System.out.println("\nPara iniciar, introduza os seguintes dados:");
						System.out.print(" *		Chave gerada no Servico de Identificacao: ");
    		    		String hashC = scanner2.nextLine();	
    		    		System.out.print(" *		Identificador Unico utilizado: ");
    		    		String idC = scanner2.nextLine();
						System.out.print(" *		Tipo de tecnologia dos SR que pretende consultar (1-JavaRMI ou 2-SocketTCP): ");
    		    		int tcnSR = scanner2.nextInt();	
    		    		int choice2;
    		    		do{
    		    		String msg2="";
    		    		choice2= menu2(tcnSR);

    		    		int accessKeylength=0;
    		    		String ipSR=null;
    		    		String nomeSR=null;
    		    		String chaveAcesso=null;
    		    		int portoSR=0;
    		    		String portoSRS;
    		    		String descricao=null;
    		    		String timestamp=null;

    		    		switch(choice2){
    		    			case 1:
    		    			{

    		    				try {

    		    					InetAddress serverAddress = InetAddress.getByName(ipST);

									Socket ligacao = null;

									ligacao = new Socket(serverAddress, portoST);
									
									BufferedReader in = new BufferedReader(new InputStreamReader(ligacao.getInputStream()));

									PrintWriter out = new PrintWriter(ligacao.getOutputStream(), true);

									String request = "get" + " " + idC + " " + hashC +" " + tcnSR ;

									out.println(request);
									String msg1;
									int length=0;
									//CommandLineTable st = new CommandLineTable();
									if(tcnSR==2){ 
										CommandLineTable st = new CommandLineTable();
										st.setHeaders("Descricao", "Chave de Acesso", "Ip", "Porto","Timestamp");
										msg2="";
										while((msg1 = in.readLine())!= null){
											if(msg1.equals("")){break;}
											StringTokenizer servicoRede = new StringTokenizer(msg1);
											String description = servicoRede.nextToken();
											String chave = servicoRede.nextToken();
											String ip = servicoRede.nextToken();
											String port = servicoRede.nextToken();
											String instant = servicoRede.nextToken();
											st.addRow(description, chave, ip, port, instant);
											
											//String registo=readFile("SocketTCP.txt");
										

											st.print();
											msg2+=msg1+"\n";
										}
										try {
											PrintWriter printsocket;
    										printsocket = new PrintWriter("SocketTCP.txt");
    										printsocket.print(msg2);
    										printsocket.close();
											} catch (FileNotFoundException e) {
    											System.err.println("File doesn't exist");
    											e.printStackTrace();
										}
										//st.print();
										//System.out.println("\n"+msg2);
									}
									if(tcnSR==1){ 
										msg2="";
										CommandLineTable st = new CommandLineTable();
										st.setHeaders("Descricao", "Chave de Acesso", "Ip", "Porto","Nome","Timestamp");
										while((msg1 = in.readLine())!= null){
											if(msg1.equals("")){break;}
											StringTokenizer servicoRede = new StringTokenizer(msg1);
											String description = servicoRede.nextToken();
											String chave = servicoRede.nextToken();
											String ip = servicoRede.nextToken();
											String port = servicoRede.nextToken();
											String name = servicoRede.nextToken();
											String instant = servicoRede.nextToken();
											st.addRow(description, chave, ip, port, name, instant);

											//st.addRow(servicoRede.nextToken(), servicoRede.nextToken(), servicoRede.nextToken(), servicoRede.nextToken(), servicoRede.nextToken(), servicoRede.nextToken());
											
											//PrintWriter printRMI;
											//String registo=readFile("JavaRMI.txt");
										
											msg2+=msg1+"\n";
											st.print();//msg2+=msg1+"\n";
										}
										try {
											PrintWriter printRMI;
    										printRMI = new PrintWriter("JavaRMI.txt");
    										printRMI.print(msg2);
    										printRMI.close();
											} catch (FileNotFoundException e) {
    											System.err.println("File doesn't exist");
    											e.printStackTrace();
										}
										//st.print();//System.out.println("\n"+msg2);
									}
									//st.print();
									ligacao.close();
								} catch (IOException e) {
									System.out.println("Erro ao comunicar com o Servico de Ticketing: " + e);
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
    									if(accessKeylength == 0) {ipSR=sr.nextToken();}
										if(accessKeylength == 1) {portoSR=Integer.parseInt(sr.nextToken());}
										accessKeylength++;
    								    //System.out.println(sr.nextToken());    
    								}  
    								BufferedReader socketinfo = new BufferedReader(new FileReader("SocketTCP.txt"));
    								String linha;
    								int teste=0;
    								
    								while((linha=socketinfo.readLine())!=null){
    									StringTokenizer token= new StringTokenizer(linha," ");    									
    									descricao=token.nextToken();
    									if(accessKey.equals(token.nextToken())){
    										token.nextToken();
    										token.nextToken();
    										timestamp=token.nextToken();
    										teste=1;
    										break;
    									}
    									else teste=0;


    								}
    								if(teste==0){
    									System.out.println("chave de Acesso não encontrada!\nConsulte novamente os Servicos de Rede disponiveis e veficique se introduziu a chave de acesso corretamente!");
    									break;
    								}
    								//System.out.print("Introduza o nome do Serviço: ");
    								//descricao = infoSR.nextLine();
    								//System.out.print("Introduza o Timestamp do Serviço: ");
    								//timestamp = infoSR.nextLine();
    		    					

    		    					try {

    		    						InetAddress serverAddress1 = InetAddress.getByName(ipSR);

										Socket ligacao1 = null;

										ligacao1 = new Socket(serverAddress1, portoSR);

										BufferedReader in = new BufferedReader(new InputStreamReader(ligacao1.getInputStream()));

										PrintWriter out = new PrintWriter(ligacao1.getOutputStream(), true);
	
										String request = "get" + descricao + " " + timestamp ;
										
										out.println(request);
										String msgSR;
										while((msgSR=in.readLine())!=null){
											System.out.println(msgSR);
											
										}

										
										ligacao1.close();
									//System.out.println("Terminou a ligacao!");
									} catch (IOException e) {
										System.out.println("Erro ao comunicar com o Servico "+ descricao + ": " + e);
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

    								BufferedReader javaRMIinfo = new BufferedReader(new FileReader("JavaRMI.txt"));
    								String linha;
    								int teste=0;
    								while((linha=javaRMIinfo.readLine())!=null){
    									StringTokenizer token= new StringTokenizer(linha," ");    									
    									descricao=token.nextToken();
    									if(accessKey.equals(token.nextToken())){
    										token.nextToken();
    										token.nextToken();
    										token.nextToken();
    										timestamp=token.nextToken();
    										teste=1;
    										break;
    									}
    									else teste=0;


    								}
    								if(teste==0){
    									System.out.println("chave de Acesso não encontrada!\nConsulte novamente os Servicos de Rede disponiveis e veficique se introduziu a chave de acesso corretamente!");
    									break;
    								} 
    								//System.out.print("Introduza o Timestamp do Serviço: ");
    								//timestamp = infoSR.nextLine();

    								connectServiceRMI(ipSR, nomeSR, timestamp);


    		    					}
    		    				break;
    		    				}

    		    			case 3: break;
    		    			default: break;
    		    		}

						
						}while(choice2!=3);

						
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


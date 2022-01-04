import java.util.*;
import java.net.*;
import java.io.*;

public class Admin2 {
    static final int SI_PORTO = 2000;
    static final String SI_HOST = "127.0.0.1"; // IP e PORTO do Serviço de Identificação

    public static int menu() {

        int selection;
        Scanner input = new Scanner(System.in);

        /***************************************************/
        System.out.println("\n************************************************************");
        System.out.println("*	Menu Admin                                            ");
        System.out.println("*	----------------------------------------------------    ");
        System.out.println("*	1 - Registar Servico de Rede  ");
        System.out.println("*	2 - Consultar Servicos de Rede         ");
        System.out.println("*	3 - Quit                                                ");
        System.out.println("*\n************************************************************");
        System.out.print("?-> ");
        selection = input.nextInt();
        return selection;
    }

    public static int menu2() {

        int selection;
        Scanner input = new Scanner(System.in);

        /***************************************************/
        System.out.println("\n************************************************************");
        System.out.println("*	Menu                                                 ");
        System.out.println("*	----------------------------------------------------    ");
        System.out.println("*	1 - Java Socket ");
        System.out.println("*	2 - Java RMI                                                ");
        System.out.println("*	3 - Quit                                               ");
        System.out.println("*************************************************************");
        System.out.print("N -> ");
        selection = input.nextInt();
        return selection;
    }

    public static void main(String[] args) throws IOException {

        String tecnologiaSR;

        int choice;
        do {

            choice = menu();

            switch (choice) {
                case 1: {
                    String servidor = SI_HOST;
                    int porto = SI_PORTO;

                    System.out.println("+*************************************************+");
                    System.out.println("*	                                              *");
                    System.out.println("*	           Criar Serviço de Rede              *");
                    System.out.println("*	                                              *");
                    System.out.println("+*************************************************+");

                    do {

                        choice = menu2();

                        switch (choice) {
                            case 1: {
                                try {
                                    technologySR = "SocketTCP";

                                    Scanner scanner = new Scanner(System.in);

                                    System.out.print("\n Introduza o IP do SR: ");
                                    String ipSR = scanner.nextLine();

                                    System.out.print("\n Introduza o porto do SR: ");
                                    String portoSR = scanner.nextLine();

                                    System.out.print("\n Introduza a descricao do SR: ");
                                    String descriptionSR = scanner.nextLine();

                                    BufferedReader in = new BufferedReader(
                                            new InputStreamReader(connection.getInputStream()));

                                    PrintWriter out = new PrintWriter(connection.getOutputStream(), true);

                                    String request = "post" + " " + descriptionSR + " " + technologySR + " " + ipSR + ""
                                            + portoSR;

                                    out.println(request);

                                    connection.close();

                                    // System.out.println("Terminou a ligacao!");
                                } catch (IOException e) {
                                    System.out.println("Erro ao comunicar com o servidor: " + e);
                                    System.exit(1);
                                }

                            }
                            case 2:
                                try {
                                    tecnologiaSR = "JavaRMI";

                                    Scanner scanner = new Scanner(System.in);

                                    System.out.print("\n Introduza o IP do SR: ");
                                    String ipSR = scanner.nextLine();

                                    System.out.print("\n Introduza o porto do SR: ");
                                    String portoSR = scanner.nextLine();

                                    System.out.print("\n Introduza o nome do SR: ");
                                    String nameSR = scanner.nextLine();

                                    System.out.print("\n Introduza a descricao do SR: ");
                                    String descriptionSR = scanner.nextLine();

                                    BufferedReader in = new BufferedReader(
                                            new InputStreamReader(connection.getInputStream()));

                                    PrintWriter out = new PrintWriter(connection.getOutputStream(), true);

                                    String request = "post" + " " + descriptionSR + " " + technologySR + " " + ipSR + ""
                                            + portoSR + "" + nameSR;

                                    connection.close();

                                    // System.out.println("Terminou a ligacao!");
                                } catch (IOException e) {
                                    System.out.println("Erro ao comunicar com o servidor: " + e);
                                    System.exit(1);
                                }

                            default:
                                break;
                        }

                    } while (choice != 3);
                }
                    ;

                case 2: {

                }

                case 3:
                    break;
                default:
                    // The user input an unexpected choice.
            }

        } while (choice != 3);

    }
}

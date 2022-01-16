import java.net.*;
import java.io.*;

public class ServicoT {
	static int DEFAULT_PORT = 3122;

	public static void main(String[] args) throws IOException {
		int port = DEFAULT_PORT;

		NetworkServices presences = new NetworkServices();

		ServerSocket servidor = null;

		// Create a server socket, bound to the specified port: API
		// java.net.ServerSocketg
		servidor = new ServerSocket(port);

		System.out.println("\nServidor à espera de ligacoes no porto " + port);

		PrintWriter writer1 = new PrintWriter("SocketTCP.txt");
		writer1.print("");
		writer1.close();
		PrintWriter writer2 = new PrintWriter("JavaRMI.txt");
		writer2.print("");
		writer2.close();

		while (true) {
			try {

				// Listen for a connection to be made to the socket and accepts it: API
				// java.net.ServerSocket

				Socket ligacao = servidor.accept();

				// Start a GetPresencesRequestHandler thread
				GetNetworkServicesHandler request = new GetNetworkServicesHandler(ligacao, presences);
				request.start();

			} catch (IOException e) {
				System.out.println("Erro na execucao do servidor: " + e);
				System.exit(1);
			}
		}
	}
}

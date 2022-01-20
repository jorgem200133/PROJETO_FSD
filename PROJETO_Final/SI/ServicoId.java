import java.net.*;
import java.io.*;

public class ServicoId {
	static int DEFAULT_PORT = 2000;

	public static void main(String[] args) throws IOException {
		int port = DEFAULT_PORT;

		SIConnection presences = new SIConnection();

		ServerSocket servidor = null;

		// Create a server socket, bound to the specified port: API
		// java.net.ServerSocketg
		servidor = new ServerSocket(port);

		System.out.println("\nServidor à espera de ligacoes no porto " + port);

		while (true) {
			try {

				// Listen for a connection to be made to the socket and accepts it: API
				// java.net.ServerSocket

				Socket ligacao = servidor.accept();

				// Start a GetPresencesRequestHandler thread
				GetSTInfoRequestHandler request = new GetSTInfoRequestHandler(ligacao, presences);
				request.start();

			} catch (IOException e) {
				System.out.println("Erro na execucao do servidor: " + e);
				System.exit(1);
			}
		}
	}
}

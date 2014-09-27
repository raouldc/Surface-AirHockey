package ahci;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * Trivial client for the date server.
 */
public class Client {

	/**
	 * Runs the client as an application.  First it displays a dialog
	 * box asking for the IP address or hostname of a host running
	 * the date server, then connects to it and displays the date that
	 * it serves.
	 */
	public static void main(String[] args) throws IOException {
		ServerSocket listener = new ServerSocket(9090);
		try {
			while (true) {
				Socket socket = listener.accept();
				try {

					BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					String x ="";
					while((x=in.readLine())!=null){
						System.out.println(x);
					}
				} finally {
					socket.close();
				}
			}
		}
		finally {
			listener.close();
		}
	}
}

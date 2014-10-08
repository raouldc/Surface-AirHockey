package ahci;

import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class TCPTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub


		//System.out.println("sending json.toString());
		SocketAddress address = new InetSocketAddress("172.23.2.9", 10000);
		Socket clientSocket = new Socket();
		try{
			clientSocket.connect(address, 20000); 
			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
			outToServer.writeChars("Hello\n");

			outToServer.flush();
			outToServer.close();
			clientSocket.close();
		}catch(Exception e){
			
			System.out.println(e.getMessage());
		}

		}
	}

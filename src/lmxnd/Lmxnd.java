package lmxnd;

import com.digi.xbee.api.XBee;

public class Lmxnd {
	/*
	public static void main(String[] args) {
		XBee xbee = new XBee();
		try {
			xbee.open("/deb/ttyUSB", 9600);
		} catch (XBeeException ex) {
			System.err.println(ex.getMessage());
			System.err.println("Fatal error. Exiting with code 1.");
			System.exit(1);
		}
	}
	*/
	public static void main(String[] args) {
		System.out.println("Hello, world");
		/*
		 * XBee xbee = new XBee();
		 * xbee.open();
		 */
		
		XBeeNetwork network = new XBeeNetwork();
		runServer(network, 1337);
	}

	private static void runServer(XBeeNetwork network, int port)
	                                                     throws IOException {
		ServerSocket serverSocket = new ServerSocket(port);
		
		while (true) {
			Socket socket = serverSocket.accept();

	}

	private static class RequestServer implements Runnable {
		public ServerLogic(Socket connection, XBeeNetwork network) {
			conn = connection;
			net = network;
		}

		public void run() {
			InputStream in = conn.getInputStream();
			OutputStream out = conn.getOutputStream();

		private Socket conn;
		private XBeeNetwork net;
	}
}

package lmxnd;

import com.digi.xbee.api.XBee;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.net.InetSocketAddress;
import java.io.IOException;
import java.io.OutputStream;

public class Lmxnd {

	public static void main(String[] args) {
		System.out.println("Hello, world");
		XBeeNetwork network = new XBeeNetwork();

		try {
			HttpServer server
			    = HttpServer.create(new InetSocketAddress(1337), 0);
			server.createContext("/", new ServerHandler(network));
			server.setExecutor(null);
			server.start();
		} catch (IOException ex) {
			System.err.println("Could not create server.");
			System.exit(1);
		}

	}

	private static class ServerHandler implements HttpHandler {

		public ServerHandler(XBeeNetwork network) {
			net = network;
		}

		@Override
		public void handle(HttpExchange exchange) {
			try {
				String jsonObject = net.view();
				exchange.sendResponseHeaders(200, jsonObject.length());
				OutputStream outputStream = exchange.getResponseBody();
				outputStream.write(jsonObject.getBytes());
			} catch (Exception ex) {
				System.err.println(
				    "Exception encountered while handling HTTP request:");
				System.err.println(ex.getMessage());
				System.err.println("\nContinuing...");
			}
		}
		
		private XBeeNetwork net;
	}
}

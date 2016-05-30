package lmxnd;

/* Java SE */
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.net.InetSocketAddress;
import java.io.IOException;
import java.io.OutputStream;

/*
 * XBee Java Library:
 * https://github.com/digidotcom/XBeeJavaLibrary
 */
import com.digi.xbee.api.XBeeDevice;
import com.digi.xbee.api.exceptions.XBeeException;
import com.digi.xbee.api.listeners.IDataReceiveListener;
import com.digi.xbee.api.models.XBeeMessage;


public class Lmxnd {

	public static void main(String[] args) {
		// This object is the shared state
		XBeeNetwork network = new XBeeNetwork();

		XBeeDevice local = new XBeeDevice("/dev/usb", 9600);
		try {
			local.open();
		} catch (XBeeException ex) {
			System.err.println(
			    "Exception raised while opening connection to local XBee.");
			System.err.println("\nExiting...");
			System.exit(1);
		}

		local.addDataListener(new XBeeListener(network));

		startHttpServer(network, 1337);
	}

	/**
	 * This function starts an HTTP server which serves a JSON representation of
	 * the given network on the given port.
	 * <p>
	 * The function blocks the current thread indefinitely.
	 *
	 * @param network This is the main state object which allows the server to
	 * serve the current state at any given time.
	 * @param port The port to listen on.
	 */
	private static void startHttpServer(XBeeNetwork network, int port) {
		try {
			HttpServer server
			    = HttpServer.create(new InetSocketAddress(port), 0);
			server.createContext("/", new ServerHandler(network));
			server.setExecutor(null);
			server.start();
		} catch (IOException ex) {
			System.err.println("Could not create server.");
			System.err.println("\nExiting...");
			System.exit(1);
		}
	}

	/**
	 * This class handles HTTP requests by serving a JSON object.
	 */
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
			} finally {
				exchange.close();
			}
		}
		
		private XBeeNetwork net;
	}

	/**
	 * This class has is a listener for when an XBee packet is received.
	 */
	private static class XBeeListener implements IDataReceiveListener {

		public XBeeListener(XBeeNetwork network) {
			net = network;
		}
		
		@Override
		public void dataReceived(XBeeMessage xbeeMessage) {
			try {
				byte[] addressArray =
					xbeeMessage.getDevice().get64BitAddress().getValue();
				String data = xbeeMessage.getDataString();

				net.update(addressArray, data);
			} catch (Exception ex) {
				System.err.println(
				    "Exception encountered while handling data from XBee");
				System.err.println(ex.getMessage());
				System.err.println("\nContinuing...");
			}
		}

		private XBeeNetwork net;
	}

}

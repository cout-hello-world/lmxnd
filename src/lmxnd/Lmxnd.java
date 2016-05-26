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
		XBee xbee = new XBee();
	}
}

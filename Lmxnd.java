import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeException;

public class Lmxnd {
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
}

package lmxnd;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class XBeeNetwork {
	/**
	 * This method updates the program's representation of an XBee network by
	 * using data from an XBeeMessage.
	 * 
	 * @param address A representation of the 16 or 64 bit address of the XBee
	 *                which sent the message
	 * @param data A {@code String} representing the actuall message. This
	 *             {@code string} must be formatted as a space seperated
	 *             key-value pair where the first "word" is the name of the
	 *             measured quantity and the second "word" is the value (either
	 *             integral or floating point decimal).
	 * @throws NullPointerException if either argument is null
	 * @throws IllegalArgumentException if the {@Code data}
	 *                                  is imporperly formatted
	 */
	public synchronized void update(byte[] address, String data) {
		/* First, get a timestamp for the update operation */
		long timestamp = System.currentTimeMillis() / 1000L;

		/* Check for errors so exception can be thrown before instance variables
		 * are modified. */
		if (address == null || data == null) {
			throw new NullPointerException(
			    "Arguments to update method must not be null");
		}
		String[] dataArr = data.split("\\s+");
		if (dataArr.length != 2) {
			throw new IllegalArgumentException(
			"Key value pair not present in data parameter");
		}
		try {
			Double.parseDouble(dataArr[1]);
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException(
			    "The value in the data string is not a number");
		}
		
		Quantity quantity = new Quantity(dataArr[0]);
		Value val = new Value(dataArr[1], timestamp);
		XBeeAddress addr = new XBeeAddress(address);
		
		/* Todo... if contains, etc, etc */
	}

	/* Private */

	HashMap<XBeeAddress, HashMap<Quantity, Value>> network;

	private static class XBeeAddress {
		/**
		 * This constructor takes a byte array and constucts an object with an
		 * independant internal representation of the byte array which can be
		 * accessed via a {@code toString} method.
		 */
			public XBeeAddress(byte[] address) {
			/*
			 * A {@code String} is used here because the loop will not run many
			 * times.
			 */
			String addressString = "";
			for (int i = 0; i + 1 < address.length; ++i) {
				addressString += str(address[i]);
				addressString += ".";
			}
			addressString += str(address[address.length - 1]);
			addr = addressString;
		}

		/*
		 * The {@code hashCode} and {@code equals} methods are overridden here
		 * to allow this type to be the key of a {@code HashMap}.
		 */
		@Override
		public int hashCode() {
			return addr.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof XBeeAddress)) {
				return false;
			}

			XBeeAddress other = (XBeeAddress)obj;
			return addr.equals(other.addr);
		}

		@Override
		public String toString() {
			return addr;
		}


		private final String addr;

		/**
		 * This function takes a byte and returns a string in the range [0,
		 * 255].
		 *
		 * @param by The {@code byte} to be converted
		 * @return The converted {@Code String}
		 */
		private static String str(byte by) {
			if (by < 0) {
				return Integer.toString((int)by + 0xff);
			} else {
				return Byte.toString(by);
			}
		}
	}

	private static class Quantity {
		public Quantity(String quantityName) {
			name = quantityName;
		}

		@Override
		public int hashCode() {
			return name.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Quantity)) {
				return false;
			}

			Quantity other = (Quantity)obj;
			return name.equals(other.name);
		}

		private final String name;
	}

	private static class Value {
		public Value(String value, long timestamp) {
			val = value;
			time = Long.toString(timestamp);
		}

		public String getValue() {
			return val;
		}

		public String getTimestamp() {
			return time;
		}

		private final String val;
		private final String time;
	}
}

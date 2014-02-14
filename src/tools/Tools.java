package tools;

import java.util.InputMismatchException;

public class Tools {

	public static int[] deciToBase(int number, int base, int digits) {
		int[] baseArray = new int[digits]; // create an array of base-digits
		for (int i = 0; Math.abs(number) > 0 && i < digits; ++i) { // convert decimal into base by mod-reduction
			baseArray[i] = number % base; // modulus
			number /= base; // reduction
		}

		return baseArray;
	}

	public static int[] deciToBase(int number, int base) {
		int digits = 0;
		for (int deci = number; Math.abs(deci) > 0; ++digits) { // determine the
																// number of
																// digits.
			deci /= base;
		}
		return deciToBase(number, base, digits); // do the conversion
	}

	public static int baseToDeci(int[] number, int base) {
		int decimal = 0;
		for (int i = 0; i < number.length; ++i) {
			decimal += number[i] * Math.pow(base, i); // digit-base scalar
														// addition.
		}
		return decimal;
	}

	public static String toBinaryString(int number, int digits) {
		String string = String.format("%" + digits + "s", Integer.toBinaryString(number)).replace(' ', '0');
		string = "0b" + string.substring(string.length() - digits, string.length());
		return (string);
	}

	public static String toHexString(int number, int digits) {
		String string = String.format("%" + digits + "s", Integer.toHexString(number)).replace(' ', '0');
		string = "0x" + string.substring(string.length() - digits, string.length());
		return string;

	}

	public static short hexToShort(String hex, int maxDigits) throws InputMismatchException {
		short number = 0;
		if (maxDigits > hex.length()) throw new InputMismatchException(maxDigits
				+ " excedes the number of digits in string \"" + hex + "\"");
		for (int i = hex.length() - 1, p = 0; i >= hex.length() - maxDigits; --i, p++) {
			char digit = hex.charAt(i);
			int iDigit = 0;
			if ('0' <= digit && digit <= '9') {
				iDigit = digit - '0';
			}
			else if ('a' <= digit && digit <= 'f') {
				iDigit = (digit - 'a') + 10;
			}
			else if ('A' <= digit && digit <= 'F') {
				iDigit = (digit - 'A') + 10;
			}
			else {
				throw new InputMismatchException("invalid hex digit " + digit);
			}

			number += iDigit * Math.pow(16, p);

		}
		if (((0b1 << (maxDigits * 4)) & number) == (0b1 << (maxDigits * 4))) {
			for( int i = (maxDigits * 4), ext = 0b1 << (maxDigits * 4); i < 16 ; ++i, ext = ext << 1 ){
				number =  (short) (number | ext);
			}
		}
		return number;
	}
}

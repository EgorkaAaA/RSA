package tytRsa;

import static java.math.BigInteger.ONE;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

class Crypto {

	public static void main(String[] args) {
		BigInteger p,q,e,d;
		try {
			p = new BigInteger(args[2]);
			q = new BigInteger(args[3]);
		} catch (Exception exception) {
			int bitLength = 128;
			Random rnd = new SecureRandom();
			p = BigInteger.probablePrime(bitLength, rnd);
			q = BigInteger.probablePrime(bitLength, rnd);
		}

		BigInteger n = p.multiply(q);
		BigInteger phi = (p.subtract(ONE)).multiply(q.subtract(ONE));

		int i = 3;
		while (true) {
			e = BigInteger.valueOf(i);
			i++;
			if (!e.isProbablePrime(100)) {
				continue;
			}
			if (e.compareTo(phi) == 1) {
				continue;
			}
			try {
				d = e.modInverse(phi);
			} catch (Exception exr) {
				continue;
			}
			break;
		}

		System.out.println("p   = " + p);
		System.out.println("q   = " + q);
		System.out.println("n   = " + n);
		System.out.println("phi = " + phi);
		System.out.println("e   = " + e);
		System.out.println("d   = " + d);

		String M = args[1];
		System.out.println("M   = " + M);

		BigInteger m = new BigInteger(M.getBytes());

		System.out.println(m);
		if (args[0].equals("e")) {
//			if (m.compareTo(n) == 1) {
//				throw new IllegalArgumentException("message too long - increase bitLength or split the message");
//			}
			BigInteger c = m.modPow(e, n);
			System.out.println("encrypted = " + c);
		} else if (args[0].equals("d")) {
			BigInteger decrypted_m = new BigInteger(M).modPow(d, n);
			System.out.println(decrypted_m);
			String decrypted_M = new String(decrypted_m.toByteArray());
			System.out.println("decrypted_M = " + decrypted_M);
		}
	}

}
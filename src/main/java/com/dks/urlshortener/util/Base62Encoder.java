package com.dks.urlshortener.util;

public class Base62Encoder {
	
	private static final String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	
	public static String encode(long num) {
		if (num == 0) {
			return String.valueOf(BASE62.charAt(0));
		}
		StringBuilder sb = new StringBuilder();
		while (num > 0) {
			int rem = (int)(num % 62);
			sb.append(BASE62.charAt(rem));
			num /= 62;
		}
		return sb.reverse().toString();
	}
}	

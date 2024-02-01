package com.qiyuan.bautil.util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class Base62 {
     private static final String BASE62_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
     public static String encode(String input) {
        BigInteger base10 = new BigInteger(input.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        while (base10.compareTo(BigInteger.ZERO) > 0) {
            BigInteger[] quotientAndRemainder = base10.divideAndRemainder(BigInteger.valueOf(62));
            sb.append(BASE62_CHARS.charAt(quotientAndRemainder[1].intValue()));
            base10 = quotientAndRemainder[0];
        }
        return sb.reverse().toString();
    }
     public static String decode(String input) {
        BigInteger base10 = BigInteger.ZERO;
        for (int i = 0; i < input.length(); i++) {
            int charValue = BASE62_CHARS.indexOf(input.charAt(i));
            base10 = base10.multiply(BigInteger.valueOf(62)).add(BigInteger.valueOf(charValue));
        }
        return new String(base10.toByteArray());
    }
}
package com.empire.android.marvelpedia.api;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class ApiAuth {

    public static String PUBLIC_KEY = "0b72e793ddaf90a3c2f2edae5d3dd02f";
    private String PRIVATE_KEY = "6fad8353778938a3ac83c2bf6253248056be447b";

    private String timestamp;
    private String hash;

    public ApiAuth(){
        Date now = new Date();
        timestamp = String.valueOf(now.getTime());
        hash = generateHash(timestamp);
    }

    private String generateHash(String timestamp){

        String hashText = "";

        try {
            String toHash = timestamp + PRIVATE_KEY + PUBLIC_KEY;

            byte[] bytesOfMessage = toHash.getBytes("UTF-8");

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] thedigest = md.digest(bytesOfMessage);

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : thedigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            hashText = hexString.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return hashText;

    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getHash() {
        return hash;
    }
}

package com.empire.android.marvelpedia.util;

import com.empire.android.marvelpedia.MarvelApi;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {

    public static String generateHash(String timestamp){

        String hashText = "";

        try {
            String toHash = timestamp + MarvelApi.PRIVATE_KEY + MarvelApi.PUBLIC_KEY;

            byte[] bytesOfMessage = toHash.getBytes("UTF-8");

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] thedigest = md.digest(bytesOfMessage);
            BigInteger bigInt = new BigInteger(1,thedigest);
            hashText = bigInt.toString(16);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return hashText;

    }

}

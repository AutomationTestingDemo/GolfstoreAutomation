package com.ca.golfstore.api;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

public class HexaDecoder {

    public static String hexaDecoder(String myhexString) throws DecoderException {
    	
    	 char[] charArray = myhexString.toCharArray();

         // decode the char array to byte[] (2nd step)
         byte[] decodedHex = Hex.decodeHex(charArray);

       // The String decoded to Base64 (3rd step)
       String result= Base64.encodeBase64String(decodedHex);
       
       System.out.println(result);
       
       System.out.println(result.length());
       
	   return result;
       
    }

}

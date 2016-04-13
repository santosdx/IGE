package com.dane.ige.seguridad;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.*;
import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import org.apache.log4j.Logger;

/**
 * Esta clase que Encripta una cadena de texto y luego la convierte a Base64;
 * Permite encriptar y desencriptar cadenas de texto.
 */
public class ClaseDESBase64 {

    final static Logger LOGGER = Logger.getLogger(ClaseDESBase64.class);

    Cipher encrypt;
    Cipher decrypt;
    SecretKey key;
    String keyTxt;

    /**
     * Constructor de clase, que recibe como parametro la clave que soportara
     * el algoritmo de encriptación y desencriptación.
     * @param clave 
     */
    public ClaseDESBase64(String clave) {
        keyTxt = clave;
        try {
            encrypt = Cipher.getInstance("DES");
            decrypt = Cipher.getInstance("DES");

            if (keyTxt.equals("")) {
                SecretKey key = KeyGenerator.getInstance("DES").generateKey();// Genera  Clave automática
                encrypt.init(Cipher.ENCRYPT_MODE, key);// Con clave aleatoria
                decrypt.init(Cipher.DECRYPT_MODE, key);// Con clave aleatoria
            } else {
                KeySpec ks = new DESKeySpec(keyTxt.getBytes("UTF8"));
                SecretKeyFactory kf = SecretKeyFactory.getInstance("DES");
                SecretKey ky = kf.generateSecret(ks);
                encrypt.init(Cipher.ENCRYPT_MODE, ky);
                decrypt.init(Cipher.DECRYPT_MODE, ky);
            }
        } catch (InvalidKeySpecException ex) {
            LOGGER.error(ex);
        } catch (UnsupportedEncodingException ex) {
            LOGGER.error(ex);
        } catch (InvalidKeyException ex) {
            LOGGER.error(ex);
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.error(ex);
        } catch (NoSuchPaddingException ex) {
            LOGGER.error(ex);
        }
    }

    /**
     * Función que permite encriptar una cadena de texto.
     *
     * @param str
     * @return String
     */
    @SuppressWarnings("hiding")
    public String encriptar(String str) {
        try {
            // Encode the string into bytes using utf-8
            byte[] utf8 = str.getBytes("UTF8");

            // Encrypt
            byte[] enc = encrypt.doFinal(utf8);

            // Encode bytes to base64 to get a string
            return new sun.misc.BASE64Encoder().encode(enc);
        } catch (javax.crypto.BadPaddingException e) {
            LOGGER.error(e);
        } catch (IllegalBlockSizeException e) {
            LOGGER.error(e);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e);
        } catch (java.io.IOException e) {
            LOGGER.error(e);
        }
        return null;
    }

    /**
     * Función que permite desencriptar una cadena de texto
     *
     * @param str
     * @return String
     */
    // @SuppressWarnings("restriction")
    public String desencriptar(String str) {
        try {
            // Decode base64 to get bytes
            byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);

            // Decrypt
            byte[] utf8 = decrypt.doFinal(dec);

            // Decode using utf-8
            return new String(utf8, "UTF8");
        } catch (javax.crypto.BadPaddingException e) {
            LOGGER.error(e);
        } catch (IllegalBlockSizeException e) {
            LOGGER.error(e);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e);
        } catch (java.io.IOException e) {
            LOGGER.error(e);
        }
        return null;
    }
}

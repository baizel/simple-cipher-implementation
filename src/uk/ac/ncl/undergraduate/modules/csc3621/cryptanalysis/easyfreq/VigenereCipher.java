package uk.ac.ncl.undergraduate.modules.csc3621.cryptanalysis.easyfreq;

/**
 * This class is capable of encrypt and decrypt according to the Vigen&egrave;re
 * cipher.
 *
 * @author Changyu Dong
 * @author Roberto Metere
 * @author Your Name
 */
public class VigenereCipher {

    /**
     * Encryption function of the Vigen&egrave;re cipher.
     *
     * <p>
     * TODO: Complete the Vigen&egrave;re encryption function.
     *
     * @param plaintext the plaintext to encrypt
     * @param key the encryption key
     * @return the ciphertext according with the Vigen&egrave;re cipher.
     */
    public static String encrypt(String plaintext, String key) {
        // Please, do not remove the editor-fold comments.
        //<editor-fold defaultstate="collapsed" desc="Write your code here below!">
        StringBuilder cipherText = new StringBuilder();
        for (int i = 0; i< plaintext.length(); i++){
            int keyCounter = i % key.length();
            int plainTextIndex = Util.charToIndex(plaintext.charAt(i));
            int keyIndex = Util.charToIndex(key.charAt(keyCounter));
            char cipherChar = Util.indexToChar((plainTextIndex+keyIndex) % 26);
            cipherText.append(cipherChar);
        }
        return cipherText.toString();
        //</editor-fold> // END OF YOUR CODE
    }

    /**
     * Decryption function of the Vigen&egrave;re cipher.
     *
     * <p>
     * TODO: Complete the Vigen&egrave;re decryption function.
     *
     * @param ciphertext the encrypted text
     * @param key the encryption key
     * @return the plaintext according with the Vigen&egrave;re cipher.
     */
    public static String decrypt(String ciphertext, String key) {
        // Please, do not remove the editor-fold comments.
        //<editor-fold defaultstate="collapsed" desc="Write your code here below!">



        return null;
        //</editor-fold> // END OF YOUR CODE
    }

}

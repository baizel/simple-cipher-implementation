package uk.ac.ncl.undergraduate.modules.csc3621.cryptanalysis.easyfreq;

import javax.sql.rowset.serial.SerialStruct;

/**
 * This class is capable of encrypt and decrypt according to the Vigen&egrave;re
 * cipher.
 *
 * @author Changyu Dong
 * @author Roberto Metere
 * @author Baizel Mathew
 */
public class VigenereCipher {

    /**
     * Encryption function of the Vigen&egrave;re cipher.
     *
     * <p>
     * TODO: Complete the Vigen&egrave;re encryption function.
     *
     * @param plaintext the plaintext to encrypt
     * @param key       the encryption key
     * @return the ciphertext according with the Vigen&egrave;re cipher.
     */
    public static String encrypt(String plaintext, String key) {
        // Please, do not remove the editor-fold comments.
        //<editor-fold defaultstate="collapsed" desc="Write your code here below!">
        StringBuilder cipherText = new StringBuilder();
        int count = 0;
        for (int i = 0; i < plaintext.length(); i++) {
            if (Util.isValidLetter(plaintext.charAt(i))) {
                int keyCounter = count % key.length();

                int plainTextIndex = Util.charToIndex(plaintext.charAt(i));
                int keyIndex = Util.charToIndex(key.charAt(keyCounter));

                int encryptedIndex = (plainTextIndex + keyIndex) % 26;
                char cipherChar = Util.indexToChar(encryptedIndex);

                cipherText.append(cipherChar);
                count++;
            } else {
                cipherText.append(plaintext.charAt(i));
            }
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
     * @param key        the encryption key
     * @return the plaintext according with the Vigen&egrave;re cipher.
     */
    public static String decrypt(String ciphertext, String key) {
        // Please, do not remove the editor-fold comments.
        //<editor-fold defaultstate="collapsed" desc="Write your code here below!">

        StringBuilder decryptedText = new StringBuilder();
        int count = 0;
        for (int i = 0; i < ciphertext.length(); i++) {
            if (Util.isValidLetter(ciphertext.charAt(i))) {
                int keyCounter = count % key.length();

                int cipherIndex = Util.charToIndex(ciphertext.charAt(i));
                int keyIndex = Util.charToIndex(key.charAt(keyCounter));

                int decryptedIndex = (cipherIndex - keyIndex); // No need for mod by 26 as it will never go over 26
                if (decryptedIndex < 0) {
                    decryptedIndex += 26; // add 26 to the negative number to loop it back around
                }

                char decryptedChar = Util.indexToChar(decryptedIndex);
                decryptedText.append(decryptedChar);
                count++;
            } else {
                decryptedText.append((ciphertext.charAt(i)));
            }
        }

        return decryptedText.toString();
        //</editor-fold> // END OF YOUR CODE
    }

}

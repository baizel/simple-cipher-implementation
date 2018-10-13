package uk.ac.ncl.undergraduate.modules.csc3621.cryptanalysis.easyfreq;

import java.math.BigInteger;
import java.util.*;

/**
 * This class is used to break the Vigenere cipher by figuring out the key length
 * and then doing a frequency analysis to work out the key
 *
 * @author Baizel
 */
public class VigenereCipherBreaker {

    private String cipherText = null;

    public VigenereCipherBreaker(String cipherText) {
        this.cipherText = cipherText;
    }
    public VigenereCipherBreaker() {}

    public void setCipherText(String cipherText) {
        this.cipherText = cipherText;
    }

    /**
     * This method does a frequency analysis on every nth letter where n is key length to work out the key
     * cipher text must be set before calling this method.
     *
     * @return the key as a string
     * @throws IllegalArgumentException if cipherText is not set
     */
    public String findKey() throws IllegalArgumentException{
        if (this.cipherText == null){
            throw new IllegalArgumentException("Cipher text not set!");
        }
        int keyLength = findKeyLength();
        StringBuilder key = new StringBuilder();
        FrequencyAnalyser analyser = new FrequencyAnalyser();
        for (int j = 0; j < keyLength; j++) {
            StringBuilder everyKeyLengthChar = new StringBuilder();
            for (int i = j; i < this.cipherText.length() - keyLength; i += keyLength) {
                everyKeyLengthChar.append(this.cipherText.charAt(i));
            }
            analyser.setText(everyKeyLengthChar.toString());
            FrequencyTable table = analyser.analyse();
            double[] tbl = table.getTable();
            Arrays.sort(tbl);
            double max = tbl[tbl.length - 1];

            int cipherCharIndex;
            for (cipherCharIndex = 0; cipherCharIndex < tbl.length; cipherCharIndex++) {
                if (table.getTable()[cipherCharIndex] == max)
                    break;
            }
            int keyIndex = cipherCharIndex - Util.charToIndex('E');
            if (keyIndex < 0) {
                keyIndex += 26;
            }
            key.append(Util.indexToChar(keyIndex));

        }
        return key.toString();
    }

    /**
     * This method finds the key length by looking at the most repeated trigram
     * and then uses the difference form occurred index to work out the lowest multiple.
     * @return key length as an int
     */
    private int findKeyLength() {
        Map<String, List<Integer>> subStringToOccurrence = getMostCommonTrigram();

        String mostOcuredSubstring = null;
        List<Integer> occurences = null;

        for (Map.Entry<String, List<Integer>> entry : subStringToOccurrence.entrySet()) {
            if (mostOcuredSubstring == null) {
                //init the variables
                mostOcuredSubstring = entry.getKey();
                occurences = entry.getValue();
            }

            if (entry.getValue().size() >= occurences.size()) {
                mostOcuredSubstring = entry.getKey();
                occurences = entry.getValue();

            }

        }

        int multiple = 0;
        List<Integer> listOfGCDs = new ArrayList<>();
        //gets gcd of every pair
        for (int i = 0; i < occurences.size() - 2; i++) {
            int a = occurences.get(i + 1) - occurences.get(i);
            int b = occurences.get(i + 2) - occurences.get(i + 1);
            listOfGCDs.add(gcd(a, b));
        }
        multiple = Collections.min(listOfGCDs);
        return multiple;
    }

    /**
     * This method does an exhaustive search for every 3 letter words and then keeps a map with a list of index's
     * @return a map of the three letter words with a list of all index's where it has occurred
     */
    private Map<String, List<Integer>> getMostCommonTrigram() {

        FrequencyAnalyser analyser = new FrequencyAnalyser();
        HashMap<String, List<Integer>> subStringToOccurrence = new HashMap<>();

        analyser.setText(this.cipherText);
        for (int i = 0; i < this.cipherText.length() - 2; i++) {
            //+3 for the trigram
            String sub = this.cipherText.substring(i, i + 3);
            if (subStringToOccurrence.containsKey(sub)) {
                subStringToOccurrence.get(sub).add(i);
            } else {
                List<Integer> occurrences = new ArrayList<>();
                occurrences.add(i);
                subStringToOccurrence.put(sub, occurrences);
            }
        }
        return subStringToOccurrence;
    }

    /**
     * Method to work out the the greatest common denominator of given two numbers
     * @param a first number
     * @param b second number
     * @return an int that is the GCD of a & b
     */
    private static int gcd(int a, int b) {
        BigInteger b1 = BigInteger.valueOf(a);
        BigInteger b2 = BigInteger.valueOf(b);
        BigInteger gcd = b1.gcd(b2);
        return gcd.intValue();
    }
}

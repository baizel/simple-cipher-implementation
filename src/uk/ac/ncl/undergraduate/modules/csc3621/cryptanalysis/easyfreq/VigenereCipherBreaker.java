package uk.ac.ncl.undergraduate.modules.csc3621.cryptanalysis.easyfreq;

import java.math.BigInteger;
import java.util.*;

public class VigenereCipherBreaker {

    private String cipherText;

    public VigenereCipherBreaker(String cipherText) {
        this.cipherText = cipherText;
    }

    public String findKey() {
        int keyLength = findKeyLength();
        StringBuilder key = new StringBuilder();
        FrequencyAnalyser analyser = new FrequencyAnalyser();
        for (int j =0; j < keyLength; j++) {
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
            if (keyIndex < 0){
                keyIndex += 26;
            }
            key.append(Util.indexToChar(keyIndex));

        }
        return key.toString();
    }

    private Map<String, List<Integer>> getMostCommonTriGram() {

        FrequencyAnalyser analyser = new FrequencyAnalyser();
        HashMap<String, List<Integer>> subStringToOccurrence = new HashMap<String, List<Integer>>();

        analyser.setText(this.cipherText);
        //+3 to find repeat of the word 'the'
        for (int i = 0; i < this.cipherText.length() - 2; i++) {
            String sub = this.cipherText.substring(i, i + 3);
            int occurenceIndex = i;
            if (subStringToOccurrence.containsKey(sub)) {
                subStringToOccurrence.get(sub).add(occurenceIndex);
            } else {
                List<Integer> occurrences = new ArrayList<>();
                occurrences.add(occurenceIndex);
                subStringToOccurrence.put(sub, occurrences);
            }
        }
        return subStringToOccurrence;
    }

    private int findKeyLength() {
        Map<String, List<Integer>> subStringToOccurrence = getMostCommonTriGram();

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
        for (int i = 0;  i < occurences.size()-2; i++) {
            int a = occurences.get(i+1) - occurences.get(i);
            int b = occurences.get(i+2) - occurences.get(i+1);
            listOfGCDs.add(gcd(a,b));
        }
        multiple = Collections.min(listOfGCDs);
        return multiple;
    }

    private static int gcd(int a, int b) {
        BigInteger b1 = BigInteger.valueOf(a);
        BigInteger b2 = BigInteger.valueOf(b);
        BigInteger gcd = b1.gcd(b2);
        return gcd.intValue();
    }
}

package uk.ac.ncl.undergraduate.modules.csc3621.cryptanalysis.easyfreq;

import java.util.List;
import java.util.Map;

public class VigenereCipherBreaker {

    private String cipherText;

    public VigenereCipherBreaker(String cipherText) {
        this.cipherText = cipherText;
    }

    public String findKey(){
        int keyLength = findKeyLength(null,null);
        return null;
    }

    private Map<String, List<Integer>> getMostCommonThreeGrams(){
        return null;
    }

    private int findKeyLength(String mostRepeatedSubstring, List<Integer> indexes){
       return 5;
    }



}

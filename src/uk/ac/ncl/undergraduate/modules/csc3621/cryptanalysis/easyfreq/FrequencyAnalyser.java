package uk.ac.ncl.undergraduate.modules.csc3621.cryptanalysis.easyfreq;

import java.util.stream.DoubleStream;

/**
 * This class is to compute a frequency table of a texts.
 *
 * @author Changyu Dong
 * @author Roberto Metere
 * @author Baizel Mathew
 */
public class FrequencyAnalyser {

    /**
     * The text to analyse
     */
    private String text;

    /**
     * Get the text to analyse.
     *
     * @return the text to analyse.
     */
    public String getText() {
        return text;
    }

    /**
     * Set the text to analyse.
     *
     * @param text the text to analyse.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * This method returns a frequency table as a result of the analysis of the
     * text.
     * <p>
     * TODO: complete the function that conduct a frequency analysis of the
     * internal buffer and produce a frequency table based on the analysis.
     * Please, write your code between the comments as appropriate.
     *
     * @return frequency table as a result of the analysis of the text
     */
    public FrequencyTable analyse() {
        // Please, do not remove the editor-fold comments.
        //<editor-fold defaultstate="collapsed" desc="Write your code here below!">
        //Tracker is an array that keeps count of each index
        double[] tracker = new double[26];
        FrequencyTable table = new FrequencyTable();
        String text = this.getText();

        for (int i = 0; i < text.length(); i++) {
            char letter = text.charAt(i);
            if (Util.isValidLetter(letter)) {
                int charIndex = Util.charToIndex(letter);
                tracker[charIndex] = tracker[charIndex] + 1.0;
            }
        }

        double totalNumberOfLetters = DoubleStream.of(tracker).sum();
        for (char i = 'A'; i <= 'Z'; i++) {
            double letterCount = tracker[Util.charToIndex(i)];
            double freq = letterCount / totalNumberOfLetters;
            table.setFrequency(i, freq);
        }

        return table;


        //</editor-fold> // END OF YOUR CODE

    }

}

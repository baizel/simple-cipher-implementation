package uk.ac.ncl.undergraduate.modules.csc3621.cryptanalysis.easyfreq;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.*;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;
import static java.util.Comparator.comparingInt;


/**
 * This class is for frequency cryptanalysis of ciphertext.
 *
 * @author Changyu Dong
 * @author Roberto Metere
 * @author Your Name
 */
public class VigenereCryptanalysis {

    /**
     * The ciphertext (encryption of the plaintext).
     */
    private String ciphertext;

    /**
     * The plaintext (readable content).
     */
    private String plaintext;

    /**
     * The key such that the encryption of the plaintext with such key gives the
     * ciphertext.
     */
    private final StringBuffer key = new StringBuffer();

    /**
     * This variable is just to run the script interactive, that is with manual
     * tunes.
     */
    private boolean interactive;

    /**
     * INTERACTIVE means that you can manually tune the analysis and/or the
     * result.
     */
    public static final boolean INTERACTIVE = true;

    /**
     * AUTOMATIC means that the analysis will not ask any further information.
     */
    public static final boolean AUTOMATIC = false;

    /**
     * Create an new class to cryptanalyze texts.
     */
    public VigenereCryptanalysis() {
    }

    /**
     * Constructor with interactive choice.
     *
     * @param interactive whether it should ask for manual tuning or not
     */
    public VigenereCryptanalysis(boolean interactive) {
        this.interactive = interactive;
    }

    /**
     * Set the ciphertext to analyse.
     *
     * @param text the text to set as
     */
    public void setCiphertext(String text) {
        this.ciphertext = text;
    }

    /**
     * This method is to allow you to manually set the key can be used as a
     * subroutine in your cryptanalysis for manual adjustment
     */
    private void manualAdjustment() {

        int answer;
        int index;
        char letter;

        do {
            System.out.println("How do you want to change the key (1: insert, 2:replace, 3:delete, 4:nothing)? ");
            answer = Util.reader.nextInt();
        } while (answer < 1 || answer > 4);

        switch (answer) {
            case 1:
                System.out.println("Enter the index where you want to insert the key charater");
                index = Util.reader.nextInt();
                System.out.println("Enter the letter you want to insert");
                letter = Util.reader.next().charAt(0);
                if (index < 0 || index > this.key.length()) {
                    System.out.println("Index out of range");
                } else if (!Util.isValidLetter(letter)) {
                    System.out.println("key character must be a letter");
                } else {
                    this.key.insert(index, letter);

                }
                break;

            case 2:
                System.out.println("Enter the index of the character you want to replace");
                index = Util.reader.nextInt();
                System.out.println("Enter the new character");
                letter = Util.reader.next().charAt(0);
                if (index < 0 || index >= this.key.length()) {
                    System.out.println("Index out of range");
                } else if (!Util.isValidLetter(letter)) {
                    System.out.println("key character must be a letter");
                } else {
                    this.key.replace(index, index, Character.toString(letter));
                }
                break;

            case 3:
                System.out.println("Enter the index of the character you want to delete");
                index = Util.reader.nextInt();
                if (index < 0 || index >= this.key.length()) {
                    System.out.println("Index out of range");
                } else {

                    this.key.deleteCharAt(index);

                }
                break;

            default:
                break;
        }
    }


    /**
     * This method conducts cryptanalysis of the frequency of letters in the
     * ciphertext to retrieve the encryption key.
     *
     * <p>
     * TODO:
     * <ul>
     * <li>Conduct a frequency analysis of the internal buffer.
     * <li>Find the key. You should try your best to find the key based on your
     * analysis.
     * <li>Store the key in the class variable <code>this.key</code>.
     * </ul>
     *
     * <p>
     * Manual adjustment in the method is allowed but needs to be justified in
     * your report. You can create methods as you like.
     *
     * @return the key as result of the cryptanalysis
     */

    public String cryptanalysis() {
        // Please, do not remove the editor-fold comments.
        //<editor-fold defaultstate="collapsed" desc="Write your code here below!">
        //Exercise 2 part a
        System.out.println(VigenereCipher.encrypt("Harry Potter and the Sorcerer's Stone. \n" +
                "CHAPTER ONE - THE BOY WHO LIVED. \n" +
                "Mr. and Mrs. Dursley, of number four, Privet Drive, were proud to say that they were perfectly normal, thank you very much. They were the last people you'd expect to be involved in anything strange or mysterious, because they just didn't hold with such nonsense. \n" +
                "Mr. Dursley was the director of a firm called Grunnings, which made \n" +
                "drills. He was a big, beefy man with hardly any neck, although he did \n" +
                "have a very large mustache. Mrs. Dursley was thin and blonde and had \n" +
                "nearly twice the usual amount of neck, which came in very useful as she \n" +
                "spent so much of her time craning over garden fences, spying on the \n" +
                "neighbors. The Dursleys had a small son called Dudley and in their \n" +
                "opinion there was no finer boy anywhere. \n" +
                "The Dursleys had everything they wanted, but they also had a secret, and \n" +
                "their greatest fear was that somebody would discover it. They didn't \n" +
                "think they could bear it if anyone found out about the Potters. Mrs. \n" +
                "Potter was Mrs. Dursley's sister, but they hadn't met for several years; \n" +
                "in fact, Mrs. Dursley pretended she didn't have a sister, because her \n" +
                "sister and her good-for-nothing husband were as unDursleyish as it was \n" +
                "possible to be. The Dursleys shuddered to think what the neighbors would \n" +
                "say if the Potters arrived in the street. The Dursleys knew that the \n" +
                "Potters had a small son, too, but they had never even seen him. This boy \n" +
                "was another good reason for keeping the Potters away; they didn't want \n" +
                "Dudley mixing with a child like that. \n" +
                "When Mr. and Mrs. Dursley woke up on the dull, gray Tuesday our story \n" +
                "starts, there was nothing about the cloudy sky outside to suggest that \n" +
                "strange and mysterious things would soon be happening all over the \n" +
                "country. Mr. Dursley hummed as he picked out his most boring tie for \n" +
                "work, and Mrs. Dursley gossiped away happily as she wrestled a screaming \n" +
                "Dudley into his high chair. \n" +
                "None of them noticed a large, tawny owl flutter past the window. \n" +
                "At half past eight, Mr. Dursley picked up his briefcase, pecked Mrs. \n" +
                "Dursley on the cheek, and tried to kiss Dudley good-bye but missed, \n" +
                "because Dudley was now having a tantrum and throwing his cereal at the \n" +
                "walls. \"Little tyke,\" chortled Mr. Dursley as he left the house. He got \n" +
                "into his car and backed out of number four's drive. \n" +
                "It was on the corner of the street that he noticed the first sign of \n" +
                "something peculiar — a cat reading a map. For a second, Mr. Dursley \n" +
                "didn't realize what he had seen — then he jerked his head around to \n" +
                "look again. There was a tabby cat standing on the corner of Privet \n" +
                "Drive, but there wasn't a map in sight. What could he have been thinking \n" +
                "of? It must have been a trick of the light. Mr. Dursley blinked and \n" +
                "stared at the cat. It stared back. As Mr. Dursley drove around the \n" +
                "corner and up the road, he watched the cat in his mirror. It was now \n" +
                "reading the sign that said Privet Drive — no, looking at the sign; cats \n" +
                "couldn't read maps or signs. Mr. Dursley gave himself a little shake and \n" +
                "put the cat out of his mind. As he drove toward town he thought of \n" +
                "nothing except a large order of drills he was hoping to get that day. \n" +
                "But on the edge of town, drills were driven out of his mind by something \n" +
                "else. As he sat in the usual morning traffic jam, he couldn't help \n" +
                "noticing that there seemed to be a lot of strangely dressed people \n" +
                "about. People in cloaks. Mr. Dursley couldn't bear people who dressed in ","cunt"));

        //Break Cipher
        VigenereCipherBreaker breaker = new VigenereCipherBreaker(this.ciphertext);
        this.key.append(breaker.findKey());


        //</editor-fold> // END OF YOUR CODE
        // The following code allows you to manually adjust your result.
        if (this.interactive) {
            String answer;
            do {

                do {
                    System.out.println("Do you want to see the plaintext (Y/N)? ");
                    answer = Util.reader.next().toUpperCase();
                } while (!(answer.equals("Y") || answer.equals("N")));

                if (answer.equals("Y")) {
                    this.decrypt();
                    System.out.println(this.plaintext);
                }

                do {
                    System.out.println("Do you want to see the key (Y/N)? ");
                    answer = Util.reader.next().toUpperCase();
                } while (!(answer.equals("Y") || answer.equals("N")));

                if (answer.equals("Y")) {
                    System.out.println(this.key);
                }

                do {
                    System.out.println("Do you want to change the key (Y/N)? ");
                    answer = Util.reader.next().toUpperCase();
                } while (!(answer.equals("Y") || answer.equals("N")));

                if (answer.equals("Y")) {
                    this.manualAdjustment();
                }

                do {
                    System.out.println("Do you want to stop (Y/N)? ");
                    answer = Util.reader.next().toUpperCase();
                } while (!(answer.equals("Y") || answer.equals("N")));

            } while (!answer.equals("Y"));
        }

        return this.key.toString();
    }

    /**
     * This method reconstructs the plaintext from the ciphertext with the key.
     */
    public void decrypt() {
        this.plaintext = VigenereCipher.decrypt(this.ciphertext, this.key.toString());
    }

    /**
     * Show the results of the complete analysis.
     */
    public void showResult() {
        System.out.println("The key is " + this.key.toString());
        this.decrypt();
        System.out.println("The plaintext is:");
        System.out.println(this.plaintext);
    }

    /**
     * @param args the command line arguments
     * @throws java.io.IOException         errors reading from files
     * @throws java.net.URISyntaxException Errors in retrieving resources
     */
    public static void main(String[] args) throws IOException, URISyntaxException {
        String mainPath, ciphertextFilePath, ciphertext;
        VigenereCryptanalysis cryptanalysis;
        File solutionDirectory;
        String solutionKeyFilePath, solutionPlaintextFilePath;

        // Add argument -i at run to enable interactive mode (and disable automatic mode)
        if (0 < args.length && args[0].equals("-i")) {
            cryptanalysis = new VigenereCryptanalysis(INTERACTIVE);
        } else {
            cryptanalysis = new VigenereCryptanalysis(AUTOMATIC);
        }

        // Get resources
        mainPath = Paths.get(FrequencyCryptanalysis.class.getResource("/").toURI()).toString();
        ciphertextFilePath = mainPath + "/res/Exercise2Ciphertext.txt";
        solutionDirectory = new File(mainPath + "/solution2");
        solutionKeyFilePath = solutionDirectory + "/key.txt";
        solutionPlaintextFilePath = solutionDirectory + "/plaintext.txt";

        // Do the job
        ciphertext = Util.readFileToBuffer(ciphertextFilePath);
        cryptanalysis.setCiphertext(ciphertext);
        cryptanalysis.cryptanalysis();
        cryptanalysis.showResult();

        // Write solution in res path
        if (!solutionDirectory.exists()) {
            solutionDirectory.mkdir();
        }
        Util.printBufferToFile(cryptanalysis.key.toString(), solutionKeyFilePath);
        Util.printBufferToFile(cryptanalysis.plaintext, solutionPlaintextFilePath);
    }
}

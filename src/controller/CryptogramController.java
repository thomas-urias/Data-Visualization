/**
 * @author Thomas Urias
 * @prgm CryptogramController class
 * @descr: Program that controls the functionality of the cryptogram game,
 *        i.e. checking if the game is over, replacing player choices in
 *        decrypted strings, and getting the encrypted quote or the user's progress.
 */
package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import model.CryptogramModel;

public class CryptogramController {
    private CryptogramModel cryptogram;
	
    /**
     * Constructs a CryptogramController using a given CryptogramModel object.
     *
     * @param model the CryptogramModel instance to control
     */
    public CryptogramController(CryptogramModel model) {
        this.cryptogram = model;
    } 
	
    /**
     * Checks if the game is over by comparing the user's decrypted string
     * with the correct answer from the model.
     *
     * @return true if the user has completely solved the cryptogram, false otherwise
     */
    public boolean isGameOver() { 
        String playerAnswer = cryptogram.getDecryptedString();
        String correctAnswer = cryptogram.getAnswer();
        return playerAnswer.equals(correctAnswer);
    }
	
    /**
     * Makes a replacement in the decryption key by calling
     * {@link CryptogramModel#setReplacement(char, char)}.
     *
     * @param letterToReplace   the letter in the encrypted string the user wants to replace
     * @param replacementLetter the letter chosen to replace {@code letterToReplace}
     */
    public void makeReplacement(char letterToReplace, char replacementLetter) { 
        cryptogram.setReplacement(letterToReplace, replacementLetter);
    }
	
    /**
     * Gets the cryptogram's encrypted quote.
     *
     * @return the encrypted quote string
     */
    public String getEncryptedQuote() {  
        return cryptogram.getEncryptedString();
    }
	
    /**
     * Gets the user's progress in decrypting the cryptogram.
     *
     * @return a string showing the letters the user has placed so far
     */
    public String getUsersProgress() { 
        return cryptogram.getDecryptedString();
    }
	
    /**
     * Provides a hint to the player by revealing one correct mapping
     * that has not yet been guessed.
     *
     * This method chooses a random position in the answer and replaces
     * the encrypted letter with the correct solution letter.
     */
    public void getHint() {
        String answer = cryptogram.getAnswer();
        String encrypted = cryptogram.getEncryptedString();
        String userProgress = cryptogram.getDecryptedString();

        // Collect unsolved indices
        ArrayList<Integer> unsolvedIndices = new ArrayList<>();
        for (int i = 0; i < answer.length(); i++) {
            if (Character.isLetter(answer.charAt(i)) && userProgress.charAt(i) != answer.charAt(i)) {
                unsolvedIndices.add(i);
            }
        }

        // No available hints
        if (unsolvedIndices.isEmpty()) {
            System.out.println("No hints left — you already solved it!");
            return;
        }

        // Pick one random unsolved letter
        Random rand = new Random();
        int randomIndex = unsolvedIndices.get(rand.nextInt(unsolvedIndices.size()));

        char encryptedLetter = encrypted.charAt(randomIndex);
        char correctLetter = answer.charAt(randomIndex);

        // Apply the correct mapping
        cryptogram.setReplacement(encryptedLetter, correctLetter);
    }

	
    /**
     * Prompts the user for the letter they want to replace in the encrypted quote.
     *
     * @param s the scanner used to read input from the user
     * @return the character selected by the user
     */
    public char getReplacedLetter(Scanner s) {
        System.out.println("Enter a letter to replace: ");
        String selectedLetter = s.next().toUpperCase();
        return selectedLetter.charAt(0);
    }
	
    /**
     * Prompts the user for the letter that will replace the previously chosen letter.
     *
     * @param s the scanner used to read input from the user
     * @return the replacement character selected by the user
     */
    public char getLetterReplacement(Scanner s) {
        System.out.println("Enter a letter to replace it with: ");
        String selectedLetter = s.next().toUpperCase();
        return selectedLetter.charAt(0);
    }
	
    /**
     * Checks the frequency of each letter in the encrypted quote.
     *
     * @return a map of letters (A–Z) to their occurrence count in the encrypted string
     */
    public HashMap<Character, Integer> checkFreq() {
        HashMap<Character, Integer> letterFreq = new HashMap<>();
        String encryptedQuote = getEncryptedQuote();
		
        for (char letter = 'A'; letter <= 'Z'; letter++) {
            letterFreq.put(letter, 0);
            for (int i = 0; i < encryptedQuote.length(); i++) {
                if (letter == encryptedQuote.charAt(i)) {
                    Integer numberOfLetters = letterFreq.get(encryptedQuote.charAt(i));
                    letterFreq.put(letter, numberOfLetters + 1);
                }
            }
        }
        return letterFreq;
    }
    
    public ArrayList<String> getFormattedFreq() {
        HashMap<Character, Integer> letterFreq = checkFreq();
        ArrayList<String> lines = new ArrayList<>();
        StringBuilder line = new StringBuilder();
        int count = 0;

        for (char letter = 'A'; letter <= 'Z'; letter++) {
            line.append(letter).append(":").append(letterFreq.get(letter)).append("  ");
            count++;
            if (count % 7 == 0) {
                lines.add(line.toString());
                line.setLength(0);
            }
        }

        if (line.length() > 0) {
            lines.add(line.toString());
        }

        return lines;
    }
    
    public CryptogramModel newPuzzle() {
        // Create a completely new model (new random quote, new keys)
        this.cryptogram = new CryptogramModel();
        return cryptogram;
    }
}

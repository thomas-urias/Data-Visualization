/**
 * @author Thomas Urias
 * @prgm CryptogramModel class
 * @descr Program that operates the cryptogram in the cryptogram game.
 *        Initializes and creates random HashMaps of encryptionKey and decryptionKey
 *        through setter functions. Also controls replacement of user choices in HashMaps,
 *        as well as providing getter functions for encryptionKey and decryptionKey Strings.
 */
package model;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CryptogramModel extends Observable {
    // private variables to store the answer, encryption key, and decryption key
    private char[] answer;
    private HashMap<Character, Character> encryptionKey;
    private HashMap<Character, Character> decryptionKey;

    /**
     * Constructs a CryptogramModel.
     *
     * Reads a random line from the quotes.txt file, initializes the answer,
     * creates the encryption key, and sets the decryption key.
     */
    public CryptogramModel() { 
        answer = generateRandomQuote().toCharArray();
        encryptionKey = setEncryptionKey(createRandomAlphabet());
        decryptionKey = setDecryptionKey();
    }
	
    /**
     * Generates a random quote from the provided text file.
     *
     * @return a randomly selected quote as an uppercase string,
     *         or an error message if the file is not found
     */
    private String generateRandomQuote() {
        Random randomInt = new Random();
        File quotes = new File("quotes.txt");
		
        try {
            Scanner readQuote = new Scanner(quotes);
            ArrayList<String> listOfQuotes = new ArrayList<>();
			
            while (readQuote.hasNext()) {
                listOfQuotes.add(readQuote.nextLine());
            }	
			
            int lineNum = randomInt.nextInt(listOfQuotes.size());
            String randomQuote = listOfQuotes.get(lineNum);
			
            readQuote.close();
            return randomQuote.toUpperCase();
        } catch (FileNotFoundException e) {
            return "Error: File not found";
        }
    }
    
    /**
     * Generates a randomized alphabet list.
     *
     * @return a shuffled list of alphabet characters A–Z
     */
    private List<Character> createRandomAlphabet() {
        List<Character> cryptogramKeys = new ArrayList<>();
        for (char ch = 'A'; ch <= 'Z'; ch++) {
            cryptogramKeys.add(ch);
        }
        Collections.shuffle(cryptogramKeys);	
        return cryptogramKeys;
    }
	
    /**
     * Creates the encryption key mapping.
     *
     * <p>Iterates through characters in the answer and assigns unique mappings.
     * Special characters map to themselves.</p>
     *
     * @param alphabet a shuffled list of characters used for the mapping
     * @return the encryption key as a HashMap
     */
    private HashMap<Character, Character> setEncryptionKey(List<Character> alphabet) {
        encryptionKey = new HashMap<>();
        int keysIndex = 0;

        for (char ch : answer) {
            if (!Character.isLetterOrDigit(ch)) {
                encryptionKey.put(ch, ch);
                continue;
            }
            if (!encryptionKey.containsKey(ch)) {
                char currentKeyChar;
                do {
                    if (keysIndex >= alphabet.size()) {
                        keysIndex = 0;
                    }
                    currentKeyChar = alphabet.get(keysIndex);
                    keysIndex++;
                } while (encryptionKey.containsValue(currentKeyChar) || currentKeyChar == ch);

                encryptionKey.put(ch, currentKeyChar);
            }
        }
        return encryptionKey;
    } 
	
    /**
     * Creates the decryption key HashMap.
     *
     * <p>Letters are initially mapped to spaces, while special characters
     * map to themselves.</p>
     *
     * @return the decryption key as a HashMap
     */
    private HashMap<Character, Character> setDecryptionKey() {
        decryptionKey = new HashMap<>();
		
        for (char ch : getEncryptedString().toCharArray()) {
            if (Character.isLetterOrDigit(ch)) {
                decryptionKey.put(ch, ' ');
            } else {
                decryptionKey.put(ch, ch);
            }
        }
        return decryptionKey;
    }
	
    /**
     * Places the user's guess in the decryption key.
     *
     * @param encryptedChar   the character in the encrypted string to replace
     * @param replacementChar the character the user wants to substitute
     */
    public void setReplacement(char encryptedChar, char replacementChar) {
        decryptionKey.put(encryptedChar, replacementChar);
        
        setChanged();              
        notifyObservers(); 
    }
	
    /**
     * Builds the encrypted string using the encryption key.
     *
     * @return the encrypted version of the answer
     */
    public String getEncryptedString() {
        StringBuilder buildString = new StringBuilder();
        
        for (char ch : answer) {
            char encryptedCh = encryptionKey.get(ch);
            buildString.append(encryptedCh); 
        }
        return buildString.toString();
    }
	
    /**
     * Builds the decrypted string using the decryption key.
     *
     * @return the current decrypted version of the encrypted string
     */
    public String getDecryptedString() {
        StringBuilder buildString = new StringBuilder();
        
        for (char ch : getEncryptedString().toCharArray()) {
            char decryptedCh = decryptionKey.get(ch);
            buildString.append(decryptedCh); 
        }
        return buildString.toString();
    }
	
    /**
     * Gets the answer string.
     *
     * @return the correct answer as a string
     */
    public String getAnswer() {
        return new String(answer);
    }
}

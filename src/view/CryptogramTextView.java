/**
 * @author Thomas Urias
 * @prgm Cryptogram game driver class
 * @descr Program that runs the main function of the cryptogram game
 * 		  by creating instances of CryptogramModel and CryptogramController
 * 		  classes and prompting for user input 
 */
package view;

import model.CryptogramModel;
import controller.CryptogramController;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

/**
 * 
 */
public class CryptogramTextView implements Observer {
	static CryptogramModel model;
	static CryptogramController controller;
	/**
	 * Main program for the Cryptogram game 
	 * 
	 * The main function of the cryptogram game creates instances
	 * of {@link model.CryptogramModel} and {@link controller.CryptogramController}
	 * classes and prompting for user input
	 */
	public static void main(String[] args) {
		model = new CryptogramModel();
		controller = new CryptogramController(model);
		model.addObserver(new CryptogramTextView());
		
		Scanner userInput = new Scanner(System.in);
		
		String gameState = "ACTIVE";
		char replacedLetter;
		char letterReplacement;
		
		displayStatus(controller);
		while (!gameState.equals("STOP")) {
		    System.out.println("\nEnter a command (type help to see commands): ");
		    String choice = userInput.nextLine().toLowerCase().trim();

		    if (choice.isEmpty()) {
		        continue;
		    }

		    switch(choice) {
		        case "help":
		            displayCommands();
		            break;
		        case "replace x with y":
		            replacedLetter = controller.getReplacedLetter(userInput);
		            letterReplacement = controller.getLetterReplacement(userInput);
		            controller.makeReplacement(replacedLetter, letterReplacement);
		            break;
		        case "x=y":
		            replacedLetter = controller.getReplacedLetter(userInput);
		            letterReplacement = controller.getLetterReplacement(userInput);
		            controller.makeReplacement(replacedLetter, letterReplacement);
		            break;
		        case "freq":
		            displayCharFreq(controller);
		            break;
		        case "hint":
		            controller.getHint();
		            break;
		        case "exit":
		            gameState = "STOP";
		            break;
		        default:
		            System.out.println("\nERROR: INVALID COMMAND");
		    }
		}
	}
	
	/**
     * Displays the frequency of each character in the encrypted quotation.
     *
     * @param controller the cryptogram controller used to check frequencies
     */
	public static void displayCharFreq(CryptogramController controller) {
	    ArrayList<String> freqLines = controller.getFormattedFreq();
	    
	    System.out.println("\nLetter Frequencies:");
	    for (String line : freqLines) {
	        System.out.println(line);
	    }
	}
	
	/**
     * Displays the encrypted quote along with the user's current progress.
     */
    public static void displayStatus(CryptogramController controller) {
        System.out.println();

        String encrypted = controller.getEncryptedQuote();
        String progress = controller.getUsersProgress();
        int lineLength = 80;

        for (int i = 0; i < encrypted.length(); i += lineLength) {
            int end = i + lineLength;
            if (end > encrypted.length()) {
                end = encrypted.length();
            }

            // Print the encrypted text chunk
            System.out.println(encrypted.substring(i, end));
            // Print the corresponding progress chunk
            System.out.println(progress.substring(i, end));
        }
    }
	
	/**
	 * This methods displays the available input commands for the Cryptogram game 
	 */
	public static void displayCommands() {
		System.out.println(
				"\nReplace X with Y  \t- replace letter X with letter Y in the cryptogram"
				+ "\nX = Y			- shortcut for the above command"
				+ "\nfreq 			- Display the letter frequencies in the encrypted quotation"
				+ "\nhint 			- Display one correct mapping that has not yet been guessed"
				+ "\nexit 			- Ends the game");		
		
	}

	@Override
	public void update(Observable o, Object arg) {
	    displayStatus(controller);
	}

}



 
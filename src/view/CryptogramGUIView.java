package view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import model.CryptogramModel;
import controller.CryptogramController;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * @desc The main GUI view for the Cryptogram game application.
 * This class creates and manages the user interface for playing cryptogram puzzles,
 * including the encrypted text display, user input fields, and control buttons.
 * 
 * @author Thomas Urias
 */
@SuppressWarnings("deprecation")
public class CryptogramGUIView extends Application implements Observer {
	
	/** The data model for the cryptogram puzzle */
	private CryptogramModel model;
	
	/** The controller that handles game logic */
	private CryptogramController controller;
	
	/** Grid pane containing the puzzle text fields and labels */
	private GridPane grid;
	
	/** Main window layout container */
	private BorderPane window;
	
	/** The scene displayed in the application window */
	private Scene scene;
	
	/** Checkbox to toggle frequency analysis display */
	private CheckBox freqCheckBox;
	
	/** Button to generate a new puzzle */
	private Button newPuzzle;
	
	/** Button to get a hint for the current puzzle */
	private Button hint;
	
	/** List of all input text fields in the puzzle grid */
	private ArrayList<TextField> inputList;
	
	/**
	 * Initializes and displays the main application window.
	 * Sets up the model, controller, and all UI components.
	 * 
	 * @param stage The primary stage for this application
	 */
	@Override
	public void start(Stage stage) {
		model = new CryptogramModel();
		controller = new CryptogramController(model);
		inputList = new ArrayList<TextField>();
		
		model.addObserver(this);
		
		stage.setTitle("Cryptogram Game");
		window = new BorderPane();
		scene = new Scene(window, 900, 400);
		stage.setScene(scene);
		stage.show();
		
		grid = new GridPane();
		window.setLeft(grid);
		buildGridPlane();
		
		buildRightPanel();
	}

	/**
	 * Constructs the right panel containing game controls.
	 * Includes buttons for new puzzle, hints, and frequency analysis checkbox.
	 * Sets up event handlers for all control elements.
	 */
	private void buildRightPanel() {
		newPuzzle = new Button("New Puzzle");
		hint = new Button("Hint");
		freqCheckBox = new CheckBox("Check Frequency");
		
		VBox freqBox = new VBox();
		window.setBottom(freqBox);
		
		// Event handler for generating a new puzzle
		newPuzzle.setOnAction(event -> {
			model = controller.newPuzzle();
			model.addObserver(this);
			
			grid.getChildren().clear();
			inputList.clear();
			buildGridPlane();
		});
		
		// Event handler for requesting a hint
		hint.setOnAction(event -> {
			controller.getHint();	
		});
		
		// Event handler for toggling frequency display
		freqCheckBox.setOnAction(event -> {
		    if (freqCheckBox.isSelected()) {
		        displayFreq(freqBox);
		    } else {
		        freqBox.getChildren().clear();
		    }
		});

		VBox rightPanel = new VBox();
		rightPanel.setAlignment(Pos.TOP_CENTER);
		rightPanel.getChildren().addAll(newPuzzle, hint, freqCheckBox);
		window.setRight(rightPanel);
	}

	/**
	 * Builds the grid pane containing the cryptogram puzzle.
	 * Creates text fields for user input and labels for encrypted characters.
	 * Arranges elements in rows of up to 30 characters each.
	 */
	public void buildGridPlane() {	
		String decrypted = controller.getUsersProgress();
	    String encrypted = controller.getEncryptedQuote();
	    int col = 0;
	    int row = 0;
	    
	    for (int i = 0; i < encrypted.length(); i++) {
	    	if (col == 30) {
	    		col = 0;
	    		row += 2;
	    	}
	    	
	    	char encryptedChar = encrypted.charAt(i);
	    	char decryptedChar = decrypted.charAt(i);
	    	
	    	TextField input = buildTextField(encryptedChar, decryptedChar);
	    	grid.add(input, col, row);

	    	Label encryptedLabel = new Label(String.valueOf(encryptedChar));
	    	encryptedLabel.setPrefWidth(25);
	    	encryptedLabel.setAlignment(Pos.CENTER);
    		grid.add(encryptedLabel, col, row + 1);
    		
    		col++;
	    }
	}
	
	/**
	 * Creates and configures a text field for a single character in the puzzle.
	 * Sets up styling, initial text, and key event handling.
	 * 
	 * @param encryptedChar The encrypted character to display below the field
	 * @param decryptedChar The current decrypted/guessed character
	 * @return A configured TextField ready to be added to the grid
	 */
	private TextField buildTextField(char encryptedChar, char decryptedChar) {
		TextField input = new TextField();
    	input.setPrefWidth(25);
		input.setAlignment(Pos.CENTER);
		
		// Disable field for non-letter characters (spaces, punctuation)
    	if (!Character.isLetter(encryptedChar)) {
    		input.setText(String.valueOf(encryptedChar));
    		input.setDisable(true);
    	} else {
    		input.setText(String.valueOf(decryptedChar));
    	}
    	
    	// Event handler for character input
    	input.setOnKeyTyped(event -> {
    	    String typedChar = event.getCharacter();

    	    if (typedChar.isEmpty()) {
    	        return; 
    	    }

    	    char c = typedChar.charAt(0);
    	    if (!Character.isLetter(c)) {
    	        return; 
    	    }

    	    controller.makeReplacement(encryptedChar, Character.toUpperCase(c));
    	});
		
    	inputList.add(input);
    	return input;
	}
	
	/**
	 * Displays letter frequency analysis in the provided VBox.
	 * Shows how often each letter appears in the encrypted text.
	 * 
	 * @param freqBox The VBox container to display frequency data
	 */
	private void displayFreq(VBox freqBox) {
	    freqBox.getChildren().clear();
	    ArrayList<String> freqLines = controller.getFormattedFreq();

	    for (String line : freqLines) {
	        freqBox.getChildren().add(new Label(line));
	    }
	}

	/**
	 * Updates the view when the model changes.
	 * Refreshes all text fields with the current decryption progress.
	 * This method is called automatically when the Observable model notifies observers.
	 * 
	 * @param o The Observable object (the model)
	 * @param arg An optional argument passed by the Observable
	 */
	@Override
	public void update(Observable o, Object arg) {
	    String decrypted = controller.getUsersProgress();

	    for (int i = 0; i < inputList.size() && i < decrypted.length(); i++) {
	        TextField input = inputList.get(i);
	        char c = decrypted.charAt(i);

	        if (!input.isDisabled()) {
	            input.setText(String.valueOf(c));
	        }
	    }
	}
}
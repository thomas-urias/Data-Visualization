import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import model.CryptogramModel;
import controller.CryptogramController;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Comprehensive test class for the Cryptogram game.
 * Tests all functionality of CryptogramModel and CryptogramController classes.
 * 
 * @author Thomas Urias
 * @version 1.0
 */
class CryptogramTests {

	private CryptogramModel model;
    private CryptogramController controller;

	@BeforeEach
	public void setUp() {
		model = new CryptogramModel();
		controller = new CryptogramController(model);
	}
	
	@Test
	void testSetReplacement() {
		String beforeReplacement = model.getDecryptedString();
		// pick first character that is a letter/digit
		char encryptedChar = 0;
		for (char c : model.getEncryptedString().toCharArray()) {
			if (Character.isLetterOrDigit(c)) {
				encryptedChar = c;
				break;
			}
		}
		model.setReplacement(encryptedChar, 'O');
		String afterReplacement = model.getDecryptedString();

		assertNotEquals(afterReplacement, beforeReplacement,
			"Set replacement should update the decrypted string");
	}
	
	@Test
	void testSetReplacementUpdatesAllOccurrences() {
		String encrypted = model.getEncryptedString();
		char testChar = 0;
		
		for (char c : encrypted.toCharArray()) {
			if (Character.isLetter(c)) {
				testChar = c;
				break;
			}
		}
		
		model.setReplacement(testChar, 'X');
		String decrypted = model.getDecryptedString();
		
		// Check all occurrences were replaced
		for (int i = 0; i < encrypted.length(); i++) {
			if (encrypted.charAt(i) == testChar) {
				assertEquals('X', decrypted.charAt(i), 
					"All occurrences of encrypted letter should be replaced");
			}
		}
	}

	@Test
	void testGetDecryptedString() {
		assertNotNull(model.getDecryptedString(), "Decrypted string should not be null");
		assertEquals(model.getEncryptedString().length(), model.getDecryptedString().length(),
			"Decrypted string should be the same length as the encrypted string");
	}
	
	@Test
	void testGetDecryptedStringInitiallySpaces() {
		String decrypted = model.getDecryptedString();
		String encrypted = model.getEncryptedString();
		
		for (int i = 0; i < encrypted.length(); i++) {
			if (Character.isLetter(encrypted.charAt(i))) {
				assertEquals(' ', decrypted.charAt(i), 
					"Initial decrypted letters should be spaces");
			}
		}
	}

	@Test
	void testGetAnswer() {
		assertNotNull(model.getAnswer(), "Answer should be a random quote, not null");
		assertFalse(model.getAnswer().isEmpty(), "Answer shouldn't be an empty String");
	}
	
	@Test
	void testGetAnswerIsUppercase() {
		String answer = model.getAnswer();
		for (char c : answer.toCharArray()) {
			if (Character.isLetter(c)) {
				assertTrue(Character.isUpperCase(c), "Answer should be all uppercase");
			}
		}
	}
	
	@Test
	void testGetEncryptedString() {
		String encrypted = model.getEncryptedString();
		assertNotNull(encrypted, "Encrypted string should not be null");
		assertFalse(encrypted.isEmpty(), "Encrypted string should not be empty");
		assertEquals(model.getAnswer().length(), encrypted.length(), 
			"Encrypted string should match answer length");
	}
	
	@Test
	void testEncryptedStringDifferentFromAnswer() {
		String encrypted = model.getEncryptedString();
		String answer = model.getAnswer();
		assertNotEquals(encrypted, answer, "Encrypted string should differ from answer");
	}
	
	@Test
	void testEncryptionPreservesNonLetters() {
		String encrypted = model.getEncryptedString();
		String answer = model.getAnswer();
		
		for (int i = 0; i < answer.length(); i++) {
			if (!Character.isLetterOrDigit(answer.charAt(i))) {
				assertEquals(answer.charAt(i), encrypted.charAt(i), 
					"Non-letter characters should be preserved in encryption");
			}
		}
	}
	
	@Test
	void testNoSelfEncryption() {
		String encrypted = model.getEncryptedString();
		String answer = model.getAnswer();
		
		for (int i = 0; i < answer.length(); i++) {
			if (Character.isLetter(answer.charAt(i))) {
				assertNotEquals(answer.charAt(i), encrypted.charAt(i), 
					"Letters should not be encrypted as themselves");
			}
		}
	}
	
	@Test
	void testConsistentEncryption() {
		String encrypted = model.getEncryptedString();
		String answer = model.getAnswer();
		HashMap<Character, Character> mapping = new HashMap<>();
		
		for (int i = 0; i < answer.length(); i++) {
			char ansChar = answer.charAt(i);
			char encChar = encrypted.charAt(i);
			
			if (Character.isLetter(ansChar)) {
				if (mapping.containsKey(ansChar)) {
					assertEquals(mapping.get(ansChar), encChar, 
						"Same answer letter should always encrypt to same letter");
				} else {
					mapping.put(ansChar, encChar);
				}
			}
		}
	}
	
	@Test
	void testOneToOneEncryption() {
		String encrypted = model.getEncryptedString();
		String answer = model.getAnswer();
		HashMap<Character, Character> reverseMapping = new HashMap<>();
		
		for (int i = 0; i < answer.length(); i++) {
			char ansChar = answer.charAt(i);
			char encChar = encrypted.charAt(i);
			
			if (Character.isLetter(ansChar)) {
				if (reverseMapping.containsKey(encChar)) {
					assertEquals(reverseMapping.get(encChar), ansChar, 
						"Each encrypted letter should map to exactly one answer letter");
				} else {
					reverseMapping.put(encChar, ansChar);
				}
			}
		}
	}
	
	@Test
	void testGetEncryptedQuote() {
		assertEquals(model.getEncryptedString(), controller.getEncryptedQuote(),
			"Controller should return the same encrypted string as the model");
	}

	@Test
	void testUsersProgress() {
		assertEquals(model.getDecryptedString(), controller.getUsersProgress(),
			"Controller should return the same decrypted string as the model");
	}
	
	@Test
	void testMakeReplacement() {
		String encrypted = controller.getEncryptedQuote();
		char encChar = 0;
		
		for (char c : encrypted.toCharArray()) {
			if (Character.isLetter(c)) {
				encChar = c;
				break;
			}
		}
		
		String before = controller.getUsersProgress();
		controller.makeReplacement(encChar, 'Z');
		String after = controller.getUsersProgress();
		
		assertNotEquals(before, after, "makeReplacement should update user progress");
		
		for (int i = 0; i < encrypted.length(); i++) {
			if (encrypted.charAt(i) == encChar) {
				assertEquals('Z', after.charAt(i), "Replacement should appear in progress");
			}
		}
	}

	@Test
	void testIsGameOverFalse() {
		assertFalse(controller.isGameOver(), "Game should not be over initially");
	}

	@Test
	void testIsGameOverTrue() {
		String answer = model.getAnswer();
		String encrypted = model.getEncryptedString();

		for (int i = 0; i < answer.length(); i++) {
			controller.makeReplacement(encrypted.charAt(i), answer.charAt(i));
		}
		assertTrue(controller.isGameOver(), 
			"Game should be over when decrypted string matches answer");
	}
	
	@Test
	void testIsGameOverWithPartialSolution() {
		String encrypted = model.getEncryptedString();
		char firstLetter = 0;
		
		for (char c : encrypted.toCharArray()) {
			if (Character.isLetter(c)) {
				firstLetter = c;
				break;
			}
		}
		
		controller.makeReplacement(firstLetter, 'A');
		assertFalse(controller.isGameOver(), 
			"Game should not be over with partial solution");
	}

	@Test
	void testGetHintWorks() {
		String before = model.getDecryptedString();
		controller.getHint();
		String after = model.getDecryptedString();
		
		assertNotEquals(before, after, "getHint should update the decrypted string");
	}
	
	@Test
	void testGetHintRevealsCorrectLetter() {
		controller.getHint();
		
		String progress = controller.getUsersProgress();
		String answer = model.getAnswer();
		String encrypted = controller.getEncryptedQuote();
		
		boolean foundCorrectLetter = false;
		for (int i = 0; i < progress.length(); i++) {
			if (Character.isLetter(encrypted.charAt(i)) && progress.charAt(i) != ' ') {
				if (progress.charAt(i) == answer.charAt(i)) {
					foundCorrectLetter = true;
					break;
				}
			}
		}
		
		assertTrue(foundCorrectLetter, "Hint should reveal at least one correct letter");
	}
	
	@Test
	void testMultipleHints() {
		String first = controller.getUsersProgress();
		controller.getHint();
		String second = controller.getUsersProgress();
		controller.getHint();
		String third = controller.getUsersProgress();
		
		assertNotEquals(first, second, "First hint should change progress");
		assertTrue(countNonSpaces(third) >= countNonSpaces(second), 
			"Hints should not decrease progress");
	}

	@Test
	void testCheckFreq() {
	    HashMap<Character, Integer> freqs = controller.checkFreq();

	    assertNotNull(freqs, "Frequency map should not be null");
	    assertEquals(26, freqs.size(), 
	    	"Freq map should contain 26 letters (one for each in the alphabet)");

	    for (int count : freqs.values()) {
	        assertTrue(count >= 0, "Letter count should never be below zero");
	    }
	}
	
	@Test
	void testCheckFreqAccuracy() {
		String encrypted = controller.getEncryptedQuote();
		HashMap<Character, Integer> freqs = controller.checkFreq();
		
		char testLetter = 'A';
		int manualCount = 0;
		for (char c : encrypted.toCharArray()) {
			if (c == testLetter) {
				manualCount++;
			}
		}
		
		assertEquals(manualCount, freqs.get(testLetter), 
			"Frequency count should match manual count");
	}
	
	@Test
	void testGetFormattedFreq() {
		ArrayList<String> formatted = controller.getFormattedFreq();
		
		assertNotNull(formatted, "Formatted frequency should not be null");
		assertFalse(formatted.isEmpty(), "Formatted frequency should not be empty");
		
		for (String line : formatted) {
			assertTrue(line.matches(".*[A-Z]:\\d+.*"), 
				"Each line should contain letter:count format");
		}
	}
	
	@Test
	void testGetFormattedFreqContainsAllLetters() {
		ArrayList<String> formatted = controller.getFormattedFreq();
		String combined = String.join(" ", formatted);
		
		for (char c = 'A'; c <= 'Z'; c++) {
			assertTrue(combined.contains(c + ":"), 
				"Formatted output should contain all letters A-Z");
		}
	}
	
	@Test
	void testNewPuzzle() {
		String firstEncrypted = controller.getEncryptedQuote();
		String firstAnswer = model.getAnswer();
		
		model = controller.newPuzzle();
		controller = new CryptogramController(model);
		
		String secondEncrypted = controller.getEncryptedQuote();
		String secondAnswer = model.getAnswer();
		
		assertNotEquals(firstEncrypted, secondEncrypted, 
			"New puzzle should have different encrypted text");
		assertNotEquals(firstAnswer, secondAnswer, 
			"New puzzle should have different answer");
	}
	
	@Test
	void testNewPuzzleResetsProgress() {
		String encrypted = controller.getEncryptedQuote();
		char firstLetter = 0;
		for (char c : encrypted.toCharArray()) {
			if (Character.isLetter(c)) {
				firstLetter = c;
				break;
			}
		}
		controller.makeReplacement(firstLetter, 'X');
		
		model = controller.newPuzzle();
		controller = new CryptogramController(model);
		
		String newProgress = controller.getUsersProgress();
		String newEncrypted = controller.getEncryptedQuote();
		
		for (int i = 0; i < newEncrypted.length(); i++) {
			if (Character.isLetter(newEncrypted.charAt(i))) {
				assertEquals(' ', newProgress.charAt(i), 
					"New puzzle should reset all progress to spaces");
			}
		}
	}

	@Test
	void testGetLetterReplacement() {
		Scanner s = new Scanner("i\n");
		char result = controller.getLetterReplacement(s);
		assertEquals('I', result, "Should convert input to uppercase");
	}
	
	@Test
	void testGetLetterReplacementUppercase() {
		Scanner s = new Scanner("Z\n");
		char result = controller.getLetterReplacement(s);
		assertEquals('Z', result, "Should handle uppercase input");
	}

	@Test
	void testGetReplacedLetter() {
		Scanner s = new Scanner("i\n");
		char result = controller.getReplacedLetter(s);
		assertEquals('I', result, "Should convert input to uppercase");
	}
	
	@Test
	void testGetReplacedLetterUppercase() {
		Scanner s = new Scanner("B\n");
		char result = controller.getReplacedLetter(s);
		assertEquals('B', result, "Should handle uppercase input");
	}
	
	@Test
	void testFullGameWorkflow() {
		// Verify initial state
		assertFalse(controller.isGameOver());
		
		String encrypted = controller.getEncryptedQuote();
		String answer = model.getAnswer();
		
		ArrayList<Character> letters = new ArrayList<>();
		for (char c : encrypted.toCharArray()) {
			if (Character.isLetter(c) && !letters.contains(c)) {
				letters.add(c);
				if (letters.size() == 3) break;
			}
		}

		for (char encChar : letters) {
			for (int i = 0; i < encrypted.length(); i++) {
				if (encrypted.charAt(i) == encChar) {
					controller.makeReplacement(encChar, answer.charAt(i));
					break;
				}
			}
		}
		
		String progress = controller.getUsersProgress();
		assertTrue(countNonSpaces(progress) >= 3, 
			"Should have at least 3 non-space characters after replacements");
	}
	
	@Test
	void testMultipleReplacementsOnSameLetter() {
		String encrypted = controller.getEncryptedQuote();
		char testLetter = 0;
		
		for (char c : encrypted.toCharArray()) {
			if (Character.isLetter(c)) {
				testLetter = c;
				break;
			}
		}
		
		controller.makeReplacement(testLetter, 'A');
		String first = controller.getUsersProgress();
		
		controller.makeReplacement(testLetter, 'B');
		String second = controller.getUsersProgress();
		
		assertNotEquals(first, second, "Should be able to change replacement");
		
		// Verify new replacement
		for (int i = 0; i < encrypted.length(); i++) {
			if (encrypted.charAt(i) == testLetter) {
				assertEquals('B', second.charAt(i), "Should show new replacement");
			}
		}
	}
	
	private int countNonSpaces(String str) {
		int count = 0;
		for (char c : str.toCharArray()) {
			if (c != ' ') {
				count++;
			}
		}
		return count;
	}
}
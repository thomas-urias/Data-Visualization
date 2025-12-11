import view.CryptogramGUIView;
import view.CryptogramTextView;
import javafx.application.Application;
import java.util.Scanner;


public class Cryptogram {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		try {			
			System.out.println("Enter -text for Text View or -gui for GUI View");
			String choice = scanner.nextLine().trim();
			
			if(choice.equalsIgnoreCase("-text")) {
				CryptogramTextView.main(args);
			} else if(choice.equalsIgnoreCase("-gui")) {
				Application.launch(CryptogramGUIView.class, args);
			} else {
				throw new IllegalArgumentException("Invalid Selection: " + choice);
			}
		} finally {
			scanner.close();
		}
		
	}
}

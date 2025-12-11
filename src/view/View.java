package view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import model.InsertionSort;
import model.BubbleSort;
import model.LinkedList;
import model.Model;
import controller.Controller;
import model.Sorter;
import model.Queue;
/**
 * The View class contains the GUI of the data structures application 
 * using JavaFX to create each structure.
 * Implements Observer pattern to update when model is changed 
 * s
 * @author thomasurias
 */
@SuppressWarnings("deprecation")
public class View extends Application implements Observer {
    @SuppressWarnings("rawtypes")
	private Model model;
    @SuppressWarnings("rawtypes")
	private Controller controller;
    private Sorter sorter;
    
    private Timeline sortTimeline;
    private BorderPane root;
	private VBox structContainer;
	private HBox bottomBar;
	private GridPane structContainerLL;
	private Stage primaryStage;
	private String active;
	private int nodes = 0;
	
	@Override
	public void start(Stage stage) {
	    this.primaryStage = stage;
	    
	    root = new BorderPane();

	    structContainer = new VBox();
	    structContainerLL = new GridPane();
	    structContainerLL.setAlignment(Pos.TOP_LEFT);
	    structContainerLL.setHgap(20);
	    structContainerLL.setVgap(20);
	    structContainer.setAlignment(Pos.BOTTOM_CENTER);
	    root.setCenter(structContainer);

	    bottomBar = new HBox();
	    bottomBar.setAlignment(Pos.CENTER);
	    bottomBar.setSpacing(10);
	    bottomBar.setPadding(new Insets(10, 0, 10, 0));
	    root.setBottom(bottomBar);
	    
		MenuBar menuBar = new MenuBar();
		Menu menu = new Menu("File");
		MenuItem menuItem = new MenuItem("Save");
		menu.getItems().add(menuItem);
		menuBar.getMenus().add(menu);
		
		menuItem.setOnAction((event) -> {
			try {
				controller.save();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
        root.setTop(menuBar);
		
	    // Create the Scene once
	    Scene scene = new Scene(root, 600, 500);
	    stage.setScene(scene);
	    stage.setTitle("Data Structures Application");
	    stage.show();

	    // Show the main menu first
	    showMainMenu();
	}

    
    /**
     * Main Menu UI Implementation.
     * 
     * @Author Emmanuel
     */
    private void showMainMenu() {
    	VBox menuBox = new VBox(15); 
        menuBox.setAlignment(Pos.CENTER);

        Button selectBtn = new Button("Select Structures & Algorithms");
        selectBtn.setOnAction(e -> showSelectionMenu());

        Button quitBtn = new Button("Quit");
        quitBtn.setOnAction(e -> primaryStage.close());

        menuBox.getChildren().addAll(selectBtn, quitBtn);

        root.setCenter(menuBox);       
        bottomBar.getChildren().clear();
    }
    
    /**
     * Selection UI Implementation.
     * 
     * @Author Emmanuel
     */
    private void showSelectionMenu() {
    	VBox selectionBox = new VBox(20);
        selectionBox.setAlignment(Pos.CENTER);

        /* Buttons for each DSA. */
        Button stackBtn = new Button("Stack");
        Button queueBtn = new Button("Queue");
        Button llBtn = new Button("Linked List");
        Button insertionBtn = new Button("Insertion Sort");
        Button bubbleBtn = new Button("Bubble Sort");

        /* Each button return user back to visualization screen. */
        stackBtn.setOnAction(e -> {
            controller = new Controller<>("Stack");
            model = controller.getModel();
            model.addObserver(this);
            active = "Stack";
            showStructureScreen("Stack");
        });

        queueBtn.setOnAction(e -> {
            controller = new Controller<>("Queue");
            model = controller.getModel();
            model.addObserver(this);
            active = "Queue";
            showStructureScreen("Queue");
        });

        llBtn.setOnAction(e -> {
            controller = new Controller<>("LL");
            model = controller.getModel();
            model.addObserver(this);
            active = "LL";
            showStructureScreen("Linked List");
        });

        insertionBtn.setOnAction(e -> {
            controller = new Controller<>("Insertion");
            model = controller.getModel();
            model.addObserver(this);
            active = "Insertion";
            showStructureScreen("Insertion Sort");
        });

        bubbleBtn.setOnAction(e -> {
            controller = new Controller<>("Bubble");
            model = controller.getModel();
            model.addObserver(this);
            active = "Bubble";
            showStructureScreen("Bubble Sort");
        });
        
        Button compareBtn = new Button("Compare Sorting Algorithms");
        compareBtn.setOnAction(e -> showCompareScreen());

        /* Return to Main Menu button */
        Button mainMenuBtn = new Button("Return to Main Menu");
        mainMenuBtn.setOnAction(e -> showMainMenu());

        selectionBox.getChildren().addAll(
                stackBtn, queueBtn, llBtn, insertionBtn, bubbleBtn, compareBtn, mainMenuBtn
        );

        root.setCenter(selectionBox);      
        bottomBar.getChildren().clear();
    }

    /**
     * Set a Placeholder after choosing a DS.
     */
    private void showStructureScreen(String name) {
        bottomBar.getChildren().clear();
        addButton();
        removeButton();
        resetButton();
        
        if (!model.hasQueue() && !model.hasStack()) {  
            findButton();
        }

        if (model.hasQueue() || model.hasStack()) { 
            peekButton();
        }

        mainMenuButton();
        
        switch (name) {
        case ("Stack"):
		        root.setCenter(structContainer);
		        drawStack(structContainer);
		        break;
        	case ("Linked List"): 
            	root.setCenter(structContainerLL);
        		drawLL(structContainerLL);
        		break;
        	case ("Queue"): 
        		root.setCenter(structContainer);
        		drawQueue(structContainer);
        		break;
        	case ("Insertion Sort"): 
        		root.setCenter(structContainer);
        		drawSort(structContainer, model.getInsertionSort());
        		startAutoSort();
        		break;
        	case ("Bubble Sort"): 
        		root.setCenter(structContainer);
        		drawSort(structContainer, model.getBubbleSort());
        		startAutoSort();
        		break;
        }
    }

	/**
	 * This method creates the "Add" button on the bottom section of the BorderPane
	 * It also has an event handler when clicking the button. The event handler calls 
	 * the Controllers add method on click
	 * 
	 * @author thomasurias
	 */
	@SuppressWarnings("unchecked")
	private void addButton() {
		VBox addBox = new VBox();
		TextField text = new TextField();
		Button addButton = new Button();
		TextField indexBox = new TextField();
		Button prepend = new Button("Prepend");
		Button insert = new Button("Insert");
		
	    if (model.hasQueue() && active.equals("Queue")) {
	        text.setMaxWidth(40);
	        addButton.setText("Enqueue");
	        addBox.getChildren().addAll(text, addButton);
	    }else if (model.hasLinkedList() && active.equals("LL")) {
			text.setMaxWidth(60);
			text.setPromptText("Value");
			indexBox.setPrefWidth(47);
			indexBox.setPromptText("Index");
			addButton.setText("Append");
			HBox buttonBox = new HBox();
			HBox textFields = new HBox();
			TextField blankBox = new TextField();
			blankBox.setVisible(false);
			blankBox.setMaxWidth(65);
			textFields.getChildren().addAll(text, blankBox,indexBox);
			buttonBox.getChildren().addAll(addButton, prepend, insert);
			addBox.getChildren().addAll(textFields, buttonBox);
		}
	    else if(model.hasStack() && active.equals("Stack")){
		    text.setMaxWidth(45);
		    addButton.setText("Push");
		    addBox.getChildren().addAll(text, addButton);
		}else {
		    text.setMaxWidth(40);
		    addButton.setText("Push");
		    addBox.getChildren().addAll(text, addButton);
		}
				
		addButton.setOnAction(e -> {
	    	if(nodes > 31 && active.equals("LL")) return;
		    String input = text.getText();
		    if(input.equals("") || input.length() >= 10) return;

		    if(model.hasInsertionSort() || model.hasBubbleSort()) {
		        try {
		            int value = Integer.parseInt(input);
		            controller.add(value, false); 
		            startAutoSort();
		        } catch (NumberFormatException ex) {
		            Alert alert = new Alert(Alert.AlertType.ERROR);
		            alert.setTitle("Invalid Input");
		            alert.setContentText("Please enter a valid integer to append.");
		            alert.showAndWait();
		        }
		    } else {
		    	if(active.equals("LL")) nodes++;
		    	controller.add(input, false); 
		    }
		});
		
		prepend.setOnAction(e->{
	    	if(nodes == 32 && active.equals("LL")) return;
			if(text.getText().equals("") || text.getText().length() >= 10) return;
			controller.add(text.getText(), true);
			nodes++;
		});
		
		insert.setOnAction(e -> {
	    	if(nodes == 32 && active.equals("LL")) return;
			Integer idx = -1;
			try {
				idx = Integer.parseInt(indexBox.getText());
				if(idx >= 0) {
			        controller.insert(text.getText(), idx);
			        nodes++;
				}
			}catch(NumberFormatException ex) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setTitle("Invalid Input");
	            alert.setContentText("Please enter a valid integer for Index.");
	            alert.showAndWait();
			}
		});
	
	    bottomBar.getChildren().add(addBox);
	    bottomBar.setAlignment(Pos.BOTTOM_CENTER);
	    root.setBottom(bottomBar);
	}
	
	/**
	 * This method creates the "Remove" button on the bottom section of the BorderPane
	 * It also has an event handler when clicking the button. The event handler calls 
	 * the Controllers add method on click
	 * 
	 * @author thomasurias
	 */
	@SuppressWarnings("unchecked")
	private void removeButton() {
		VBox removeBox = new VBox();
		Button removeButton = new Button("Remove");
		TextField removeText = new TextField();
		
		removeButton.setOnAction(e -> {
			
		    if (model.hasQueue() && active.equals("Queue")) {
		        model.getQueue().dequeue();
		        update(model, null);  // redraw
		        return;
		    }
		    
		    if (model.hasStack()) {
		        controller.remove(null);  
		        update(model, null);
		        return;
		    }
		    
		    String input = removeText.getText();
		    if(input.equals("") || input.length() >= 10) return;

		    if(model.hasInsertionSort() || model.hasBubbleSort()) {
		        try {
		            int value = Integer.parseInt(input);
		            controller.remove(value);
		            removeText.clear();
		            startAutoSort();
		        } catch (NumberFormatException ex) {
		            Alert alert = new Alert(Alert.AlertType.ERROR);
		            alert.setTitle("Invalid Input");
		            alert.setContentText("Please enter a valid integer to append.");
		            alert.showAndWait();
		        }
		    } else {
		        controller.remove(input); 
		        removeText.clear();
		    }
		});

		
		if(model.hasQueue() && active.equals("Queue")) {
		    removeButton.setText("Dequeue");
		    bottomBar.getChildren().add(removeButton);
		    root.setBottom(bottomBar);
		
		 // Made Queue and Stack handle separately instead of grouped together.
		 // Previously: if (model.hasQueue() || model.hasStack()) { ... }
		} else if(model.hasStack() && active.equals("Stack")) {
		    removeButton.setText("Pop");
		    bottomBar.getChildren().add(removeButton);
		    root.setBottom(bottomBar);

		} else {
		    removeText.setMaxWidth(62);
		    removeText.setPromptText("Value");
		    removeBox.getChildren().addAll(removeText, removeButton);
		    bottomBar.getChildren().add(removeBox);
		    root.setBottom(bottomBar);
		}

	}
	
	/**
	 * This method creates a reset button that resets the current data structure
	 * If model is either insertion sort or bubble sort then it runs the sorting animation 
	 */
	private void resetButton() {
		Button resetButton = new Button("Reset");
		resetButton.setOnAction(e -> {
	        controller.reset();
	        if(model.hasInsertionSort() || model.hasBubbleSort()) startAutoSort();
	    });
	    bottomBar.getChildren().add(resetButton);
	    root.setBottom(bottomBar);
	}
	
	/**
	 * This method creates a find button with a text field to search for a certain entry
	 * If the model contains bubble or insertion sort then it only takes an integer value 
	 */
	@SuppressWarnings("unchecked")
	private void findButton() {
	    VBox findBox = new VBox();
	    TextField searchText = new TextField();
	    Button findButton = new Button("Find");

	    searchText.setMaxWidth(40);
	    findBox.getChildren().addAll(searchText, findButton);

	    findButton.setOnAction(e -> {
	        String input = searchText.getText().trim();
	        if (input.isEmpty() || input.length() >= 10) return;

	        Comparable valueToFind = input;

	        // For sorting algorithms, only integers are allowed
	        if (model.hasInsertionSort() || model.hasBubbleSort()) {
	            try {
	                valueToFind = Integer.parseInt(input);
	            } catch (NumberFormatException ex) {
	                Alert alert = new Alert(Alert.AlertType.ERROR);
	                alert.setTitle("Invalid Input");
	                alert.setContentText("Please enter a valid integer for the sorting algorithms.");
	                alert.showAndWait();
	                return;
	            }
	        }

	        try {
	            Object result = controller.find(valueToFind);
	            String resultMsg;

	            if (result == null) {
	                resultMsg = "Not found";
	            } 
	            
                int index = (Integer) result;
                if (index != -1) {
                    resultMsg = "Found " + valueToFind + " at index " + index;
                } else {
                    resultMsg = valueToFind + " not found";
                }

	            Alert alert = new Alert(Alert.AlertType.INFORMATION);
	            alert.setTitle("Search Result");
	            alert.setContentText(resultMsg);
	            alert.showAndWait();

	        } catch (Exception ex) {
	            Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setTitle("Error");
	            alert.setContentText("An unexpected error occurred.");
	            alert.showAndWait();
	        }
	    });

	    bottomBar.getChildren().add(findBox);
	    root.setBottom(bottomBar);
	}


	
	/**
	 * Creates and adds a "Peek" button for Queue.
	 * Retrieves the front element of the queue, displays it in an informational alert.  
	 * Shows error if empty.
	 *
	 * @author Emmanuel
	 */
	private void peekButton() {
	    if (!model.hasQueue() && !model.hasStack()) return;

	    Button peekBtn = new Button("Peek");

	    peekBtn.setOnAction(e -> {
	        try {
	        	if(model.hasQueue()) {
		            Object frontItem = model.getQueue().peek();
		            Alert alert = new Alert(Alert.AlertType.INFORMATION);
		            alert.setTitle("Queue Peek");
		            alert.setHeaderText("Front of Queue:");
		            alert.setContentText(frontItem.toString());
		            alert.showAndWait();
	        	}
	        	if(model.hasStack() ) {
	        		Object topItem = controller.find(null);
		            Alert alert = new Alert(Alert.AlertType.INFORMATION);
		            alert.setTitle("Stack Peek");
		            alert.setHeaderText("Top of Stack:");
		            alert.setContentText(topItem.toString());
		            alert.showAndWait();
	        	}
	        } catch (Exception ex) {
	            Alert alert = new Alert(Alert.AlertType.ERROR);
	            if(model.hasQueue()) {
	            	alert.setTitle("Queue Peek Error");
		            alert.setContentText("Queue is empty.");
	            }
	            if (model.hasStack()) {
	            	alert.setTitle("Stack Peek Error");
		            alert.setContentText("Stack is empty.");
	            }
	            alert.showAndWait();
	        }
	    });

	    bottomBar.getChildren().add(peekBtn);
	}

	/**
	 * This method creates the main menu button on the bottom bar 
	 * so the user can return to the home screen and select another ds option 
	 */
	@SuppressWarnings("unused")
	private void mainMenuButton() {
		Button menuButton = new Button("Main Menu");
		menuButton.setOnAction(e -> {
	        showMainMenu();
	    });
	    bottomBar.getChildren().add(menuButton);
	    root.setBottom(bottomBar);
	}
	
	/**
	 * This method updates the View class whenever the Model is changed 
	 * 
	 * @param o : Observable object that is notifying the function when to update 
	 * @param arg: Generic object type of what is being modified
	 * @author thomasurias
	 */
	@Override
	public void update(Observable o, Object arg) {
		model = controller.getModel();
		if (model.hasStack()) drawStack(structContainer);
		if (model.hasLinkedList()) drawLL(structContainerLL);
		if (model.hasQueue()) drawQueue(structContainer);
		if (model.hasInsertionSort()) drawSort(structContainer, model.getInsertionSort());
		if (model.hasBubbleSort()) drawSort(structContainer, model.getBubbleSort());
	}
	
	/**
	 * This method creates a GUI instance of an insertion sort algorithm or a bubble sort algorithm.
	 * Uses a bar graph to display different values. Can be reused for comparing multiple sorts.
	 * 
	 * @param structContainer : VBox for the container that the bar graph will be displayed in
	 * @param sorter : the sorting algorithm instance (InsertionSort or BubbleSort)
	 */
	@SuppressWarnings("unchecked")
	private void drawSort(VBox targetContainer, Sorter sorter) {
	    targetContainer.getChildren().clear();
	    targetContainer.setSpacing(5);
	    targetContainer.setAlignment(Pos.BOTTOM_CENTER);

	    Text title = null;
	    ArrayList<Integer> list = null;

	    if (sorter instanceof InsertionSort) {
	        list = ((InsertionSort) sorter).getlistToSort();
	        title = new Text("Insertion Sort");
	    } else if (sorter instanceof BubbleSort) {
	        list = ((BubbleSort) sorter).getlistToSort();
	        title = new Text("Bubble Sort");
	    }

	    title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
	    targetContainer.getChildren().add(title);

	    HBox barBox = new HBox(5);
	    barBox.setAlignment(Pos.BOTTOM_CENTER);

	    int maxVal = 1;
	    for (int val : list) {
	        if (val > maxVal) {
	            maxVal = val;
	        }
	    }

	    for (int val : list) {
	        double height = (200.0 * val) / maxVal;

	        VBox barContainer = new VBox(2);
	        barContainer.setAlignment(Pos.BOTTOM_CENTER);

	        Rectangle rect = new Rectangle(20, height, Color.LIGHTBLUE);
	        rect.setStroke(Color.BLACK);

	        Text text = new Text(String.valueOf(val));

	        barContainer.getChildren().addAll(rect, text);
	        barBox.getChildren().add(barContainer);
	    }

	    targetContainer.getChildren().add(barBox);
	}

	/**
	 * This method creates the stack in JavaFX by iterating through the Stack elements 
	 * and creating rectangles for each one. 
	 * 
	 * @param <T>
	 * @param structContainer : Vbox of where the stack will be contained in
	 * @author thomasurias
	 */
	@SuppressWarnings("unchecked")
	public <T> void drawStack(VBox structContainer) {
	    structContainer.getChildren().clear();
	    structContainer.setSpacing(1);
	    structContainer.setAlignment(Pos.BOTTOM_CENTER);

	    if (controller == null || controller.getModel() == null) return;
	    
	    Text title = new Text("Stack");
	    title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
	    structContainer.getChildren().add(title);

	    List<T> elements = controller.getModel().getStackElements();

		double maxHeight = 400;
		double itemHeight = 30 + structContainer.getSpacing(); 
		int maxItems = (int) (maxHeight / itemHeight);
	
		int startIndex = Math.max(0, elements.size() - maxItems);
	
		for (int i = elements.size() - 1; i >= startIndex; i--) {
		    T element = elements.get(i);
		    StackPane itemBox = new StackPane();
		    Rectangle rect = new Rectangle(100, 30, Color.LIGHTBLUE);
		    rect.setStroke(Color.BLACK);
		    rect.setStrokeWidth(3);

		    Text text = new Text(element.toString());
		    itemBox.getChildren().addAll(rect, text);

		    structContainer.getChildren().add(itemBox); // adds from top to bottom
		}
	}
	
	/**
	 * This method is used to draw the LL. It uses a GridPane to build show the nodes which are 
	 * StackPanes comprised of rectangles, data values, and arrow to next node or null. This method
	 * also utilizes the drawArrow method to show arrows between nodes on lower levels in the GUI. 
	 * @param structContainerLL : A GridPane that holds the LL components
	 * @author cole 
	 */
	@SuppressWarnings("unchecked")
	public <T> void drawLL(GridPane structContainerLL) {
	    structContainerLL.getChildren().clear();
	    if (controller == null || controller.getModel() == null) {
	    	return;
	    }
	    ArrayList<T> theList = model.getLL();
	    int counter = 0;
	    int row = 0;
	    boolean decCounter = false;
	    boolean lToR = true;
	    for(int i = 0; i < theList.size(); i++) {
	    	if(counter >= 4 || counter == 0 && i != 0) {
	    		Canvas arrow = drawArrow(lToR);
	    		structContainerLL.add(arrow, counter, row);
	    		decCounter = !decCounter;
	    		row++;
	    		lToR = !lToR;
	    	}

	    	StackPane pane = new StackPane();
	    	Rectangle rect = new Rectangle(90, 30, Color.LIGHTGREEN);
	        rect.setStroke(Color.BLACK);
	        rect.setStrokeWidth(2);
	        
	    	Rectangle innerRect = new Rectangle(30,30, Color.LIGHTGOLDENRODYELLOW);
	        innerRect.setStroke(Color.BLACK);
	        innerRect.setStrokeWidth(2); 
	        Text text = new Text(theList.get(i).toString());	
	        
	        if(lToR) StackPane.setMargin(text, new Insets(0, 0, 0, 4));
		    else StackPane.setMargin(text, new Insets(0, 4, 0, 0));
	        
	    	if(i == theList.size() - 1) {
	    		Canvas canvas = new Canvas(30, 30);
	    		GraphicsContext gc = canvas.getGraphicsContext2D();
	    		gc.strokeLine(0, 30, 30, 0);
	    		gc.setLineWidth(2);
	    		gc.setStroke(Color.BLACK);
		        pane.getChildren().addAll(rect,text, innerRect, canvas);
		    	if(lToR) {
			        pane.setAlignment(text, Pos.CENTER_LEFT);
			        pane.setAlignment(innerRect, Pos.CENTER_RIGHT);
			        pane.setAlignment(canvas, Pos.CENTER_RIGHT);
		    	}else {
			        pane.setAlignment(text, Pos.CENTER_RIGHT);
			        pane.setAlignment(innerRect, Pos.CENTER_LEFT);
			        pane.setAlignment(canvas, Pos.CENTER_LEFT);
		    	}
		        structContainerLL.add(pane, counter, row);
		        continue;
	    	}
    		Canvas nextLine = new Canvas(30, 30);
    		GraphicsContext gc = nextLine.getGraphicsContext2D();
    		gc.setLineWidth(1.5);
    		gc.setStroke(Color.BLACK);
    		if(lToR) {
    		    gc.strokeLine(5, 15, 25, 15);
    		    gc.strokeLine(25, 15, 20, 10);
    		    gc.strokeLine(25, 15, 20, 20); 
    		}else {
    		    gc.strokeLine(25, 15, 5, 15); 
    		    gc.strokeLine(5, 15, 10, 10); 
    		    gc.strokeLine(5, 15, 10, 20);
    		}
	        pane.getChildren().addAll(rect,text, innerRect, nextLine);
	        
	    	if(lToR) {
		        pane.setAlignment(text, Pos.CENTER_LEFT);
		        pane.setAlignment(innerRect, Pos.CENTER_RIGHT);
		        pane.setAlignment(nextLine, Pos.CENTER_RIGHT);
	    	}else {
		        pane.setAlignment(text, Pos.CENTER_RIGHT);
		        pane.setAlignment(innerRect, Pos.CENTER_LEFT);
		        pane.setAlignment(nextLine, Pos.CENTER_LEFT);
	    	}
	    	
	    	structContainerLL.add(pane, counter, row);
	        if(decCounter == false) counter++;
	        else counter--;
	    }
	}
	
	/**
	 *  This method is used to draw an arrow pointing to the next nodde.
	 * @param lToR : boolean representing if left to right is true or false
	 * @return canvas : a Canvas that is the arrow
	 */
	public Canvas drawArrow(boolean lToR) {
		Canvas canvas = new Canvas(90, 30);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setLineWidth(1.5);
		gc.setStroke(Color.BLACK);
	    
		if(lToR) {
		    gc.strokeLine(5, 15, 20, 15); // horiz line
		    gc.strokeLine(20, 16, 20, 29); // right part of arrow
		    gc.strokeLine(20, 30, 25, 25); // vert line
		    gc.strokeLine(20, 30, 15, 25); // left part of arrow
	    }
		else {
		    gc.strokeLine(90, 15, 70, 15); // horiz line
	        gc.strokeLine(70, 30, 75, 25); // right part of arrow
	        gc.strokeLine(70, 16, 70, 29); // vert line
	        gc.strokeLine(70, 30, 65, 25); // left part of arrow
		}
		return canvas;
	}
	
	/**
	 * This method draws the queue by looping through the queue's contents and
	 * creating visual cells that represent each element. Each cell contains a rectangle 
	 * and a text node displaying the value, with labels for the front and rear elements.
	 * 
	 * @param <T> - the type of elements stored in the queue.
	 * @param container - the VBox in which the queue visualization will be displayed.
	 * @author Emmanuel
	 */
	@SuppressWarnings("unchecked")
	public <T> void drawQueue(VBox container) {
	    container.getChildren().clear();
	    
	    Text title = new Text("Queue");
	    title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
	    structContainer.getChildren().add(title);

	    Queue<T> q = (Queue<T>) model.getQueue();
	    if (q == null || q.size() == 0) return;

	    FlowPane pane = new FlowPane();
	    pane.setAlignment(Pos.CENTER);
	    pane.setHgap(15);
	    pane.setVgap(20);

	    T[] arr = q.getRawArray();
	    int front = q.getFrontIndex();
	    int size = q.size();
	    int cap = q.getCapacity();

	    for (int i = 0; i < size; i++) {
	        int idx = (front + i) % cap;
	        T value = arr[idx];

	        VBox cell = new VBox(5);
	        cell.setAlignment(Pos.CENTER);

	        Rectangle rect = new Rectangle(70, 40);
	        rect.setFill(Color.LIGHTBLUE);
	        rect.setStroke(Color.BLACK);

	        Text text = new Text(String.valueOf(value));

	        if (i == 0) cell.getChildren().add(new Text("Front"));
	        if (i == size - 1) cell.getChildren().add(new Text("Rear"));

	        cell.getChildren().addAll(rect, text);
	        pane.getChildren().add(cell);
	    }

	    container.getChildren().add(pane);
	}

	/**
	 * Starts the automatic sorting animation for the current sort 
	 * (Insertion Sort or Bubble Sort). Steps through the sort every 500ms 
	 * and updates the GUI until the sort is finished.
	 */
	private void startAutoSort() {
	    if (sortTimeline != null) {
	        sortTimeline.stop();
	    }

	    if (model.hasInsertionSort()) {
	        sorter = model.getInsertionSort();  // a Sorter
	        sorter.startSort();
	    } else if (model.hasBubbleSort()) {
	        sorter = model.getBubbleSort();     // also a Sorter
	        sorter.startSort();
	    }

	    sortTimeline = new Timeline(
	        new KeyFrame(Duration.millis(500), e -> {
	            boolean hasMore = sorter.stepSort();   // now works!
	            if (model.hasInsertionSort()) {
	            	drawSort(structContainer, model.getInsertionSort());
	            }
	            if (model.hasBubbleSort()) {
	            	drawSort(structContainer, model.getBubbleSort());
	            }

	            if (!hasMore) {
	                sortTimeline.stop();
	            }
	        })
	    );

	    sortTimeline.setCycleCount(Timeline.INDEFINITE);
	    sortTimeline.play();
	}
	
	/**
	 * Shows a comparison screen for Insertion Sort and Bubble Sort.
	 * Displays both sorts side by side with a timer and updates the GUI
	 * every 200ms until both sorts are done.
	 */
	private void showCompareScreen() {
	    bottomBar.getChildren().clear();

	    HBox compareBox = new HBox(50);
	    compareBox.setAlignment(Pos.CENTER);

	    VBox insertionBox = new VBox();
	    insertionBox.setAlignment(Pos.BOTTOM_CENTER);
	    VBox bubbleBox = new VBox();
	    bubbleBox.setAlignment(Pos.BOTTOM_CENTER);

	    compareBox.getChildren().addAll(insertionBox, bubbleBox);

	    Text timerText = new Text("Time: 0.0 s");
	    timerText.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

	    VBox mainBox = new VBox(20);
	    mainBox.setAlignment(Pos.CENTER);
	    mainBox.getChildren().addAll(compareBox, timerText);

	    root.setCenter(mainBox);

	    // Create new sorters
	    InsertionSort insertion = new InsertionSort();
	    BubbleSort bubble = new BubbleSort();

	    insertion.startSort();
	    bubble.startSort();

	    Timeline compareTimeline = new Timeline();
	    double[] elapsedTime = {0};

	    compareTimeline.getKeyFrames().add(
	        new KeyFrame(Duration.millis(200), e -> {
	            boolean moreInsertion = insertion.stepSort();
	            boolean moreBubble = bubble.stepSort();

	            // Reuse drawSort
	            drawSort(insertionBox, insertion);
	            drawSort(bubbleBox, bubble);

	            elapsedTime[0] += 0.2;
	            timerText.setText(String.format("Time: %.1f s", elapsedTime[0]));

	            if (!moreInsertion && !moreBubble) {
	                compareTimeline.stop();
	            }
	        })
	    );

	    compareTimeline.setCycleCount(Timeline.INDEFINITE);
	    compareTimeline.play();
	}

}

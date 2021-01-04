/*
 * Neeco Fabian
 * 14 June 2019
 */


package application;

import javafx.animation.Timeline;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;
import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class Breathe extends Stage {

	private Stage primaryStage;
	private double change;
	private Timeline timer;
	private ArrayList<String> speedChoices;
	private double speed;
	private final double SLOW = 2;
	private final double NORMAL = 3;
	private final double FAST = 4;
	private final double[][] layoutConstants;
	private Shape[] shapes;
	private Pane root;
	private File f;
	private ImageView ivLax;

	public Breathe(Stage bs) {

		// Declare and initialize Stage, Pane, and Canvas objects
		primaryStage = bs;

		root = new Pane();
		root.setPrefSize(1280, 800);

		Canvas canvas = new Canvas(root.getPrefWidth(), root.getPrefHeight());
		GraphicsContext gc = canvas.getGraphicsContext2D();

		// Set speed to normal by default
		speed = NORMAL;

		// Declare and initialize an array of Shapes
		shapes = new Shape[7];

		// Initialize each shape
		shapes[0] = new Shape(275, 0, 75, Color.rgb(56, 149, 211), Color.rgb(255, 211, 0));
		shapes[1] = new Shape(100, 400, 300, Color.rgb(18, 97, 160), Color.rgb(218, 165, 32));
		shapes[2] = new Shape(200, -460, -350, Color.rgb(32, 107, 176), Color.rgb(254, 220, 86));
		shapes[3] = new Shape(60, 300, -320, Color.rgb(64, 183, 221), Color.rgb(252, 244, 163));
		shapes[4] = new Shape(200, 500, -50, Color.rgb(200, 237, 245), Color.rgb(244, 222, 126));
		shapes[5] = new Shape(60, -350, -50, Color.rgb(100, 199, 232), Color.rgb(249, 166, 2));
		shapes[6] = new Shape(175, -490, 190, Color.rgb(88, 204, 237), Color.rgb(255, 195, 11));

		// Initialize an array for the x and y values of the moving shapes that are constant
		layoutConstants = new double[shapes.length][4];

		// Loop through all shapes
		for (int i = 0; i < shapes.length; i++)
		{			
			// Initialize the coordinate values that are constant for each shape
			layoutConstants[i][0] = root.getPrefWidth()/2 + shapes[i].getShifterX() + shapes[i].getWidth()/2;
			layoutConstants[i][1] = root.getPrefHeight()/2 + shapes[i].getShifterY() + shapes[i].getHeight()/2;
			layoutConstants[i][2] = root.getPrefWidth()/2 + shapes[i].getShifterX() + shapes[i].getWidth()/2;
			layoutConstants[i][3] = root.getPrefHeight()/2 + shapes[i].getShifterY() + shapes[i].getHeight()/2;
		}
		// Declare, initialize, and fill a String ArrayList of speeds for shape expansion and contraction
		speedChoices = new ArrayList<String>(); 
		speedChoices.add("Slow");
		speedChoices.add("Normal");
		speedChoices.add("Fast");

		// Declare, initialize, set properties of Labels for breathe and dynamic in/out Label
		Label lblBreathe = new Label("breathe");
		lblBreathe.setPrefWidth(300);
		lblBreathe.setLayoutX((root.getPrefWidth() - lblBreathe.getPrefWidth())/2);
		lblBreathe.setLayoutY(20);
		lblBreathe.setAlignment(Pos.CENTER);
		
		// Dark Mode
		if (MainLax.d.equalsIgnoreCase("d"))
		{
			lblBreathe.setStyle("-fx-text-fill: white");
		}
		else
		{
			lblBreathe.setStyle("-fx-text-fill: black");
		}
		

		Label lblInOut = new Label("in");
		lblInOut.setPrefWidth(300);
		lblInOut.setLayoutX((root.getPrefWidth() - lblInOut.getPrefWidth())/2);
		lblInOut.setLayoutY(85);
		lblInOut.setAlignment(Pos.CENTER);
		lblInOut.setStyle("-fx-text-fill: darkGray;" +"-fx-font-weight: bold;");
		
		// Correct logo
		if (MainLax.d.equalsIgnoreCase("d"))
		{
			// Dark
			ivLax = new ImageView(new Image("file:images/lax_white_logo.png", 120, 40, true, true));
			
		}
		else
		{
			// Light
			ivLax = new ImageView(new Image("file:images/lax_logo.png", 120, 40, true, true));
			
		}
		ivLax.setLayoutX(1090);
		ivLax.setLayoutY(35);
		ivLax.setStyle("-fx-background-color: transparent");

				

		// Declare, initialize, set properties of options Button
		Button btnMore = new Button();
		if (MainLax.d.equals("d"))
		{
			btnMore.setStyle(
					"-fx-background-radius: 20em; " +
							"-fx-min-width: 50px; " +
							"-fx-min-height: 50px; " +
							"-fx-max-width: 50px; " +
							"-fx-max-height: 50px;" +
							"-fx-background-color: rgb(255, 255, 255) "
					);
			btnMore.setGraphic(new ImageView(new Image("file:images/3dots_black.png", 50, 50, true, true)));
		}
		else
		{
			btnMore.setStyle(
					"-fx-background-radius: 20em; " +
							"-fx-min-width: 50px; " +
							"-fx-min-height: 50px; " +
							"-fx-max-width: 50px; " +
							"-fx-max-height: 50px;" +
							"-fx-background-color: rgb(0,0,0) "
					);
			btnMore.setGraphic(new ImageView(new Image("file:images/3dots.png", 50, 50, true, true)));
		}
		
		btnMore.setLayoutX(1200);
		btnMore.setLayoutY(30);
		btnMore.setPrefSize(50, 50);
		btnMore.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {

				// Stop the moving shapes, pause music
				timer.stop();

				// Declare and initialize a Dialog object
				Dialog<ButtonType> dialog = new Dialog<ButtonType>(); 
				dialog.setTitle("Preferences"); 
				dialog.setHeaderText("Inhale as the shapes expand. Exhale as they contract.");

				// Declare and initialize ButtonType objects
				ButtonType btOk = new ButtonType("OK"); 
				ButtonType btCancel = new ButtonType("CANCEL");

				// Add Buttons to the dialog
				dialog.getDialogPane().getButtonTypes().addAll(btOk, btCancel);

				// Declare and Initialize GridPane to format buttons, labels, and ComboBoxes; set properties
				GridPane grid = new GridPane(); 
				grid.setHgap(20);
				grid.setVgap(20);
				grid.setPadding(new Insets(20, 20, 5, 20));

				// Add ComboBox Labels to GridPane
				grid.add(new Label("Speed"), 0, 0);
				grid.add(new Label("Theme"), 0, 1);

				// Declare and initialize variable representing the index of the desired speed on the array list
				int currentSpeed = 1;

				// Check the shape speed, set ArrayList to correct index
				if (speed == SLOW)
				{
					currentSpeed = 0;	
				}
				else if (speed == NORMAL)
				{
					currentSpeed = 1;
				}
				else if (speed == FAST)
				{
					currentSpeed = 2;
				}

				// Declare and initialize ComboBoxes for speed and theme preferences
				ComboBox<String> cboSpeed = new ComboBox<String>();
				cboSpeed.getItems().addAll(speedChoices);
				cboSpeed.setValue(speedChoices.get(currentSpeed));

				ComboBox<String> cboTheme = new ComboBox<String>();
				cboTheme.getItems().addAll("Blue Moons", "Twinkling Stars");
				cboTheme.setPromptText("Theme:");

				// Set the default value of ComboBoxes according to current theme
				if (shapes[0].getShape() == 0)
				{
					cboTheme.setValue("Blue Moons");
				}
				else
				{
					cboTheme.setValue("Twinkling Stars");
				}

				// Add ComboBoxes to GridPane
				grid.add(cboSpeed, 1, 0);
				grid.add(cboTheme, 1, 1);

				// Add GridPane to dialog box
				dialog.getDialogPane().setContent(grid);

				// Check for user button press
				Optional<ButtonType> result = dialog.showAndWait();

				// Check if the user presses a button
				if (result.isPresent())
				{
					// Check if user presses ok
					if (result.get() == btOk)
					{				
						// Check result, set new speed to desired choice
						if (cboSpeed.getValue().equals("Slow"))
						{
							speed = SLOW;
						} 
						else if (cboSpeed.getValue().equals("Normal"))
						{
							speed = NORMAL;
						}
						else if (cboSpeed.getValue().equals("Fast"))
						{
							speed = FAST;
						}

						// Check the theme preference
						if (cboTheme.getValue().equals("Blue Moons"))
						{
							// Set colour to blue shade and circle shape for all shapes
							for (int i = 0; i < shapes.length; i++)
							{
								shapes[i].setColor(shapes[i].getBlueShade());
								shapes[i].setShape(0);
							}
						} 
						else if (cboTheme.getValue().equals("Twinkling Stars"))
						{
							// Set colour to yellow shade and diamond shape for all shapes
							for (int i = 0; i < shapes.length; i++)
							{
								shapes[i].setColor(shapes[i].getYellowShade());
								shapes[i].setShape(1);
							}
						}			
					}

					// Restart timer, play music
					timer.play();
				}
			}
		});
		// Change Button when mouse enters and exits it
		btnMore.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				btnMore.setStyle(
						"-fx-background-radius: 20em; " +
								"-fx-min-width: 50px; " +
								"-fx-min-height: 50px; " +
								"-fx-max-width: 50px; " +
								"-fx-max-height: 50px;" +
						"-fx-background-color: darkGray");
			}
		});
		btnMore.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if (MainLax.d.equals("d"))
				{
					btnMore.setStyle(
							"-fx-background-radius: 20em; " +
									"-fx-min-width: 50px; " +
									"-fx-min-height: 50px; " +
									"-fx-max-width: 50px; " +
									"-fx-max-height: 50px;" +
									"-fx-background-color: rgb(255, 255, 255) "
							);
					btnMore.setGraphic(new ImageView(new Image("file:images/3dots_black.png", 50, 50, true, true)));
				}
				else
				{
					btnMore.setStyle(
							"-fx-background-radius: 20em; " +
									"-fx-min-width: 50px; " +
									"-fx-min-height: 50px; " +
									"-fx-max-width: 50px; " +
									"-fx-max-height: 50px;" +
									"-fx-background-color: rgb(0,0,0) "
							);
					btnMore.setGraphic(new ImageView(new Image("file:images/3dots.png", 50, 50, true, true)));
				}
			}
		});

		// Initialize change value (values that changes according to sine function)
		change = 1;


		// Declare and initialize KeyFrame object, refresh rate is dynamic
		KeyFrame kf = new KeyFrame(Duration.millis(speed*10.0), new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {

				//	Loop through all shapes	
				for (int i =0; i < shapes.length; i++)
				{
					// Set the shape colour according to the current user preferences
					gc.setFill(shapes[i].getColor());

					// Clear any previous circles, then draw new oval
					gc.clearRect((root.getPrefWidth() - shapes[i].getTempWidth())/2 + shapes[i].getShifterX(), (root.getPrefHeight() - shapes[i].getTempHeight())/2 + shapes[i].getShifterY(), shapes[i].getTempWidth(), shapes[i].getTempHeight());

					// Set the width and height of the new circle, sine function slows the motion at teh extreme ends
					shapes[i].setWidth((Math.sin(change*0.3 * speed) + 1) * shapes[i].getSizeMultiplier());
					shapes[i].setHeight(shapes[i].getWidth());	

					// Change label name according to expanding or contracting shape
					if (shapes[0].getTempWidth() > shapes[0].getWidth())
					{
						lblInOut.setText("out");
					}
					else if (shapes[0].getTempWidth() == shapes[0].getWidth())
					{
					}
					else
					{
						lblInOut.setText("in");
					}

					// Check the preferred shapes
					if (shapes[i].getShape() == 0)
					{
						// Draw circles
						gc.fillOval((root.getPrefWidth() - shapes[i].getWidth())/2 + shapes[i].getShifterX(), (root.getPrefHeight() - shapes[i].getHeight())/2 + shapes[i].getShifterY(), shapes[i].getWidth(), shapes[i].getHeight());

					}
					else
					{
						// Draw diamonds
						double[] xPoints = new double[] {layoutConstants[i][0], 
								root.getPrefWidth()/2 + shapes[i].getShifterX() + shapes[i].getWidth()/2, 
								layoutConstants[i][2], 
								root.getPrefWidth()/2 + shapes[i].getShifterX() - shapes[i].getWidth()/2};

						double[] yPoints = new double[] {root.getPrefHeight()/2 + shapes[i].getShifterY() - shapes[i].getHeight()/2, 
								layoutConstants[i][1], 
								root.getPrefHeight()/2 + shapes[i].getShifterY() + shapes[i].getHeight()/2, 
								layoutConstants[i][3]};

						gc.fillPolygon(xPoints, yPoints, 4);
					}

					// Store the current width and height temporarily to clear the current shape when drawing its next iteration
					shapes[i].setTempWidth(shapes[i].getWidth());
					shapes[i].setTempHeight(shapes[i].getHeight());
				}

				// Change the value that determines how the shape's width is determined through the sine function
				change += 0.03;
			} 
		});

		// Initialize Timeline object, set properties, start timer
		timer = new Timeline(kf);
		timer.setCycleCount(Timeline.INDEFINITE);
		timer.play();

		// Change Pane background, add all nodes
		if (MainLax.d.equals("d"))
		{
			root.setStyle("-fx-background-color: rgb(0, 0, 0)");
		}
		else
		{
			root.setStyle("-fx-background-color: rgb(255, 255, 255)");
		}
		root.getChildren().addAll(canvas, btnMore, ivLax, lblBreathe, lblInOut);

		// Declare and initialize scene objects, obtain css formating, set properties, show
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		setTitle("Breathe");
		setScene(scene);
		show();

		// If user clicks X Button
		setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent e) {

				// Stop timer, show home menu
				timer.stop();
				primaryStage.show();
			}
		});

	};

}
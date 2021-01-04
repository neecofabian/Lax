
package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class Remember extends Stage {

	private Stage primaryStage;
	public LinkedList<FlashCard> fc;
	public TilePane tPane;
	private ImageView ivLax;

	public Remember(Stage rms) {

		// Initialize Stage, declare and initialize BorderPane
		primaryStage = rms;
		BorderPane root = new BorderPane();
		root.setPrefSize(1280, 800);
		root.setPadding(new Insets(0, 0, 0, 0));

		// Declare, initialize, set properties for HBox
		HBox hbTop = new HBox();
		hbTop.setSpacing(20);
		hbTop.setPadding(new Insets(0, 30, 0, 20));
		hbTop.setAlignment(Pos.CENTER_RIGHT);

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
		btnMore.setPrefSize(50, 50);
		btnMore.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {

				// Declare and initialize a Dialog object
				Dialog<ButtonType> dialog = new Dialog<ButtonType>(); 
				dialog.setTitle("Preferences"); 
				dialog.setHeaderText("Use the flash cards to help you remember information.");

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

				Label lblTheme = new Label("Choose Theme:");				
				
				// Add ComboBox Labels to GridPane
				grid.add(new Label("To see the other side of the flashcard, press Enter.\nThe front is darker with white text. The back is lighter with black text."), 0, 0);
				

				// Declare and initialize ComboBoxes for theme preferences
				ComboBox<String> cboTheme = new ComboBox<String>();
				cboTheme.getItems().addAll("Green Beans", "Hot Dogs");
				if(fc.get(0).isGreen())
				{
					cboTheme.setValue("Green Beans");
				}
				else
				{
					cboTheme.setValue("Hot Dogs");
				}

				// HBox for the Label and ComboBox for the theme
				HBox hbPair = new HBox(20);
				hbPair.setAlignment(Pos.CENTER_LEFT);
				hbPair.getChildren().addAll(lblTheme, cboTheme);
				
				// Add ComboBox to GridPane, set Alignment
				grid.add(hbPair, 0, 1);

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
						// Set color of all FlashCards
						for (int i = 0; i < fc.size(); i++)
						{
							// Check the theme preference
							if (cboTheme.getValue().equals("Green Beans"))
							{
								// Set color to green
								fc.get(i).setGreen(true);
							} 
							else if (cboTheme.getValue().equals("Hot Dogs"))
							{
								// Set color to red
								fc.get(i).setGreen(false);
							}		
							fc.get(i).setColor();
						}
					}
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

		// Declare, initialize, set properties of Labels for Remember
		Label lblRemember = new Label("remember");
		lblRemember.setPrefWidth(1050);
		lblRemember.setAlignment(Pos.CENTER_LEFT);
		// Dark Mode
		if (MainLax.d.equalsIgnoreCase("d"))
		{
			lblRemember.setStyle("-fx-text-fill: white");
		}
		else
		{
			lblRemember.setStyle("-fx-text-fill: black");
		}


		// Add label, logo and button to HBox; add all to the top of BorderPane
		hbTop.getChildren().addAll(lblRemember, ivLax, btnMore);
		root.setTop(hbTop);

		// Declare and initialize TilePane object, set properties
		tPane = new TilePane();
		tPane.setPrefColumns(5);
		tPane.setHgap(30);
		tPane.setVgap(30);
		tPane.setPadding(new Insets(30, 30, 30, 30)); 
		tPane.setOrientation(Orientation.HORIZONTAL);
		if (MainLax.d.equals("d"))
		{
			tPane.setStyle("-fx-background-color: black");
		}
		else
		{
			tPane.setStyle("-fx-background-color: white");
		}

		// Declare and initialize ScrollPane object, set properties
		ScrollPane sPane = new ScrollPane();
		sPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		sPane.setVbarPolicy(ScrollBarPolicy.NEVER); 
		sPane.setFitToHeight(true);
		sPane.setFitToWidth(true);
		sPane.setStyle("-fx-background-color: rgb(255,255, 255);" +
				"-fx-width: 0px;" +
				"-fx-background: transparent;"
				);
		if (MainLax.d.equals("d"))
		{
			sPane.setStyle("-fx-background-color: black");
		}
		else
		{
			sPane.setStyle("-fx-background-color: white");
		}

		// Initialize LinkedList
		fc = new LinkedList<FlashCard>();

		// Initialize 50 FlashCards
		for (int i = 0; i < 50; i++)
		{
			// Declare and initialize new FlashCard object, add to LinkedList, add to tPane
			FlashCard newFc = new FlashCard(i);
			fc.add(newFc);
			// Add th enew FlashCard
			tPane.getChildren().add(newFc);

		}

		// READ TEXT FILE
		try
		{
			// Declare and initialize the BufferedReader object, set to  correct text file
			BufferedReader in = new BufferedReader(new FileReader(new File("flashcards.txt")));
			String line;
			String [] data;

			// Loop through all FlashCards
			int i = 0;
			line = in.readLine();

			// Continue while there is text
			while(line != null)
			{
				// Split data into array, set elements as front an back of the FlashCard
				data = line.split("     , ");

				// Check if text has the no-text symbol, if so, set to null
				if (data[0].equals("^"))
				{
					fc.get(i).setFront("");
				}
				else
				{
					fc.get(i).setFront(data[0]);
				}



				// Check if text has the no-text symbol, if so, set to null
				if (data[1].equals("^"))
				{
					fc.get(i).setBack("");
				}
				else
				{
					fc.get(i).setBack(data[1]);
				}

				// Check which side the FlashCard is shown, set FlashCard to correct side, to the same color
				if (data[2].equals("b"))
				{
					// Output the back side
					fc.get(i).setText(fc.get(i).getBack());
					fc.get(i).setFrontShown(false);

				}
				else if (data[2].equals("f"))
				{
					// Output the front side
					fc.get(i).setText(fc.get(i).getFront());
					fc.get(i).setFrontShown(true);
				}

				fc.get(i).setColor();

				i++;

				// Read the next line
				line = in.readLine();

			}
			// Close BufferedReader
			in.close();
		}
		catch (FileNotFoundException ex)
		{
		}
		catch (IOException ex)
		{
		}

		// Add TilePane to Scroll Pane
		sPane.setContent(tPane);

		// Add the ScrollPane to the center of BorderPane, set background
		root.setCenter(sPane);

		// Declare and initialize Scene objects, get css, set properties and show
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		setTitle("Remember");
		setScene(scene);
		show();

		// When user clicks x Button
		setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent e) {
				// Ask user if they want to save changes
				// Declare, initialize Alert to get user action
				Alert alert = new Alert(AlertType.CONFIRMATION); 
				alert.setTitle("Save");
				alert.setContentText("Would you like to save changes to Remember?"); 
				alert.setHeaderText(null);

				// Add ButtonTypes to alert
				alert.getButtonTypes().clear();
				alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);

				// Show the alert, wait and obtain result
				Optional<ButtonType> result = alert.showAndWait();

				// Check for the correct button, do appropriate action
				if (result.get() == ButtonType.YES)
				{
					// SAVE TO A TEXT FILE
					try {
						// Declare and initialize a BufferedWriter object, save to text file
						BufferedWriter out = new BufferedWriter(new FileWriter("flashcards.txt"));

						// Loop through all FlashCards
						for (int i = 0; i < fc.size(); i++)
						{
							// Write front and back contents of fc onto file, separate them, add new line after
							if (fc.get(i).getFront() == "")
							{
								out.write("^");
							}
							else
							{
								out.write(fc.get(i).getFront());
							}


							// Delimiter is distinct to differentiate from FlashCards with commas in them
							out.write("     , ");

							// Write front and back contents of fc onto file, separate them, add new line after
							if (fc.get(i).getBack() == "")
							{
								out.write("^");
							}
							else
							{
								out.write(fc.get(i).getBack());
							}

							// Add delimiter
							out.write("     , ");	

							// Check if front is shown, indicate in binary file
							if (fc.get(i).getFrontShown())
							{
								out.write("f");
							}
							else
							{
								out.write("b");
							}


							// Add new line			
							out.newLine();
						}

						// Close the output stream
						out.close();
					}
					catch (IOException f)
					{
					}

				}

				// Show home menu, stop music
				primaryStage.show();
			}
		});

		if (MainLax.d.equals("d"))
		{
			root.setStyle("-fx-background-color: rgb(0, 0, 0)");
		}
		else
		{
			root.setStyle("-fx-background-color: rgb(255, 255, 255)");
		}
	}
}
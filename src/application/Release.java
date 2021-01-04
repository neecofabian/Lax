/*
 * Neeco Fabian
 * 14 June 2019
 */


package application;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Release extends Stage {

	private LinkedList<String> worries;
	private Stage primaryStage;
	private int bubbleIndex;
	private String[] purples;
	private String formatted;
	private final int MAX_LENGTH = 30*3;
	private ImageView ivLax;
	private TextField txtInput;
	private LinkedList<Label> lblBubbles;

	public Release(Stage rs) {

		// Declare and initialize Linkedlist to represent String of worries
		worries = new LinkedList<String>();

		// Declare and initialize an array of purple rgb codes
		purples = new String[] {"rgb(162, 107, 243)",
				"rgb(172, 73, 245)",
				"rgb(182, 39, 246)",
				"rgb(192, 5, 238)",
				"rgb(191, 5, 247)",

				"rgb(149, 33, 246)",
				"rgb(121, 61, 244)",
				"rgb(104, 88, 243)",
				"rgb(115, 129, 241)",
				"rgb(152, 141, 242)"
		};

		// Declare and inItialize Stage, BorderPane objects, set size
		primaryStage = rs;
		BorderPane root = new BorderPane();
		root.setPadding(new Insets(30, 30, 30, 30));
		root.setPrefSize(1280, 800);

		// Declare, initialize, set properties for Hboxes
		HBox hbBottom = new HBox();
		hbBottom.setSpacing(20);

		HBox hbTop = new HBox();
		hbTop.setSpacing(20);
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

				//RESETTING THE PASSWORD

				// Declare and initialize a Dialog object
				Dialog<ButtonType> dialog = new Dialog<ButtonType>(); 
				dialog.setTitle("Preferences"); 
				dialog.setHeaderText("Expressing worries releases pressure off of you. Once it's down, consider it gone.");
				dialog.setContentText("Reset Password:");

				// Declare and initialize ButtonType objects, add to Dialog
				ButtonType btReset = new ButtonType("Reset"); 
				ButtonType btCancel = new ButtonType("Cancel");
				dialog.getDialogPane().getButtonTypes().addAll(btReset, btCancel);

				// Declare and initialize GridPane, set properties
				GridPane grid = new GridPane(); grid.setHgap(10);
				grid.setVgap(10);
				grid.setPadding(new Insets(20, 150, 10, 10));

				// Declare and initialize PasswordFields
				PasswordField txtCurrentPassword = new PasswordField(); 
				txtCurrentPassword.setPromptText("Current Password");

				PasswordField txtNewPassword = new PasswordField(); 
				txtNewPassword.setPromptText("New Password");

				// Add nodes to GridPane
				grid.add(new Label("Current Password:"), 0, 0);
				grid.add(txtCurrentPassword, 1, 0);
				grid.add(new Label("New Password:"), 0, 1);
				grid.add(txtNewPassword, 1, 1);

				// Add pane to the dialog
				dialog.getDialogPane().setContent(grid);

				// Show dialog
				Optional<ButtonType> result = dialog.showAndWait();

				// Check for user action
				if (result.isPresent())
				{
					if (result.get() == btReset)
					{
						// If user wants to reset, check if entered current password matches actual password
						if (txtCurrentPassword.getText().equals(MainLax.password))
						{
							// Set new password 
							MainLax.password = txtNewPassword.getText();

							// Update binary file
							try {
								// Declare and initialize DataOutputStream object
								DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("initial.dat")));

								// Write important variables on the file
								out.writeUTF(MainLax.firstRelease);	
								out.writeUTF(MainLax.password);

								// Close output stream
								out.close();
							}
							catch(IOException ea)
							{
							}

							// Show Successful LAert
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Password Reset");
							alert.setHeaderText(null);
							alert.setContentText("Password reset successful.");
							alert.showAndWait();
						}
						else
						{
							// Show Alert saying wrong entered password
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("Error");
							alert.setHeaderText(null);
							alert.setContentText("You entered the wrong current password!");
							alert.showAndWait();
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

		// Add logo and more button to HBox; add all to the top of BorderPane
		hbTop.getChildren().addAll(ivLax, btnMore);
		root.setTop(hbTop);

		// Declare and initialize LinkedList of bubble labels
		lblBubbles = new LinkedList<Label>();

		// Loop thorugh all labels
		for (int i = 0; i < 10; i++)
		{
			// Declare, initialize, set properties of Label, add it to LinkedList
			Label lblBubble = new Label();
			lblBubble.setPrefSize(300, 140);
			lblBubble.setStyle("-fx-background-radius: 20em; " +
					"-fx-text-fill: white;" +
					"-fx-font-family: Avenir;" +
					"-fx-font-size: 12pt;" +
					"-fx-background-color: rgb(200, 200, 200)"
					);
			lblBubble.setPadding(new Insets(0, 10, 0, 10));
			lblBubble.setAlignment(Pos.CENTER);

			lblBubbles.add(lblBubble);
		}

		// Declare and initialize GridPane, set properties
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(30, 10, 20, 10));
		grid.setHgap(30);
		grid.setVgap(30);
		grid.setGridLinesVisible(false);

		for (int i = 0; i < lblBubbles.size(); i++)
		{
			GridPane.setFillHeight(lblBubbles.get(i), false);
			GridPane.setFillWidth(lblBubbles.get(i), false);

		}

		// Add Bubble Labls to grid
		grid.add(lblBubbles.get(0), 0, 0);
		grid.add(lblBubbles.get(1), 1, 0);
		grid.add(lblBubbles.get(2), 2, 0);
		grid.add(lblBubbles.get(3), 3, 0);

		grid.add(lblBubbles.get(4), 3, 1);
		grid.add(lblBubbles.get(5), 3, 2);

		grid.add(lblBubbles.get(6), 2, 2);
		grid.add(lblBubbles.get(7), 1, 2);
		grid.add(lblBubbles.get(8), 0, 2);
		grid.add(lblBubbles.get(9), 0, 1);


		// Declare, initialize, set properties of Labels for Release
		Label lblRelease = new Label("release");
		lblRelease.setPrefWidth(300);
		lblRelease.setAlignment(Pos.CENTER);
		// Dark Mode
		if (MainLax.d.equalsIgnoreCase("d"))
		{
			lblRelease.setStyle("-fx-text-fill: white");
		}
		else
		{
			lblRelease.setStyle("-fx-text-fill: black");
		}

		// Set grid properties
		GridPane.setColumnSpan(lblRelease, 2);
		GridPane.setHalignment(lblRelease, HPos.CENTER);
		grid.add(lblRelease, 1, 1);

		// Add grid to Pane
		root.setCenter(grid);

		// Declare and initialize TextField object as type bar
		txtInput = new TextField("Enter your worries. Let it all out.");
		txtInput.setPrefHeight(70);
		txtInput.setPrefWidth(1130);
		txtInput.setPrefColumnCount(69);
		txtInput.setStyle("-fx-font-family: Avenir;" + 
				"-fx-font-size: 22pt;" + 
				"-fx-font-weight: bold;" +
				"-fx-text-fill: white;"+ 
				"-fx-background-color: rgb(75, 75, 75);" +
				"-fx-background-radius: 20em; "
				);
		txtInput.setPadding(new Insets(0,20,0,30));
		txtInput.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				// Add the worry
				add();
			}
		});
		// Set the index of first worry/bubble to 0
		bubbleIndex = 0;


		// READING BINARY FILES
		try {

			// Declare and initialize DataInputStream object
			DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream("release.dat")));

			// Declare and initialize value that determines if EOFException is thrown at the end of the file
			boolean eof = false;

			// Continue reading until the end of the file
			while (!eof)
			{
				try {

					// Read binary file for String, add the worry to the LinkedList
					String worry = in.readUTF();
					worries.add(worry);


					// Change Button colour
					// Dark mode
					if (MainLax.d.equals("d"))
					{
						// Set their Labels to gray and empty the text output again
						lblBubbles.get(bubbleIndex).setStyle("-fx-background-radius: 20em; " +
								"-fx-text-fill: black;" +
								"-fx-font-family: Avenir;" +
								"-fx-font-size: 12pt;" +
								"-fx-background-color: " + purples[bubbleIndex] + ";"
								);
					}
					else
					{
						// Set their Labels to gray and empty the text output again
						lblBubbles.get(bubbleIndex).setStyle("-fx-background-radius: 20em; " +
								"-fx-text-fill: white;" +
								"-fx-font-family: Avenir;" +
								"-fx-font-size: 12pt;" +
								"-fx-background-color: " + purples[bubbleIndex] + ";"
								);
					}

					// ADDING THE APPROPRIATE NEW LINES
					formatted = new String(worries.getLast());

					// Look at every certain number of characters/ the limit of the Label
					for (int i = 30; i < formatted.length(); i+=30)
					{
						// Declare and initialize the index of the of the last space in the line
						int spaceIndex = formatted.lastIndexOf(" ", i);

						// Look for the last space, add a new line
						if (spaceIndex > 0)
						{
							// Set new formatted text from index 0 to the index before the space, add a new line, append the rest of the text
							formatted = formatted.substring(0, spaceIndex) + "\n" + formatted.substring(spaceIndex + 1);
						}
						else
						{
							// Divide the text into two lines (even if it's not at a space)
							formatted = formatted.substring(0, i) + "\n" + formatted.substring(i);
						}

					}

					// Add String to Button, increase index
					lblBubbles.get(bubbleIndex).setText(formatted);
					bubbleIndex++;
				}
				// Catch exception thrown at when there's nothing left to read
				catch (EOFException e)
				{
					eof = true; 
				}
				catch (IOException e)
				{
				} 
			}

			// Close input stream
			in.close();
		}
		catch (IOException e)
		{
		} 

		// Declare and initialize add button, set properties
		Button btnEnter = new Button();
		btnEnter.setStyle(
				"-fx-background-radius: 20em; " +
						"-fx-background-color: rgb(75, 75, 75);"
				);
		btnEnter.setPrefSize(txtInput.getPrefHeight(), txtInput.getPrefHeight());
		btnEnter.setGraphic(new ImageView(new Image("file:images/plus.png", 35, 35, true, true)));
		btnEnter.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {

				// Add worry to a bubble
				add();
			}
		});
		// Change Button when mouse enters and exits it
		btnEnter.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				btnEnter.setStyle(
						"-fx-background-radius: 20em; " +
								"-fx-background-color: rgb(100, 100, 100) "
						);
			}
		});
		btnEnter.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				btnEnter.setStyle(
						"-fx-background-radius: 20em;" +
								"-fx-background-color: rgb(75, 75, 75);"
						);
			}
		});

		// Add TextField and button to HBox, add HBox to the bottom of Border Pane
		hbBottom.getChildren().addAll(txtInput, btnEnter);
		root.setBottom(hbBottom);


		// Loop through all Labels
		for (int i = 0; i < lblBubbles.size(); i++)
		{
			// Store temporary variable
			int index = i;

			// Set behaviour when Labels are pressed
			lblBubbles.get(i).setOnMousePressed(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent e) {

					// Check if Button is used
					if (lblBubbles.get(index).getText().length() != 0)
					{
						// Declare, initialize Alert to get user action
						Alert alert = new Alert(AlertType.CONFIRMATION); 
						alert.setTitle("Release");
						alert.setContentText("What do you want to do with this worry?"); 
						alert.setHeaderText("\"" + lblBubbles.get(index).getText() + "\"");

						// Declare and initialize custom buttons to edit, release, and cancel
						ButtonType btEdit = new ButtonType("Edit"); 
						ButtonType btOvercome = new ButtonType("Overcome"); 
						ButtonType btCancel = new ButtonType("Cancel",
								ButtonData.CANCEL_CLOSE);

						// Add ButtonTypes to alert
						alert.getButtonTypes().clear();
						alert.getButtonTypes().addAll(btEdit, btOvercome, btCancel);

						// Show the alert, wait and obtain result
						Optional<ButtonType> result = alert.showAndWait();

						// Check for the correct button, do appropriate action
						if (result.get() == btEdit)
						{

							// Declare and Initialize TID for editing
							TextInputDialog dialog = new TextInputDialog(); 
							dialog.setTitle("Edit"); 
							dialog.setHeaderText("\"" + lblBubbles.get(index).getText() + "\""); 
							dialog.setContentText(null);

							// Get user's edit
							Optional<String> edit = dialog.showAndWait();

							// Check if there's a result
							if (edit.isPresent())
							{
								// Ensure it's the right length and not empty
								if(edit.get().length() > 0 && edit.get().length() < MAX_LENGTH)
								{
									String editString = edit.get();

									// Replace the worry LinkedList with the edited version of the thought
									worries.set(index, editString);

									// Add a new line in the appropriate spot
									// Look at every certain number of characters/ the limit of the Label
									for (int i = 30; i < editString.length(); i+=30)
									{
										// Declare and initialize the index of the of the last space in the line
										int spaceIndex = editString.lastIndexOf(" ", i);

										// Look for the last space, add a new line
										if (spaceIndex > 0)
										{										
											// Set new formatted text from index 0 to the index before the space, add a new line, append the rest of the text
											editString = editString.substring(0, spaceIndex) + "\n" + editString.substring(spaceIndex + 1);
										}
										else
										{
											// Divide the text into two lines (even if it's not at a space)
											editString = editString.substring(0, i) + "\n" + editString.substring(i);
										}

									}

									// Add String to Buttons
									lblBubbles.get(index).setText(editString);
								}
								else
								{
									//Alert that the edit isn't the right size
									Alert alertWrongSize = new Alert(AlertType.ERROR); 
									alertWrongSize.setHeaderText(null); 
									alertWrongSize.setContentText("The edit is too long or short. It must be between 1 - 90 characters");
									alertWrongSize.showAndWait();
								}
							}


						}
						// Check if user wants to delete a bubble
						else if (result.get() == btOvercome)
						{
							// Remove deleted Bubble
							worries.remove(index);

							// Loop through all labels, set their test with corresponding worry
							for (int i = 0; i < worries.size(); i ++)
							{
								// Add new lines in the appropriate spaces
								formatted = new String(worries.get(i));

								// Look at every certain number of characters/ the limit of the Label
								for (int q = 30; q < formatted.length(); q+=30)
								{
									// Declare and initialize the index of the of the last space in the line
									int spaceIndex = formatted.lastIndexOf(" ", q);

									// Look for the last space, add a new line
									if (spaceIndex > 0)
									{
										// Set new formatted text from index 0 to the index before the space, add a new line, append the rest of the text
										formatted = formatted.substring(0, spaceIndex) + "\n" + formatted.substring(spaceIndex + 1);
									}
									else
									{
										// Divide the text into two lines (even if it's not at a space)
										formatted = formatted.substring(0, q) + "\n" + formatted.substring(q);
									}

								}
								lblBubbles.get(i).setText(formatted);
							}

							// Loop through the non-existent worries
							for (int i = 10 - 1; i > (worries.size()-1); i--)
							{
								// Dark mode
								if (MainLax.d.equals("d"))
								{
									// Set their Labels to gray and empty the text output again
									lblBubbles.get(i).setStyle("-fx-background-radius: 20em; " +
											"-fx-text-fill: black;" +
											"-fx-font-family: Avenir;" +
											"-fx-font-size: 12pt;" +
											"-fx-background-color: rgb(200, 200, 200)"
											);
								}
								else
								{
									// Set their Labels to gray and empty the text output again
									lblBubbles.get(i).setStyle("-fx-background-radius: 20em; " +
											"-fx-text-fill: white;" +
											"-fx-font-family: Avenir;" +
											"-fx-font-size: 12pt;" +
											"-fx-background-color: rgb(200, 200, 200)"
											);
								}

								lblBubbles.get(i).setText("");
							}

							// Decrease bubble index
							bubbleIndex--;
						}
					}
				}
			});
		}




		// Declare, initialize Scene object, set properties, show
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		setTitle("Release");
		setScene(scene);
		show();

		// When user clicks the x button
		setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent e) {

				// If worries is not empty, then prompt user to save
				// Declare, initialize Alert to get user action
				Alert alert = new Alert(AlertType.CONFIRMATION); 
				alert.setTitle("Save");
				alert.setContentText("Would you like to save any changes in your worries?"); 
				alert.setHeaderText(null);

				// Add ButtonTypes to alert
				alert.getButtonTypes().clear();
				alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);

				// Show the alert, wait and obtain result
				Optional<ButtonType> result = alert.showAndWait();

				// Check for the correct button, do appropriate action
				if (result.get() == ButtonType.YES)
				{
					try {
						// Declare and initialize DataOutputStream object
						DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("release.dat")));

						// Loop through worries LinkedList, write the data on the binary file
						for(int i = 0; i < worries.size(); i++)
						{
							out.writeUTF(worries.get(i));
						}

						// Close output stream
						out.close();
					}
					catch(IOException ea)
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
	
	// Called when user wants to add a worry
	public void add()
	{
		// Check if the TextField is not empty
		if (txtInput.getText().length() != 0)
		{
			// Check if there is enough room in the LL of worries
			if (worries.size() < 10)
			{
				// Loop through LinkedList until there's an empty spot
				// Add the worry into LinkedList, find the first empty button (not null), set String in LinkedList as the text
				worries.add(txtInput.getText());

				// Check length
				if (txtInput.getText().length() <= MAX_LENGTH)
				{
					// Change Button colour					
					// Dark mode
					if (MainLax.d.equals("d"))
					{
						// Set their Labels to gray and empty the text output again
						lblBubbles.get(bubbleIndex).setStyle("-fx-background-radius: 20em; " +
								"-fx-text-fill: black;" +
								"-fx-font-family: Avenir;" +
								"-fx-font-size: 12pt;" +
								"-fx-background-color: " + purples[bubbleIndex] + ";"
								);
					}
					else
					{
						// Set their Labels to gray and empty the text output again
						lblBubbles.get(bubbleIndex).setStyle("-fx-background-radius: 20em; " +
								"-fx-text-fill: white;" +
								"-fx-font-family: Avenir;" +
								"-fx-font-size: 12pt;" +
								"-fx-background-color: " + purples[bubbleIndex] + ";"
								);
					}

					// Add new lines in the appropriate spaces
					formatted = new String(worries.getLast());

					// Look at every certain number of characters/ the limit of the Label
					for (int i = 30; i < formatted.length(); i+=30)
					{
						// Declare and initialize the index of the of the last space in the line
						int spaceIndex = formatted.lastIndexOf(" ", i);

						// Look for the last space, add a new line
						if (spaceIndex > 0)
						{
							// Set new formatted text from index 0 to the index before the space, add a new line, append the rest of the text
							formatted = formatted.substring(0, spaceIndex) + "\n" + formatted.substring(spaceIndex + 1);
						}
						else
						{
							// Divide the text into two lines (even if it's not at a space)
							formatted = formatted.substring(0, i) + "\n" + formatted.substring(i);
						}

					}

					// Add String to Button
					lblBubbles.get(bubbleIndex).setText(formatted);bubbleIndex++;

					// Clear TextField
					txtInput.clear();
				}
				else
				{
					// Show Alert error when worry is too long
					Alert alert = new Alert(AlertType.ERROR);
					alert.setHeaderText(null);
					alert.setContentText("Thought must be less than 90 characters long.");
					alert.showAndWait();
				}
			}
			else
			{
				// Alert that there is no more room in LinkedList
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setContentText("Too many worries! Focus on the ones you have and press a thought bubble when you're ready to overcome it.");
				alert.showAndWait();
			}
		}
		else
		{
			// User has not typed anything, output Alert
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText("You didn't type anything. Reflect on what makes you nervous and add it.");
			alert.showAndWait();
		}

	}
}



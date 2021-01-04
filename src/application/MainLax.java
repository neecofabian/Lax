/*
 * Neeco Fabian
 * 14 June 2019
 */


package application;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;


public class MainLax extends Application {

	public static String password, firstRelease;
	private PasswordField txtCreatePassword, txtEnterPassword;
	private File f;
	public static String d;
	private ImageView ivLax;
	private BorderPane root;
	private Label lblMsg;
	private Button btnSettings;

	public void start(Stage primaryStage) {
		try {

			// Dark mode
			d = "l";
			
			// Initialize variable representing if it is the user's first time opening the Release section of the app
			firstRelease = "y";

			// Set default password
			password = "";

			// READ BINARY FILES to obtain previously stored firstRelease and password values
			try {

				// Declare and initialize DataInputStream object
				DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream("initial.dat")));

				// Declare and initialize value that determines if EOFException is thrown at the end of the file
				boolean eof = false;

				while (!eof)
				{
					try {

						// Obtain dark mode preference, password and determine if this is the first time the user has logged on
						firstRelease = in.readUTF();
						password = in.readUTF();
						d = in.readUTF();

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

				// Close in stream object
				in.close();
			}
			catch (IOException e)
			{
			} 

			// Declare and initialize BorderPane, Scene objects, get style sheets
			root = new BorderPane();
			Scene scene = new Scene(root,420,695);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			// Declare and initialize label for app descriptions, set properties
			lblMsg= new Label();
			if (d.equalsIgnoreCase("d"))
			{
				lblMsg.setStyle(
						"-fx-text-fill: rgb(170, 170, 170);"+
								"-fx-font-family: Avenir;" +
								"-fx-font-style: italic;"+
								"-fx-font-weight: bold;" +
								"-fx-font-size: 18;" +
								"-fx-background-color: transparent; "
						);
			}
			else
			{
				lblMsg.setStyle(
						"-fx-text-fill: rgb(50, 50, 50);"+
								"-fx-font-family: Avenir;" +
								"-fx-font-style: italic;"+
								"-fx-font-weight: bold;" +
								"-fx-font-size: 18;" +
								"-fx-background-color: transparent; "
						);
			}
			lblMsg.setText("Balance work and wellness");			
			lblMsg.setPadding(new Insets(20, 0, 0, 0));
			lblMsg.setAlignment(Pos.CENTER);


			// Set Button properties after initializing and declaring, repeat for all 3
			Button btnBreathe = new Button("breathe");
			btnBreathe.setPrefSize(300, 100);
			btnBreathe.setFocusTraversable(false);
			btnBreathe.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {

					// Pause music, open app
					primaryStage.hide();

					new Breathe(primaryStage);
				}
			});
			btnBreathe.setStyle(
					"-fx-background-radius: 40em; " +
							"-fx-text-fill: white;"+
							"-fx-font-family: Avenir;" +
							"-fx-font-weight: bold;" +
							"-fx-font-size: 40;" +
							"-fx-background-color: rgb(56, 149, 211); "
					);
			// Change Button when mouse enters and exits it
			btnBreathe.setOnMouseEntered(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent e) {
					btnBreathe.setStyle(
							"-fx-background-radius: 40em; " +
									"-fx-text-fill: white;"+
									"-fx-font-family: Avenir;" +
									"-fx-font-weight: bold;" +
									"-fx-font-size: 40;" +
									"-fx-background-color: rgb(16, 125, 172); "
							);
					lblMsg.setText("Control your calmness");
				}
			});
			btnBreathe.setOnMouseExited(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent e) {
					btnBreathe.setStyle(
							"-fx-background-radius: 40em; " +
									"-fx-text-fill: white;"+
									"-fx-font-family: Avenir;" +
									"-fx-font-weight: bold;" +
									"-fx-font-size: 40;" +
									"-fx-background-color: rgb(56, 149, 211); "
							);
					lblMsg.setText("Balance work and wellness");
				}
			});

			// Declare, initialize, set properties
			Button btnRelease = new Button("release");
			btnRelease.setPrefSize(300, 100);
			btnRelease.setFocusTraversable(false);
			btnRelease.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {

					// Check if this is the first time user has gone to Release
					if (firstRelease.equals("y"))
					{
						// Declare and initialize a Dialog object for password
						Dialog<ButtonType> dialog = new Dialog<ButtonType>(); 
						dialog.setTitle("Create New Password"); 
						dialog.setHeaderText(null);

						// Declare and initialize ButtonType objects
						ButtonType btCreate = new ButtonType("Create"); 
						ButtonType btCancel = new ButtonType("Cancel");
						// Add the buttons to the dialog pane
						dialog.getDialogPane().getButtonTypes().addAll(btCreate, btCancel);

						// Initialize PasswordField object
						txtCreatePassword = new PasswordField(); 

						// Declare and initialize GridPane object
						GridPane grid = new GridPane(); grid.setHgap(10);
						grid.setVgap(10);
						grid.setPadding(new Insets(20, 150, 10, 10));
						grid.add(txtCreatePassword, 1, 0);

						// Prompt user to create password
						grid.add(new Label("Create a password:"), 0, 0);

						// Add the pane to the dialog
						dialog.getDialogPane().setContent(grid);

						// Show the dialog
						Optional<ButtonType> result = dialog.showAndWait();

						// Check user selection
						if (result.isPresent())
						{
							if (result.get() == btCreate)
							{
								// Check if new password is not the default
								if (txtCreatePassword.getText().length() >0)
								{
									// Store password
									password = txtCreatePassword.getText();

									// Indicate that the user has entered Release at least once
									firstRelease = "n";

									// Open Release
									primaryStage.hide();
									new Release(primaryStage);
								}
							}
						}

					}
					else
					{
						// Declare and initialize a Dialog object for password
						Dialog<ButtonType> dialog = new Dialog<ButtonType>(); 
						dialog.setTitle("Enter Password"); 
						dialog.setHeaderText(null);

						// Declare and initialize ButtonType objects
						ButtonType btEnter = new ButtonType("Enter"); 
						ButtonType btCancel = new ButtonType("Cancel");
						// Add the buttons to the dialog pane
						dialog.getDialogPane().getButtonTypes().addAll(btEnter, btCancel);

						// Initialize PasswordField object
						txtEnterPassword = new PasswordField(); 

						// Declare and initialize GridPane object, set properties
						GridPane grid = new GridPane(); grid.setHgap(10);
						grid.setVgap(10);
						grid.setPadding(new Insets(20, 150, 10, 10));
						grid.add(txtEnterPassword, 1, 0);

						// Prompt user to create password
						grid.add(new Label("Enter password:"), 0, 0);

						// Add the pane to the dialog
						dialog.getDialogPane().setContent(grid);
						// Prompt user to enter password

						// Show the dialog
						Optional<ButtonType> result = dialog.showAndWait();

						// Check user selection
						if (result.isPresent())
						{
							if (result.get() == btEnter)
							{
								if (txtEnterPassword.getText().equals(password)) 
								{

									// User is correct
									primaryStage.hide();
									new Release(primaryStage);
								} 
								else 
								{
									// Show Alert if incorrect
									Alert alert = new Alert(AlertType.NONE);
									alert.setHeaderText(null);
									alert.setContentText("The password is incorrect!"); 
									alert.setTitle("Incorrect"); alert.setAlertType(AlertType.ERROR);
									alert.showAndWait();
								}
							}
						}
					}					
				}
			});
			btnRelease.setStyle(
					"-fx-background-radius: 40em; " +
							"-fx-text-fill: white;"+
							"-fx-font-family: Avenir;" +
							"-fx-font-weight: bold;" +
							"-fx-font-size: 40;" +
							"-fx-background-color: rgb(172, 73, 245); "
					);
			// Change Button when mouse enters and exits it
			btnRelease.setOnMouseEntered(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent e) {
					btnRelease.setStyle(
							"-fx-background-radius: 40em; " +
									"-fx-text-fill: white;"+
									"-fx-font-family: Avenir;" +
									"-fx-font-weight: bold;" +
									"-fx-font-size: 40;" +
									"-fx-background-color: rgb(121, 81, 168); "
							);
					lblMsg.setText("Manage your worries");
				}
			});
			btnRelease.setOnMouseExited(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent e) {
					btnRelease.setStyle(
							"-fx-background-radius: 40em; " +
									"-fx-text-fill: white;"+
									"-fx-font-family: Avenir;" +
									"-fx-font-weight: bold;" +
									"-fx-font-size: 40;" +
									"-fx-background-color: rgb(172, 73, 245); "
							);
					lblMsg.setText("Balance work and wellness");
				}
			});

			// Declare, initialize, set properties
			Button btnRemember = new Button("remember");
			btnRemember.setPrefSize(300, 100);
			btnRemember.setFocusTraversable(false);
			btnRemember.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					// Pause music, open app
					primaryStage.hide();
					new Remember(primaryStage);
				}
			});
			btnRemember.setStyle(
					"-fx-background-radius: 40em; " +
							"-fx-text-fill: white;"+
							"-fx-font-family: Avenir;" +
							"-fx-font-weight: bold;" +
							"-fx-font-size: 40;" +
							"-fx-background-color: rgb(57, 168, 123); "
					);
			// Change Button when mouse enters and exits it
			btnRemember.setOnMouseEntered(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent e) {
					btnRemember.setStyle(
							"-fx-background-radius: 40em; " +
									"-fx-text-fill: white;"+
									"-fx-font-family: Avenir;" +
									"-fx-font-weight: bold;" +
									"-fx-font-size: 40;" +
									"-fx-background-color: rgb(66, 118, 103); "
							);
					lblMsg.setText("Train your brain");
				}
			});
			btnRemember.setOnMouseExited(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent e) {
					btnRemember.setStyle(
							"-fx-background-radius: 40em; " +
									"-fx-text-fill: white;"+
									"-fx-font-family: Avenir;" +
									"-fx-font-weight: bold;" +
									"-fx-font-size: 40;" +
									"-fx-background-color: rgb(57, 168, 123); "
							);
					lblMsg.setText("Balance work and wellness");
				}
			});
			
			// Set Logo image
			if (d.equalsIgnoreCase("d"))
			{
				// Declare, initialize, set properties of logo
				ivLax = new ImageView(new Image("file:images/lax_white_logo.png", 300, 400, true, true));
			}
			else
			{
				// Declare, initialize, set properties of logo
				ivLax = new ImageView(new Image("file:images/lax_logo.png", 300, 400, true, true));
				
			}
			ivLax.setStyle("-fx-background-color: transparent");

			
			

			
			
			// Declare and initialize settings Button, make it dark
			btnSettings = new Button();
			btnSettings.setAlignment(Pos.CENTER);
			btnSettings.setPadding(new Insets(0,0,30,0));
			btnSettings.setStyle("-fx-background-color: transparent");
			if (d.equals("d"))
			{
				btnSettings.setGraphic(new ImageView(new Image("file:images/settings_light.png", 50, 50, true, true)));
			}
			else
			{
				btnSettings.setGraphic(new ImageView(new Image("file:images/settings_dark.png", 50, 50, true, true)));
			}
			btnSettings.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {

					// Declare and initialize a Dialog object
					Dialog<ButtonType> dialog = new Dialog<ButtonType>(); 
					dialog.setTitle("General Preferences"); 
					dialog.setHeaderText("Lax improves mental health and productivity in three steps.");

					// Declare and initialize ButtonType objects
					ButtonType btDone = new ButtonType("DONE"); 
					ButtonType btCancel = new ButtonType("CANCEL");

					// Add Buttons to the dialog
					dialog.getDialogPane().getButtonTypes().addAll(btDone, btCancel);

					// Declare and Initialize GridPane to format buttons, labels, and ComboBoxes; set properties
					GridPane grid = new GridPane(); 
					grid.setHgap(20);
					grid.setVgap(20);
					grid.setPadding(new Insets(20, 20, 5, 20));

					Label lblTheme = new Label("Choose App Theme:");	
					Label lblReset = new Label("Reset App:");
					
					// Declare and initialize Reset Button
					Button btnReset = new Button("RESET");
					btnReset.setOnAction(new EventHandler<ActionEvent>() {
						public void handle(ActionEvent e) {
							// Declare and initialize a Dialog object for password
							Dialog<ButtonType> dialog = new Dialog<ButtonType>(); 
							dialog.setTitle("Reset App"); 
							dialog.setHeaderText("Reset app to default state. All settings, data, and the password will be erased. This cannot be undone.");

							// Declare and initialize ButtonType objects
							ButtonType btReset = new ButtonType("Reset"); 
							ButtonType btCancel = new ButtonType("Cancel");
							
							// Add the buttons to the dialog pane
							dialog.getDialogPane().getButtonTypes().addAll(btReset, btCancel);

							// Initialize PasswordField object
							txtEnterPassword = new PasswordField(); 

							// Declare and initialize GridPane object, set properties
							GridPane grid = new GridPane(); grid.setHgap(10);
							grid.setVgap(10);
							grid.setPadding(new Insets(20, 150, 10, 10));
							grid.add(txtEnterPassword, 1, 0);

							// Prompt user to create password
							grid.add(new Label("Enter password:"), 0, 0);

							// Add the pane to the dialog
							dialog.getDialogPane().setContent(grid);
							
							// Prompt user to enter password

							// Show the dialog
							Optional<ButtonType> result = dialog.showAndWait();

							// Check user selection
							if (result.isPresent())
							{
								if (result.get() == btReset)
								{
									if (txtEnterPassword.getText().equals(password)) 
									{
										System.out.println("RESET THE APP");
										
										// Reset initial variables
										d = "l";
										firstRelease = "y";
										password = "";
										
										// Reset Release
										try {
											// Declare and initialize DataOutputStream object
											DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("release.dat")));

											// Close output stream
											out.close();
										}
										catch(IOException ea)
										{
										}
										
										// Reset Remember
										try {
											// Declare and initialize a BufferedWriter object, save to text file
											BufferedWriter out = new BufferedWriter(new FileWriter("flashcards.txt"));


											// Close the output stream
											out.close();
										}
										catch (IOException f)
										{
										}
										
										
										// Show Alert if incorrect
										Alert alert = new Alert(AlertType.INFORMATION);
										alert.setHeaderText(null);
										alert.setContentText("The app settings have been reset."); 
										alert.setTitle("Reset");
										alert.showAndWait();
									} 
									else 
									{
										// Show Alert if incorrect
										Alert alert = new Alert(AlertType.NONE);
										alert.setHeaderText(null);
										alert.setContentText("The password is incorrect!"); 
										alert.setTitle("Incorrect"); alert.setAlertType(AlertType.ERROR);
										alert.showAndWait();
									}
								}
							}
						}				
						
					});
					
					// Declare and initialize ComboBoxes for theme preferences
					ComboBox<String> cboTheme = new ComboBox<String>();
					cboTheme.getItems().addAll("Light", "Dark");
					if(d.equals("d"))
					{
						cboTheme.setValue("Dark");
					}
					else
					{
						cboTheme.setValue("Light");
					}
					// Obtain theme preference when ComboBox is changed
					cboTheme.setOnAction(new EventHandler<ActionEvent>() { 
						public void handle(ActionEvent e) {
							System.out.println("Change Detected");
							
							if (cboTheme.getValue().equals("Dark"))
							{
								System.out.println("Dark Chosen");
								d = "d";
							}
							else
							{
								d = "l";
								System.out.println("Light chosen");
							}
						} 
						});

					
					// Add nodes to GridPane
					grid.add(lblTheme, 0, 0);
					grid.add(cboTheme, 1, 0);
					grid.add(lblReset, 0, 1);
					grid.add(btnReset, 1, 1);

					// Add GridPane to dialog box
					dialog.getDialogPane().setContent(grid);

					// Check for user button press
					Optional<ButtonType> result = dialog.showAndWait();

					// Check if the user presses a button
					if (result.isPresent())
					{
						// Check if user presses done
						if (result.get() == btDone)
						{
							// Update variables
							writeOut();
							refresh();
						}
					}
				
				}
			});

			// Mouse enters
			btnSettings.setOnMouseEntered(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent e) {
					// Dark button
					if (d.equals("d"))
					{
						btnSettings.setGraphic(new ImageView(new Image("file:images/settings_dark.png", 50, 50, true, true)));
					}
					else
					{
						btnSettings.setGraphic(new ImageView(new Image("file:images/settings_light.png", 50, 50, true, true)));
					}
					lblMsg.setText("Preferences");
				}
			});
			// Mouse exits
			btnSettings.setOnMouseExited(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent e) {
					//Light button
					if (d.equals("d"))
					{
						btnSettings.setGraphic(new ImageView(new Image("file:images/settings_light.png", 50, 50, true, true)));
					}
					else
					{
						btnSettings.setGraphic(new ImageView(new Image("file:images/settings_dark.png", 50, 50, true, true)));
					}
					lblMsg.setText("Balance work and wellness");
				}
			});

			// Declare, initialize, set properties of Vbox
			VBox vb = new VBox(15);
			vb.setPadding(new Insets(40, 30, 30, 30));
			vb.getChildren().addAll(btnBreathe, btnRelease, btnRemember, lblMsg);
			vb.setAlignment(Pos.CENTER);
			// Center all nodes
			BorderPane.setAlignment(ivLax, Pos.CENTER);
			BorderPane.setAlignment(vb, Pos.CENTER);
			BorderPane.setAlignment(btnSettings, Pos.CENTER);

			// Add nodes to BorderPane, set padding
			root.setTop(ivLax);
			root.setCenter(vb);
			root.setBottom(btnSettings);		
			root.setPadding(new Insets(50, 40, 50, 40));

			
			
			// Refresh for theme
			refresh();
			

			// Set stage properties
			primaryStage.setTitle("Lax");
			primaryStage.setScene(scene);
			primaryStage.show();

			// When user clicks X button
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent e) {
					writeOut();

					// Set Alert object properties
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Lax");
					alert.setHeaderText(null);
					alert.setContentText("Do you want to leave Lax?");
					alert.getButtonTypes().clear();
					alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);

					// Show the Alert, wait for a response
					Optional<ButtonType> result = alert.showAndWait();

					// Check if user wants to exit, show goodbye message
					if (result.get() == ButtonType.YES)
					{

						// Close Lax
						System.exit(0);

					}
					else
					{
						// Abort close request
						e.consume();
					}

				}
			});
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void writeOut() {
		try 
		{
			// Declare and initialize DataOutputStream object
			DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("initial.dat")));

			// Write important variables on the file
			out.writeUTF(firstRelease);	
			out.writeUTF(password);
			out.writeUTF(d);

			// Close output stream
			out.close();
		}
		catch(IOException ea)
		{
		}

	}
	
	// Refresh for dark mode
	public void refresh() {
		// Dark Mode
		if (d.equalsIgnoreCase("d"))
		{
			lblMsg.setStyle(
					"-fx-text-fill: rgb(170, 170, 170);"+
							"-fx-font-family: Avenir;" +
							"-fx-font-style: italic;"+
							"-fx-font-weight: bold;" +
							"-fx-font-size: 18;" +
							"-fx-background-color: transparent; "
					);
			root.setStyle("-fx-background-color: rgb(0, 0, 0)");

			// Declare, initialize, set properties of logo
			ivLax.setImage(new Image("file:images/lax_white_logo.png", 300, 400, true, true));
			btnSettings.setGraphic(new ImageView(new Image("file:images/settings_light.png", 50, 50, true, true)));
		}
		else
		{
			lblMsg.setStyle(
					"-fx-text-fill: rgb(50, 50, 50);"+
							"-fx-font-family: Avenir;" +
							"-fx-font-style: italic;"+
							"-fx-font-weight: bold;" +
							"-fx-font-size: 18;" +
							"-fx-background-color: transparent; "
					);
			root.setStyle("-fx-background-color: rgb(255, 255, 255)");

			// Declare, initialize, set properties of logo
			ivLax.setImage(new Image("file:images/lax_logo.png", 300, 400, true, true));
			btnSettings.setGraphic(new ImageView(new Image("file:images/settings_dark.png", 50, 50, true, true)));
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
}

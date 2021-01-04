/*
 * Neeco Fabian
 * 14 June 2019
 */


package application;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class FlashCard extends TextField{

	private String front, back;
	private boolean frontShown;
	private int index;
	private boolean green;

	public FlashCard() {

		// Initialize class variables
		front = "";
		back = "";
		frontShown = true;
		index = 0;
		green = true;

		// Set TextArea properties
		this.setPrefWidth(1200);
		this.setPrefHeight(50);
		this.setStyle("-fx-font-family: Avenir;" + 
				"-fx-font-size: 18pt;" + 
				"-fx-font-weight: normal;" +
				"-fx-text-fill: white;"+ 
				"-fx-background-color: rgb(57, 168, 123);" +
				"-fx-background-radius: 20em; "
				);
		this.setPadding(new Insets(10, 20, 10, 20));

		// Detect triple click and switch from one side of fc to the other
		this.setOnMouseClicked(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent e) {

				// Check for double click, set to the opposite side
				if (e.getClickCount() == 3)
				{
					// Reverse the sides
					if (frontShown)
					{
						// Set the FlashCard to back
						setFrontShown(false);						
						setText(back);		

						if (isGreen())
						{
							setStyle("-fx-font-family: Avenir;" + 
									"-fx-font-size: 18pt;" + 
									"-fx-font-weight: normal;" +
									"-fx-text-fill: black;"+ 
									"-fx-background-color: rgb(66, 229, 163);" +
									"-fx-background-radius: 20em; "
									);
						}
						else
						{
							setStyle("-fx-font-family: Avenir;" + 
									"-fx-font-size: 18pt;" + 
									"-fx-font-weight: normal;" +
									"-fx-text-fill: black;"+ 
									"-fx-background-color: rgb(249, 135, 127);" +
									"-fx-background-radius: 20em; "
									);
						}
					}
					else
					{
						// Set the FlashCard to front
						setFrontShown(true);
						setText(front);						
						if (isGreen())
						{
							setStyle("-fx-font-family: Avenir;" + 
									"-fx-font-size: 18pt;" + 
									"-fx-font-weight: normal;" +
									"-fx-text-fill: white;"+ 
									"-fx-background-color: rgb(57, 168, 123);" +
									"-fx-background-radius: 20em; "
									);
						}
						else
						{
							setStyle("-fx-font-family: Avenir;" + 
									"-fx-font-size: 18pt;" + 
									"-fx-font-weight: normal;" +
									"-fx-text-fill: white;"+ 
									"-fx-background-color: rgb(242, 68, 55);" +
									"-fx-background-radius: 20em; "
									);
						}
					}
				}

			}
		});

		// Store typed information
		this.setOnKeyTyped(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent e) {
				if (frontShown)
				{
					front = getText();
				}
				else
				{
					back = getText();
				}
			}
		});
	}

	// Set constructor to set custom index
	public FlashCard(int index) {

		front = "";
		back = "";
		frontShown = true;
		this.index = index;
		green = true;


		// Set TextArea properties
		this.setPrefWidth(1200);
		this.setPrefHeight(50);
		this.setStyle("-fx-font-family: Avenir;" + 
				"-fx-font-size: 18pt;" + 
				"-fx-font-weight: normal;" +
				"-fx-text-fill: white;"+ 
				"-fx-background-color: rgb(57, 168, 123);" +
				"-fx-background-radius: 20em; "
				);
		this.setPadding(new Insets(10, 20, 10, 20));
		
		// When user presses enter
		this.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				if (e.getCode() == KeyCode.ENTER)
				{
					reverseSides();
				}
			}
		});

		// Store typed information
		this.setOnKeyTyped(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent e) {
				if (frontShown)
				{
					front = getText();
				}
				else
				{
					back = getText();
				}
			}
		});


	}



	// ACCESSOR METHODS

	/**
	 * Returns the information on the front side of the FlashCard
	 *
	 * @return	front - info on the FlashCard's front
	 */
	public String getFront(){
		return front;
	}

	/**
	 * Returns the information on the front back of the FlashCard
	 *
	 * @return	back - info on the FlashCard's back
	 */
	public String getBack(){
		return back;
	}

	/**
	 * Returns whether or not the FlashCard's front side is being shown
	 *
	 * @return	frontShown -  Indicates if the FlashCard's front is being shown
	 */
	public boolean getFrontShown(){
		return frontShown;
	}

	/**
	 * Returns FlashCard's index
	 *
	 * @return	index -  Indicates FlashCard's current index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Returns is FlashCard is green
	 *
	 * @return	green -  Indicates if FlashCard is green
	 */
	public boolean isGreen() {
		return green;
	}





	// MUTATOR METHODS

	/**
	 * Sets the information on the front side of the FlashCard
	 *
	 * @param 	f - information on FlashCard's front
	 */
	public void setFront(String f) {
		front = f;
	}

	/**
	 * Sets the information on the back side of the FlashCard
	 *
	 * @param 	b - information on FlashCard's back
	 */
	public void setBack(String b) {
		back = b;
	}

	/**
	 * Sets if the FlashCard is being shown
	 *
	 * @param 	frontShown - indicates if the front of FlashCard is being shown
	 */
	public void setFrontShown(boolean frontShown) {
		this.frontShown = frontShown;
	}

	/**
	 * Sets if the FlashCard's index
	 *
	 * @param 	i - FlashCard's index
	 */
	public void setIndex(int i) {
		index = i;
	}

	/**
	 * Sets the FlashCard to green
	 *
	 * @param 	g - sets the FlashCard to green
	 */
	public void setGreen(boolean g) {
		green = g;
	}


	/**
	 * Sets the FlashCard color
	 */
	public void setColor() {

		// Set the right color to the right side
		if (frontShown)
		{	
			if (isGreen())
			{
				setStyle("-fx-font-family: Avenir;" + 
						"-fx-font-size: 18pt;" + 
						"-fx-font-weight: normal;" +
						"-fx-text-fill: white;"+ 
						"-fx-background-color: rgb(57, 168, 123);" +
						"-fx-background-radius: 20em; "
						);
			}
			else
			{
				setStyle("-fx-font-family: Avenir;" + 
						"-fx-font-size: 18pt;" + 
						"-fx-font-weight: normal;" +
						"-fx-text-fill: white;"+ 
						"-fx-background-color: rgb(242, 68, 55);" +
						"-fx-background-radius: 20em; "
						);
			}

		}
		else
		{				
			if (isGreen())
			{
				setStyle("-fx-font-family: Avenir;" + 
						"-fx-font-size: 18pt;" + 
						"-fx-font-weight: normal;" +
						"-fx-text-fill: black;"+ 
						"-fx-background-color: rgb(66, 229, 163);" +
						"-fx-background-radius: 20em; "
						);
			}
			else
			{
				setStyle("-fx-font-family: Avenir;" + 
						"-fx-font-size: 18pt;" + 
						"-fx-font-weight: normal;" +
						"-fx-text-fill: black;"+ 
						"-fx-background-color: rgb(249, 135, 127);" +
						"-fx-background-radius: 20em; "
						);
			}
		}
	}

	/**
	 *  Shows the other side of the FlashCard
	 */
	public void reverseSides() {
		// Reverse the sides
		if (frontShown)
		{
			// Set the FlashCard to back
			setFrontShown(false);						
			setText(back);		

			if (isGreen())
			{
				setStyle("-fx-font-family: Avenir;" + 
						"-fx-font-size: 18pt;" + 
						"-fx-font-weight: normal;" +
						"-fx-text-fill: black;"+ 
						"-fx-background-color: rgb(66, 229, 163);" +
						"-fx-background-radius: 20em; "
						);
			}
			else
			{
				setStyle("-fx-font-family: Avenir;" + 
						"-fx-font-size: 18pt;" + 
						"-fx-font-weight: normal;" +
						"-fx-text-fill: black;"+ 
						"-fx-background-color: rgb(249, 135, 127);" +
						"-fx-background-radius: 20em; "
						);
			}
		}
		else
		{
			// Set the FlashCard to front
			setFrontShown(true);
			setText(front);						
			if (isGreen())
			{
				setStyle("-fx-font-family: Avenir;" + 
						"-fx-font-size: 18pt;" + 
						"-fx-font-weight: normal;" +
						"-fx-text-fill: white;"+ 
						"-fx-background-color: rgb(57, 168, 123);" +
						"-fx-background-radius: 20em; "
						);
			}
			else
			{
				setStyle("-fx-font-family: Avenir;" + 
						"-fx-font-size: 18pt;" + 
						"-fx-font-weight: normal;" +
						"-fx-text-fill: white;"+ 
						"-fx-background-color: rgb(242, 68, 55);" +
						"-fx-background-radius: 20em; "
						);
			}
		}
	}


}
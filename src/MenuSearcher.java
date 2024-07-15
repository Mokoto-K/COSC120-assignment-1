/**
 * @author Joshua Hahn
 * Email: jhahn@myune.edu.au
 * COSC120 - Assignment 1
 * Date: 15/07/24
 */

import javax.swing.*;
import java.util.List;

public class MenuSearcher {

    private static final String filePath = "./menu.txt";

    /**
     * The main method of our program
     */
    public static void main(String[] args) {

    }

    public Menu loadMenu() {
        // TODO - has to read from a file, split up the data, create a coffee object using the split data, and call addcoffee?

        return null;
    }

    public Coffee userCoffee(){
        // TODO - Everything, a series of questions bout coffee
        return userCoffee();
    }

    public String matchedCoffee(Coffee coffee) {
        // TODO - This should be a string... unless its a stringbuilder... but probably a list, just not sure what
        // TODO - this part of the program looks like, most likely a list of coffees to be put in a joptiionpane

        return matchedCoffee(coffee);
    }

    /**
     * Asks the customer for their name and phone number and creates a Geek
     * object using this information
     * @return a Geek object containing our customers information
     */
    public Geek customerDetails(){
        // Ask the user to enter their name and phone number
        String name = JOptionPane.showInputDialog("Please enter your name (First Last)");
        long number = Long.parseLong(JOptionPane.showInputDialog("Please enter your phone number (04xxxxxxxx)"));

        // Return the created geek object from the users input
        return new Geek(name, number);
        // TODO - return statement might not work, test it when i get to it.

    }

    public void sumbitOrder(Geek geek, Coffee coffee){
        // TODO - Unsure if this takes in a coffee yet, will find out soon, prints everything to a txt file
    }

    // TODO - Probably a bunch of regex method checks for input control

}

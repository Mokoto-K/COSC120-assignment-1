/**
 * @author Enter name
 * Email: Enter email
 * COSC120 - Assignment 1
 * Date: 15/07/24
 */

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class MenuSearcher {

    private static final String filePath = "./menu.txt";
    private static final String appName = "The Caffeinated Geek";
    private static final String appIcon = "./the_caffeinated_geek.png";

    /**
     * The main method of our program
     */
    public static void main(String[] args) {
        ImageIcon icon = new ImageIcon(appIcon);
        Menu menu = loadMenu();
        userCoffee();
    }

    // TODO - This is pretty poorly wirtten
    /**
     * Opens a file which is our database of drinks, splits each line up into specific details of
     * each coffee, adds new coffees to list as coffee objects using to information gleamed from
     * the file
     * @return Menu object from the Menu class, used to call and control the classes methods and database
     */
    private static Menu loadMenu() {
        Path path = Path.of(filePath);
        Menu menu = new Menu();

        // Creating a list of strings to temporarily hold our database information so we can iterate through each one.
        List<String> allCoffee = new ArrayList<>();
        try {
            // Read all lines in from out database and place them in a list of strings
            allCoffee = Files.readAllLines(path);

            // Remove the headers from our list of strings
            allCoffee.remove(allCoffee.getFirst());

            for (String line : allCoffee) {
                // We have to split our db up in two different ways, first we need to split on the one square bracket
                // This will separate the milk, extras and description fields from the rest of the data, then it we
                // can just split at comma and do a little bit of data cleaning to remove some unwanted characters.
                String[] splitOne = line.split("\\[");
                String[] coffeeDetails = splitOne[0].split(",");
                // TODO - Consider extracting the splitting to a diff function for a cleaner solution

                // Assigning all of our split strings to their appropriate variables
                long id = Long.parseLong(coffeeDetails[0]);
                String name = coffeeDetails[1];
                float price = Float.parseFloat(coffeeDetails[2]);
                int shots = Integer.parseInt(coffeeDetails[3]);
                String sugar = coffeeDetails[4];
                String[] milk = splitOne[1].replace("],", "").split(",");
                String[] extras = splitOne[2].replace("],", "").split(",");
                String description = splitOne[3];

                // Sending all our variables to our coffee class to create a coffee object
                Coffee coffee = new Coffee(id, name, price, shots, sugar, milk, extras, description);

                // adding the coffee object to our database to query later
                menu.addCoffee(coffee);
            }
        }
        catch (IOException e) {
            System.out.println(e);
            // TODO - Deal with this bertter, joption paone telling a homie it broke
        }

        return menu;
    }

    private static Coffee userCoffee(){
        // TODO - Everything, a series of questions bout coffee
        ImageIcon icon = new ImageIcon(appIcon);

        // Prompt the user on which milk they would like
        // TODO - Figure out what the null 3 null bullshit is about and correct it
        Milk milk = (Milk) JOptionPane.showInputDialog(null, "What type of milk are you looking for?",
                appName,JOptionPane.QUESTION_MESSAGE,icon, Milk.values(), Milk.FULLCREAM);

        // Pormpt user on how many shots they would like
        // TODO - Change this to regex for control
        // TODO - fix the infinite persistence of entering an int when a user hits the close button
        int shots = 0;
        do {
            try {
            shots = Integer.parseInt(JOptionPane.showInputDialog(null,"Enter the number shots you would like?",
                    appName, JOptionPane.QUESTION_MESSAGE));
            }
            catch (NumberFormatException e) {
                // Let the developer know the problem
                System.out.println(e);

                // Let the user know the problem
                JOptionPane.showMessageDialog(null,"Please enter a positive integer", appName,
                        JOptionPane.QUESTION_MESSAGE, icon);
            }
        }
        while (shots == 0);


        int sugarChoice = JOptionPane.showConfirmDialog(null, "Would you like sugar?", appName,
                JOptionPane.YES_NO_OPTION);
        String sugar = "";
        if (sugarChoice == JOptionPane.YES_OPTION) { sugar = "Yes";}
        else {sugar = "No";}


        return null;
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

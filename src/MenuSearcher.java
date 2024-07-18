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
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MenuSearcher {

    private static final String filePath = "./menu.txt";
    private static final String appName = "The Caffeinated Geek";
    private static final String appIcon = "./the_caffeinated_geek.png";
    private static Menu menu;
    /**
     * The main method of our program
     */
    public static void main(String[] args) {
        ImageIcon icon = new ImageIcon(appIcon);
        menu = loadMenu();

        for (Coffee c : menu.compareCoffee(userCoffee())) {
//        List<Coffee> c = menu.compareCoffee(userCoffee());
            System.out.println(c.getName());
        }


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

                // TODO - Explain why this is happening and extras below it
                List<String> tempMilk = List.of(splitOne[1].replace("],", "").split(","));
                List<String> milk = new ArrayList<>();
                for (String milkElement : tempMilk) {
                    if (milkElement.isEmpty()) {
                        milkElement = "None";
                    }
                    milk.add(milkElement.trim());
                }
                // TODO above todo
                List<String> tempExtras = List.of(splitOne[2].replace("],", "").split(","));
                List<String> extras = new ArrayList<>();
                for (String extraElement : tempExtras) {
                    if (extraElement.isEmpty()) {
                        extraElement = "No Extras";
                    }
                    extras.add(extraElement.trim());
                }

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


    /**
     * Asks the customer a series of questions about the drink they would like to order and
     * creates a coffee object containing all of their requirements to be compared against
     * our menu later on.
     * @return Coffee - a coffee object of our customers desired "order"
     */
    // TODO - consider removing all the joptionpanes and making a function that calls a pane instead, passing in the Q
    // TODO- the null pointers... they will be everywhere in here
    private static Coffee userCoffee(){
        // TODO - Everything, a series of questions bout coffee
        ImageIcon icon = new ImageIcon(appIcon);

        // Prompt the user on which milk they would like
        // TODO - Figure out what the null 3 null bullshit is about and correct it
        Milk milk = (Milk) JOptionPane.showInputDialog(null, "What type of milk are you looking for?",
                appName,JOptionPane.QUESTION_MESSAGE,icon, Milk.values(), Milk.FULLCREAM);

        // Prompt user on how many shots they would like
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

        // Prompt the user with a yes or no question for whether or not they would like sugar
        int sugarChoice = JOptionPane.showConfirmDialog(null, "Would you like sugar?", appName,
                JOptionPane.YES_NO_OPTION);
        String sugar = "";
        if (sugarChoice == JOptionPane.YES_OPTION) { sugar = "yes";}
        else {sugar = "no";}

        // TODO - Re explain all of this as it got tehnical with the whole skip and add all situation
        // Initiating an int to be used for keeping track of extras
        List<String> extras = new ArrayList<>();
        int decision = 0;
        while (decision == 0) {

            // Prompt the user to select any extras they would like
            String extra = (String) JOptionPane.showInputDialog(null, "What type of milk are you looking for?",
                    appName, JOptionPane.QUESTION_MESSAGE, null, menu.allExtras().toArray(), "");

            extras.add(extra);
            // Using the fact that "no" == 1 and "yes" == 0 to control the while loop
            if (extra.equalsIgnoreCase("Skip")) {
                extras.addAll(menu.allExtras());
                break;
            }
            decision = JOptionPane.showConfirmDialog(null, "Would you like to add another extra",
                    appName, JOptionPane.YES_NO_OPTION);
        }

        // Initiate price variables
        float min = -1;
        float max = -1;
        // TODO - NULLPOINTER ERRORS
        // While loop for lowest price range
        while (min < 0) {
            try {
                min = Float.parseFloat(JOptionPane.showInputDialog(null,"Enter your lowest price:",
                        appName, JOptionPane.QUESTION_MESSAGE));
            }
            catch (NumberFormatException e) {
                System.out.println(e);
                JOptionPane.showMessageDialog(null, "Please enter a positive price.");
            }
            catch (NullPointerException e) {
                System.out.println(e);
                JOptionPane.showMessageDialog(null, "Sorry we couldn't help you today");
                System.exit(0);
            }
        }

//        while loop for highest price range
        while (max < min) {
            try {
                max = Float.parseFloat(JOptionPane.showInputDialog(null, "Enter your highest price:",
                        appName, JOptionPane.QUESTION_MESSAGE));
            } catch (NumberFormatException e) {
                System.out.println(e);
                JOptionPane.showMessageDialog(null, "Please enter a positive price.");
            }
            catch (NullPointerException e) {
                System.out.println(e);
                JOptionPane.showMessageDialog(null, "Sorry we couldn't help you today");
                System.exit(0);
            }
        }
        // TODO - make sure i am allowed to do this.... cause this should be multiple constructpr  solution
        Coffee usersCoffee = new Coffee(0, "", 0, shots, sugar, Collections.singletonList(milk.toString()), extras,"");
        usersCoffee.setMax(max);
        usersCoffee.setMin(min);
        return usersCoffee;
    }

    public String matchedCoffee(Coffee coffee) {
        // TODO - This should be a string... unless its a stringbuilder... but probably a list, just not sure what
        // TODO - this part of the program looks like, most likely a list of coffees to be put in a joptiionpane

        return matchedCoffee(coffee);
    }

//    private boolean isIntValue(int number) {
//        Pattern pattern = Pattern.compile("^[0-100]$");
//        Matcher matcher = pattern.matcher(number);
//        return matcher.matches();
//
//    }



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

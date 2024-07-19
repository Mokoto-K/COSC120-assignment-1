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
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MenuSearcher {
    // TODO - Add milk to the end of all of the milk names... didnt know where to put this todo
    // TODO - Price doesnt have following 0 when displaying results
    // TODO - Handle if the file name already exists
    private static final String filePath = "./menu.txt";
    private static final String appName = "The Caffeinated Geek";
    private static final String appIcon = "./the_caffeinated_geek.png";
    private static Menu menu;
    /**
     * The main method of our program
     */
    public static void main(String[] args) {
        // Sets the icon for Joptionpans
        ImageIcon icon = new ImageIcon(appIcon);

        // Is an objetc of the menu class which contains our database of coffees
        menu = loadMenu();

        // TODO - is fix the statement below to accept this coffee instead of just calling the function
        Coffee userCoffee = userCoffee();

        // TODO - Handle the no match case
        // Is a map of coffees that matched the users critera
        Map<String, Coffee> matchedCoffees = menu.compareCoffee(userCoffee);

        // Is a string of the name of the coffee of the users choice
        String usersChoice = selectedCoffee(matchedCoffees);

        // Asks the user for their name and number for their order
        Geek customer = customerDetails();

        // Writes users details and coffee selection to a file
        submitOrder(customer, usersChoice, matchedCoffees, userCoffee);


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

                String description = splitOne[3].replace("]","");

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

    /**
     * Displays a list of coffees and their details from the database that match the users criteria
     * for what they want to order. It then asks the user which of the listed coffees they would like
     * and returns that choice
     * @param coffeeMatches a map of all coffees that match the users criteria from the db
     * @return the users choice of coffee from the given list.
     */
    // TODO - Explain everyline inside the function... I didn't comment enough as i went
    private static String selectedCoffee(Map<String, Coffee> coffeeMatches) {
        // TODO - DIsplay id number for drink in drinks display
        StringBuilder displayMessage = new StringBuilder();
        displayMessage.append("Matches found!! The following coffees meet your criteria:\n\n");
        for (Coffee coffee : coffeeMatches.values()) {
            // Decided to split the appended message into strings first before chaining it in the builder as it was a
            // 30 odd length chain... seemed ridiculous.
            String divider = "*****************************************************\n\n";
            String name = coffee.getName() + "\n";
            String description = coffee.getDescription() + "\n";
            // TODO - Milk needs to be handled like extras is below... multiple for some
            String milk = "Ingredients:\nMilk: " + coffee.getMilk() + "\n";
            String shots = "Number of shots: " + coffee.getShots() + "\n";
            String sugar = "Sugar: " + coffee.getSugar() + "\n";

            // TODO - Used this twice, in two different places, consider extracting to function
            StringBuilder extrasList = new StringBuilder();
            for (String extra : coffee.getExtras()) {
                // If it's the last element in the list, append it without a comma and space
                if (!extra.equals(coffee.getExtras().getLast())) {extrasList.append(extra).append(", ");}
                else {
                    extrasList.append(extra);
                }

                // otherwise append the extra with a comma and space for the next one.

            }
            String extras = "Extras: " + extrasList + "\n";
            String price = "Price: $" + coffee.getPrice() + "\n";

            // Appand all the strings in an orderly manner
            displayMessage.append(divider).append(name).append(description).append(milk)
                    .append(shots).append(sugar).append(extras).append(price);
        }
        // TODO - Add a no thankyou so that he user doesn't have to order any of the selected and it exits the program.
        // TODO - Also potentially a search again in stead of selection. "Please select your chioec" rather then selected your coffee
        return (String) JOptionPane.showInputDialog(null, displayMessage + "Which coffee would you like:", appName, JOptionPane.QUESTION_MESSAGE,
                null, coffeeMatches.keySet().toArray(),"");

    }




    // TODO - null case again probably
    /**
     * Asks the customer for their name and phone number and creates a Geek
     * object using this information
     * @return a Geek object containing our customers information
     */
    private static Geek customerDetails(){
        // Ask the user to enter their first and last name and check if it is correct.
        String name;
        do {
            name = JOptionPane.showInputDialog("Please enter your name (First Last)");
        }
        while (!isValidNames(name));

        // Ask the user to enter their phone number and check if it is correct.
        String number;
        do {
            number = JOptionPane.showInputDialog("Please enter your phone number (04xxxxxxxx)");
        }
        while (!isValidPhoneNumber(number));

        JOptionPane.showMessageDialog(null, "Thankyou, your order has been placed.");
        // Return the created geek object from the users input
        return new Geek(name, number);
        // TODO - return statement might not work, test it when i get to it.

    }
    // TODO this whole method is missing comments and some lines as i rushed to finish it today... which wont be today when i read this
    private static void submitOrder(Geek geek, String coffee, Map<String, Coffee> allCoffees, Coffee usersCoffee){
        // TODO - Unsure if this is the correct filename format wanted
        String fileName = "Order-Number-" + geek.phoneNumber();

        // Get the users details in correct format and as a string
        String usersDetails = "\tName: " + geek.name() + " (" + geek.phoneNumber() + ") \n";

        // Get the users selected coffees details in correct format and in string form
        Coffee selectedCoffee = allCoffees.get(coffee);
        String item = "\tItem: " + selectedCoffee.getName() + " (" + selectedCoffee.getId() + ")\n";
        String milk = "\tMilk: " + usersCoffee.getMilk() + "\n";

        // Everything for ectrasdasdefgads
        StringBuilder extrasList = new StringBuilder();
        for (String extra : usersCoffee.getExtras()) {
            // If it's the last element in the list, append it without a comma and space
            if (!extra.equals(usersCoffee.getExtras().getLast())) {extrasList.append(extra).append(", ");}
            else {extrasList.append(extra);}
            // otherwise append the extra with a comma and space for the next one.

        }

        String extras = "\tExtras: " + extrasList;

        // TODO replace with builder maybe
        String orderMessage = "Order details:\n" + usersDetails + item + milk + extras;
        // Set the path of the file to be created as the name of the name
        Path path = Path.of("./" + fileName + ".txt");

        try {
            Files.writeString(path, orderMessage);
        }
        catch(IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Compares a given string against a predetermined squence of charaters to determine if
     * customer input is correct. In this case the format of the users first and last name
     * @param names - customers first and last name
     * @return boolean True of False whether the input matched the required format
     */
    public static boolean isValidNames(String names) {
        // Create a pattern object containing the required format
        // TODO - Perhaps I need to bullet proof the name regex, it might not work for all types of names with spec chars
        Pattern pattern = Pattern.compile("^[a-zA-z]+\\s[a-zA-Z]+$");

        // Match the users input against the required format
        Matcher matcher = pattern.matcher(names);

        // Return the result
        return matcher.matches();
    }

    /**
     * Compares a given string against a predetermined squence of charaters to determine if
     * customer input is correct. In this case the format of a phone number
     * @param number - customer phone number asked to be input
     * @return boolean True of False whether or not the input matched the required format
     */
    public static boolean isValidPhoneNumber(String number) {
        // Create a pattern object containing the required format
        Pattern pattern = Pattern.compile("^04\\d{8}$");

        // Match the users input against the required format
        Matcher matcher = pattern.matcher(number);

        // Return the result
        return matcher.matches();
    }

}

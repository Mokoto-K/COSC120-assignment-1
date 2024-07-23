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
    // TODO add author header info
    private static final String filePath = "./menu.txt";
    private static final String appName = "The Caffeinated Geek";
    private static final ImageIcon icon = new ImageIcon("./the_caffeinated_geek.png");
    private static Menu menu;

    /**
     * The main method of our program
     */
    public static void main(String[] args) {
        // Is an object of the menu class which contains our database of coffees
        menu = loadMenu();

        // Create a coffee representing the users choices
        Coffee userCoffee = userCoffee();

        // Is a map of coffees that matched the users criteria
        Map<String, Coffee> matchedCoffees = menu.compareCoffee(userCoffee);
        if (matchedCoffees.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Sorry, we don't have any coffee's that match " +
                    "your description",appName, JOptionPane.INFORMATION_MESSAGE, icon);
            System.exit(0);
        }

        // Is a string of the name of the coffee of the users choice
        String usersChoice = selectedCoffee(matchedCoffees);

        // Asks the user for their name and number for their order
        Geek customer = customerDetails();

        // Writes users details and coffee selection to a file
        submitOrder(customer, usersChoice, userCoffee);

    }

    /**
     * Opens a file which is our database of drinks, splits each line up into specific details of
     * each coffee, adds new coffees to a list as coffee objects using to information gleamed from
     * the file
     * @return Menu object from the Menu class, used to call and control the classes methods and database
     */
    private static Menu loadMenu() {
        Path path = Path.of(filePath);
        Menu menu = new Menu();

        // Creating a list of strings to temporarily hold our database information so we can iterate through each one.
        List<String> allCoffee;
        try {
            // Read all lines in from out database and place them in a list of strings
            allCoffee = Files.readAllLines(path);

            // Remove the headers from our list of strings
            allCoffee.remove(allCoffee.getFirst());

            for (String line : allCoffee) {
                // We have to split our db up in two different ways, first we need to split on the open square bracket
                // This will separate the milk, extras and description fields from the rest of the data, then we
                // can just split at comma and do a little bit of data cleaning to remove some unwanted characters.
                String[] MilkExtrasDescription = line.split("\\[");

                // Assigning the first half of the above split to a new array of strings which contains most of our
                // coffee information, and splitting on commas
                String[] coffeeDetails = MilkExtrasDescription[0].split(",");

                // Assigning all of our split strings to their appropriate variables
                int id = Integer.parseInt(coffeeDetails[0]);
                String name = coffeeDetails[1];
                float price = Float.parseFloat(coffeeDetails[2]);
                int shots = Integer.parseInt(coffeeDetails[3]);
                String sugar = coffeeDetails[4];

                // Creating a temporary list of strings containing our milk options. Removing unwanted characters and
                // splitting each element at the commas.
                List<String> tempMilk = List.of(MilkExtrasDescription[1].replace("],", "").split(","));

                // Creating a fresh list of strings to hold our cleaned milk data
                List<String> milk = new ArrayList<>();

                // Iterating through all elements in the temporary milk list and adding them to the cleaned list
                for (String milkElement : tempMilk) {
                    // If the string is empty, we are replacing it with None as the milk option for that coffee
                    if (milkElement.isEmpty()) {
                        milkElement = "None";
                    }
                    // Finally trimming the string of any white space before adding it to the cleaned list
                    milk.add(milkElement.trim());
                }
                // Creating a temporary list of strings containing our extras options. Removing unwanted characters and
                // splitting each element at the commas.
                List<String> tempExtras = List.of(MilkExtrasDescription[2].replace("],", "").split(","));

                // Creating a fresh list of strings to hold our cleaned extras data
                List<String> extras = new ArrayList<>();

                // Iterating through all elements in the temporary extras list and adding them to the cleaned list
                for (String extraElement : tempExtras) {
                    // If the string is empty, we are replacing it with No Extras as the extras option for that coffee
                    if (extraElement.isEmpty()) {
                        extraElement = "No Extras";
                    }
                    // Trimming the extra of any white space before adding it to the cleaned list
                    extras.add(extraElement.trim());
                }

                // Cleaning the description for our coffee
                String description = MilkExtrasDescription[3].replace("]","");

                // Sending all our variables to our coffee class to create a coffee object
                Coffee coffee = new Coffee(id, name, price, shots, sugar, milk, extras, description);

                // adding the coffee object to our database to query later
                menu.addCoffee(coffee);
            }
        }

        catch (IOException e) {
            System.out.println("A File error occurred" + e);
            JOptionPane.showMessageDialog(null, "There was an error reading the coffee database"
            , appName, JOptionPane.INFORMATION_MESSAGE, icon);
        }

        return menu;
    }

    /**
     * Asks the customer a series of questions about the drink they would like to order and
     * creates a coffee object containing all of their requirements to be compared against
     * our menu later on.
     * @return Coffee - a coffee object of our customers desired "order"
     */
    private static Coffee userCoffee() {
        // Prompt the user on which milk they would like
        Milk milk = (Milk) JOptionPane.showInputDialog(null, "What type of milk are you looking for?",
                appName, JOptionPane.QUESTION_MESSAGE, icon, Milk.values(), Milk.FULLCREAM);
        // Handles the case of the user exiting at milk choice
        if (milk == null) {
            System.exit(0);
        }

//      Prompt user on how many shots they would like
        int shots = -1;
        do {
            try {
                // Setting the message dialog as a string for more control
                String shotsStr = JOptionPane.showInputDialog(null, "Enter the number shots you would like?",
                        appName, JOptionPane.QUESTION_MESSAGE);

                // If the string is null, ie the user exited the program, handle the null pointer error
                if (shotsStr == null) {System.exit(0);}

                // Otherwise convert the string to an int
                shots = Integer.parseInt(shotsStr);
            }
            // If the user doesn't enter a positive int
            catch (NumberFormatException e) {
                // Let the developer know the problem
                System.out.println("User enter the wrong number format" + e);

                // Let the user know the problem
                JOptionPane.showMessageDialog(null, "Please enter a positive integer", appName,
                        JOptionPane.QUESTION_MESSAGE, icon);
            }
        }
        while (shots < 0);

        // Prompt the user with a yes or no question for their sugar preference
        int sugarChoice = JOptionPane.showConfirmDialog(null, "Would you like sugar?", appName,
                JOptionPane.YES_NO_OPTION);

        // Initiate variable for sugar
        String sugar = "";

        // Control structure for sugar choice, if the user closes the dialog box, this handles the null pointer error
        if (sugarChoice != 0 && sugarChoice != 1) {
            System.exit(0);
        }

        // These two cases handle if the user selects yes or no.
        else if (sugarChoice == JOptionPane.YES_OPTION) {
            sugar = "Yes";
        } else {
            sugar = "No";
        }

        // Initiating a set to temporarily hold the extras the user picks, this is to prevent duplicates of the same
        // extra appearing in there coffee object.
        Set<String> tempExtras = new HashSet<>();
        // Initiating an int to be used for keeping track of extras
        int decision = 0;
        while (decision == 0) {

            // Prompt the user to select any extras they would like
            String extra = (String) JOptionPane.showInputDialog(null, "What type of milk are you looking for?",
                    appName, JOptionPane.QUESTION_MESSAGE, icon, menu.allExtras().toArray(), "");

            // Handle the null case
            if (extra == null) {System.exit(0);}

            // If the user selects Skip, we want to add "No extras" to their order and not the word skip, then we want
            // to break from the loop to move to the next item
            if (extra.equalsIgnoreCase("Skip") && tempExtras.isEmpty()) {
                tempExtras.add("No Extras");
                break;
            }
            else if (extra.equalsIgnoreCase("Skip")) {break; }

            // Add the extra after a check for "skip"
            tempExtras.add(extra);



            // Prompt user if they would like to add more extras, using the fact that with a Joptionpane,
            // "no" == 1 and "yes" == 0 to control the while loop
            decision = JOptionPane.showConfirmDialog(null, "Would you like to add another extra",
                    appName, JOptionPane.YES_NO_OPTION);
        }

         // Converting the set of extras to a list as lists provide a few extra (excuse the pun) features that I need
         //to use in this project, such as .getlast()
        List<String> extras = new ArrayList<>(tempExtras);

        // Initiate price variables
        float min = -1;
        float max = -1;
        // While loop for lowest price range
        while (min < 0) {
            try {
                min = Float.parseFloat(JOptionPane.showInputDialog(null, "Enter your lowest price:",
                        appName, JOptionPane.QUESTION_MESSAGE));
            }
            // If the user enters and incorrect price
            catch (NumberFormatException e) {
                System.out.println("User entered the wrong number format" + e);
                JOptionPane.showMessageDialog(null, "Please enter a positive price.");
            }
            // If the user exits out of the program
            catch (NullPointerException e) {
                System.out.println("User exited the program" + e);
                JOptionPane.showMessageDialog(null, "Sorry we couldn't help you today");
                System.exit(0);
            }
        }

        // while loop for highest price range
        while (max < min) {
            try {
                max = Float.parseFloat(JOptionPane.showInputDialog(null, "Enter your highest price:",
                        appName, JOptionPane.QUESTION_MESSAGE));
            }
            // If the user enters a value less then the min or not an int
            catch (NumberFormatException e) {
                System.out.println("User entered the wrong number format" + e);
                JOptionPane.showMessageDialog(null, "Please enter a positive price greater then the minimum.");
            }
            // If the exits the program
            catch (NullPointerException e) {
                System.out.println("User exited the program" + e);
                JOptionPane.showMessageDialog(null, "Sorry we couldn't help you today");
                System.exit(0);
            }
        }

        // Sends all of the gathered information to the coffee class and creates a coffee object of the users choices.
        Coffee usersCoffee = new Coffee(0, "user's Coffee", 0, shots, sugar, Collections.singletonList(milk.toString()), extras, "A user's coffee");

        // Sets the min and max price range for the users coffee for comparison later.
        usersCoffee.setMax(max);
        usersCoffee.setMin(min);
        return usersCoffee;
    }

    /**
     * Displays a list of coffees and their details from the database that match the users criteria
     * for what they want to order. It then asks the user which of the listed coffees they would like
     * and returns that choice
     * @param coffeeMatches a map of all coffees that match the users criteria from the db
     * @return String of the name of the users choice of coffee from the given list appended with the coffees id
     */
    private static String selectedCoffee(Map<String, Coffee> coffeeMatches) {
        // Create a stringbuilder to help concatenate all of the information to be displayed to the user
        StringBuilder displayMessage = new StringBuilder();

        // Top line of the message to the user
        displayMessage.append("Matches found!! The following coffees meet your criteria:\n\n");

        // For each coffee that matches the users coffee, print out the information for the coffee.
        for (Coffee coffee : coffeeMatches.values()) {
            // Decided to split the appended message into strings first before chaining it in the builder as it was a
            // chain consisting of more than 30 appends... seemed ridiculous and a little redundant.
            String divider = "*****************************************************\n\n";
            String name = coffee.getName() + " (" + coffee.getId() + ")\n";
            String description = coffee.getDescription() + "\n";

            // Creating a string builder for our milk options as some coffees have more than one, so displaying them
            // correctly is not as straight forward as just getting the coffees milk option
            StringBuilder milkList = new StringBuilder();
            // For every milk option in a given coffee, add that milk to the string builder
            for (String milk : coffee.getMilk()) {
                // If it's not the last element in the list then append it with trailing characters for display
                if (!milk.equals(coffee.getMilk().getLast())) {milkList.append(milk).append(", ");}
                else {
                    // otherwise only append the milk
                    milkList.append(milk);
                }
            }
            // String for milk option with all milk appended in correct formatting
            String milk = "Ingredients:\nMilk: " + milkList + "\n";

            // Strings for shots and sugar formatted correctly
            String shots = "Number of shots: " + coffee.getShots() + "\n";
            String sugar = "Sugar: " + coffee.getSugar() + "\n";

            // This is the same as the above milk option. Since extras is a list due to coffees having multiple extras
            // we have to treat it a little different using a stringbuilder and for each loop to control the display
            StringBuilder extrasList = new StringBuilder();
            for (String extra : coffee.getExtras()) {
                // If it's not the last element in the list then append it with trailing characters for display
                if (!extra.equals(coffee.getExtras().getLast())) {extrasList.append(extra).append(", ");}
                else {
                    // otherwise only append the extra
                    extrasList.append(extra);
                }
            }
            // Finally adding the list of extras as a correctly formatted string
            String extras = "Extras: " + extrasList + "\n";
            String price = "Price: $" + String.format("%,.2f",coffee.getPrice()) + "\n";

            // Append all the strings in an orderly manner
            displayMessage.append(divider).append(name).append(description).append(milk)
                    .append(shots).append(sugar).append(extras).append(price);
        }
        // Present the user with all of the coffees that match their desired coffee and ask them to select which one they would like
        String choice = (String) JOptionPane.showInputDialog(null, displayMessage + "Which coffee would you like:", appName, JOptionPane.QUESTION_MESSAGE,
                icon, coffeeMatches.keySet().toArray(),"");
        if (choice == null) {System.exit(0);}
        // Take the users choice and query the map to add the id to the name for later use when writing to file
        // This saves us from having to pass a map into a future function
        return coffeeMatches.get(choice).getName() + " ("+ coffeeMatches.get(choice).getId() + ")";
    }

    /**
     * Asks the customer for their name and phone number and creates a Geek
     * object using this information
     * @return a Geek object containing our customers information
     */
    private static Geek customerDetails(){
        // Ask the user to enter their first and last name and check if it is correct.
        String name;
        do {
            name = JOptionPane.showInputDialog("Please enter your full name (John Doe)");
            if (name == null) {System.exit(0);}
        }
        // send the user input to the isValidName function to check if it is correctly formatted
        while (!isValidNames(name));

        // Ask the user to enter their phone number and check if it is correct.
        String number;
        do {
            number = JOptionPane.showInputDialog("Please enter your phone number (04xxxxxxxx)");
            if (number == null) {System.exit(0);}
        }
        // Send the users input to the isValidPhoneNumber function to check if it is correctly formatted
        while (!isValidPhoneNumber(number));

        // Return the created geek object from the users input
        return new Geek(name, number);
    }

    /**
     * Takes the user's information, the name of the coffee they wish to order, and a coffee object containing
     * the user's question choices and systematically adds all relevant information to a String that is then
     * used to write a txt file of the user's order.
     * @param geek - A geek object of with the users name and phone number (phone number used for order number)
     * @param coffeeName - A String containing the name of the coffee the user has chosen to order
     * @param uCoffee - The coffee object that contains all of the users choices
     */
    private static void submitOrder(Geek geek, String coffeeName, Coffee uCoffee){
        // Set the name of our file using the users phone number
        String fileName = "Order-Number-" + geek.phoneNumber();

        // Get the users details in correct format and as a string
        String usersDetails = "\tName: " + geek.name() + " (" + geek.phoneNumber() + ") \n";

        // Get the users selected coffees details in correct format and in string form
        String item = "\tItem: " + coffeeName + "\n";
        String milk = "\tMilk: " + uCoffee.getMilk().getFirst() + "\n";

        // Create a string builder to hold all of the customers extras choices
        StringBuilder extrasList = new StringBuilder();
        // Iterate through every choice
        for (String extra : uCoffee.getExtras()) {
            // If the extra isnt the last one in the list, then append a comma and a space
            // this is only used for visual appearance to the customer
            if (!extra.equals(uCoffee.getExtras().getLast())) {extrasList.append(extra).append(", ");}
            // Otherwise just append the last extra
            else {extrasList.append(extra);}
        }

        // format the extras string using the stringBuilder from above
        String extras = "\tExtras: " + extrasList;

        // Create the string to be saved in the order file
        String orderMessage = "Order details:\n" + usersDetails + item + milk + extras;
        // Set the path of the file to be created as the filename we assigned above
        Path path = Path.of("./" + fileName + ".txt");

        try {

            // Create the file and write the order
            Files.writeString(path, orderMessage);
            JOptionPane.showMessageDialog(null, "Thankyou, your order has been placed.");
        }
        catch(IOException e) {
            System.out.println("Error writing the file" + e);
            JOptionPane.showMessageDialog(null, "Sorry, there was an error when creating your order.");
        }
    }

    /**
     * Compares a given string against a predetermined squence of charaters to determine if
     * customer input is correct. In this case the format of the users first and last name
     * @param names - User input of their first and last name
     * @return boolean True of False whether the input matched the required format
     */
    public static boolean isValidNames(String names) {
        // Create a pattern object containing the required format
        // Lets a user enter however many names are in their full name as well as fullstops,
        // hyphens, and apostrophe, only letters allowed, not case-sensitive. this covers most cases.
        Pattern pattern = Pattern.compile("^[a-zA-Z '.-]*$");

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
        // format is 10 digits starting with 04
        Pattern pattern = Pattern.compile("^04\\d{8}$");

        // Match the users input against the required format
        Matcher matcher = pattern.matcher(number);

        // Return the result
        return matcher.matches();
    }

}

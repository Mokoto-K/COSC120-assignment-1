/**
 * @author Enter name
 * Email: Enter email
 * COSC120 - Assignment 1
 * Date: 15/07/24
 */

/**
 * An enum class for our milk selection.
 */
public enum Milk {

    // All milk elements our program needs
    FULLCREAM, SOY, SKIM, ALMOND, OAT, NONE;

    /**
     * Converts our different Milks from their element form to a readable
     * string to be displayed to our user
     * @return a string version of the selected element
     */
    public String toString() {

        // returns the case of whichever element was selected in
        // our prescribed string form
        return switch (this) {
            case FULLCREAM -> "Full-cream";
            case SOY -> "Soy";
            case SKIM -> "Skim";
            case ALMOND -> "Almond";
            case OAT -> "Oat";
            case NONE -> "None";
        };
    }

}

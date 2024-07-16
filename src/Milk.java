public enum Milk {

    FULLCREAM, SOY, SKIM, ALMOND, OAT, NONE;

    public String toString() {
        return switch (this) {
            case FULLCREAM -> "Full-Cream";
            case SOY -> "Soy";
            case SKIM -> "Skim";
            case ALMOND -> "Almond";
            case OAT -> "Oat";
            case NONE -> "None";
        };
    }

}

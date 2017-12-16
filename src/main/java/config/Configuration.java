package config;

/**
 * Project configuration.
 */
public class Configuration {

    private static int NUMBER_OF_TRAVELERS = 3;

    public static int FITNESS_EVALUATED = 0;

    public static int getNumberOfTravelers() {
        return NUMBER_OF_TRAVELERS;
    }

    public static void setNumberOfTravelers(int numberOfTravelers) {
        NUMBER_OF_TRAVELERS = numberOfTravelers;
    }
}

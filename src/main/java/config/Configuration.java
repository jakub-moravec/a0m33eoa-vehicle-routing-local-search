package config;

/**
 * Project configuration.
 */
public class Configuration {


    // vars
    public static int fitnessEvaluated = 0;

    public static int currentTry = 0;

    // parameters
    public static String outputFile = "C:\\Users\\jmoravec\\Desktop\\results\\ls_200.txt";

    public static int maxFitnessEvaluated = 2000000;

    public static int epochSampleModulo = 20;

    public static int numberOfTries = 20;

    private static int NUMBER_OF_TRAVELERS = 3;

    public static int getNumberOfTravelers() {
        return NUMBER_OF_TRAVELERS;
    }

    public static void setNumberOfTravelers(int numberOfTravelers) {
        NUMBER_OF_TRAVELERS = numberOfTravelers;
    }
}

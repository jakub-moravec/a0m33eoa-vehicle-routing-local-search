package config;

import lombok.Getter;
import lombok.Setter;

/**
 * Project configuration.
 */
public class Configuration {

    @Getter
    @Setter
    private static int numberOfTravelers = 3;

    @Getter
    @Setter
    private static int maxEpoch = 100;

    @Getter
    @Setter
    private static int populationSize = 50;

    @Getter
    @Setter
    private static double probabilityOfMutation = 0.01;

    @Getter
    @Setter
    private static double probabilityOfCrossover = 0.75;
}

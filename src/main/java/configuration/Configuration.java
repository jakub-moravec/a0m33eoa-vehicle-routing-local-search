package configuration;

import lombok.Getter;
import lombok.Setter;

/**
 * Algorithm configuration.
 *
 * @author moravja8
 */
public class Configuration {

    @Getter
    @Setter
    private static int numberOfTravelers = 3;

    @Getter
    @Setter
    private static int maxEpoch = 1000;

    @Getter
    @Setter
    private static int populationSize = 50;

    @Getter
    @Setter
    private static double probabilityOfMutation = 0.01;

    @Getter
    @Setter
    private static double probabilityOfCrossover = 0.75;

    @Getter
    @Setter
    private static int tournamentCandidates = 5;
}

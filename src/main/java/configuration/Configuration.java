package configuration;

import lombok.Getter;
import lombok.Setter;
import model.Solution;
import selection.TournamentSelectionStrategy;
import templates.operations.SelectorStrategy;

/**
 * Algorithm configuration.
 *
 * @author moravja8
 */
public class Configuration {

    // vars
    @Getter
    @Setter
    private static int fitnessEvaluated = 0;

    @Getter
    @Setter
    private static int currentTry = 0;

    // parameters
    @Getter
    @Setter
    private static String outputFile = "C:\\Users\\jmoravec\\Desktop\\results\\ea_200.txt";

    @Getter
    @Setter
    private static int maxFitnessEvaluated = 2000000;

    @Getter
    @Setter
    private static int epochSampleModulo = 20;

    @Getter
    @Setter
    private static int numberOfTries = 20;

    @Getter
    @Setter
    private static int numberOfTravelers = 3;

    @Getter
    @Setter
    private static int maxEpoch = 5000;

    @Getter
    @Setter
    private static int populationSize = 500;

    @Getter
    @Setter
    private static double probabilityOfMutation = 0.005;

    @Getter
    @Setter
    private static double probabilityOfCrossover = 0.6;

    @Getter
    @Setter
    private static int tournamentCandidates = 10;

    @Getter
    @Setter
    private static int individualsToKeep = 100;

    //strategies
    @Getter
    @Setter
    private static SelectorStrategy<Solution, Solution, Double> selectorStrategy = new TournamentSelectionStrategy();
}

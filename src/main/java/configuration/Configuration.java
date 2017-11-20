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

    // parameters
    @Getter
    @Setter
    private static int numberOfTravelers = 3;

    @Getter
    @Setter
    private static int maxEpoch = 2000;

    @Getter
    @Setter
    private static int populationSize = 200;

    @Getter
    @Setter
    private static double probabilityOfMutation = 0.01;

    @Getter
    @Setter
    private static double probabilityOfCrossover = 0.5;

    @Getter
    @Setter
    private static int tournamentCandidates = 20;

    @Getter
    @Setter
    private static int individualsToKeep = 20;

    //strategies
    @Getter
    @Setter
    private static SelectorStrategy<Solution, Solution, Double> selectorStrategy = new TournamentSelectionStrategy();
}

package configuration;

import localSearch.TwoOptLocalSearchStrategy;
import lombok.Getter;
import lombok.Setter;
import model.Solution;
import mutation.SubpathExchangeMutationStrategy;
import selection.TournamentSelectionStrategy;
import templates.operations.LocalSearchStrategy;
import templates.operations.MutationStrategy;
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
    private static int maxEpoch = 3000;

    @Getter
    @Setter
    private static int populationSize = 500;

    @Getter
    @Setter
    private static double probabilityOfMutation = 0.01;

    @Getter
    @Setter
    private static double probabilityOfLocalSearch = 0.1;

    @Getter
    @Setter
    private static double probabilityOfLocalSearchSwap = 0.001;

    @Getter
    @Setter
    private static double probabilityOfCrossover = 0.5;

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

    @Getter
    @Setter
    private static LocalSearchStrategy<Solution, Solution> localSearchStrategy = new TwoOptLocalSearchStrategy();

    @Getter
    @Setter
    private static MutationStrategy<Solution, Solution> mutationStrategy = new SubpathExchangeMutationStrategy();
}

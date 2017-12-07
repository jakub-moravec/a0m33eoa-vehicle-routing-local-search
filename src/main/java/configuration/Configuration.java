package configuration;

import localSearch.LocalSearchStrategyPicker;
import localSearch.TwoOptAllTravelersLocalSearchStrategy;
import localSearch.TwoOptOneTravelerLocalSearchStrategy;
import lombok.Getter;
import lombok.Setter;
import model.Solution;
import mutation.SubpathExchangeMutationStrategy;
import selection.TournamentSelectionStrategy;
import templates.operations.LocalSearchStrategy;
import templates.operations.MutationStrategy;
import templates.operations.SelectorStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private static int maxEpoch = 5000;

    @Getter
    @Setter
    private static int populationSize = 500;

    @Getter
    @Setter
    private static double probabilityOfMutation = 0.005;

    @Getter
    @Setter
    private static double probabilityOfLocalSearch = 0.25;

    @Getter
    @Setter
    private static double probabilityOfLocalSearchSwap = 0.005;

    @Getter
    @Setter
    private static double probabilityOfCrossover = 0.5;

    @Getter
    @Setter
    private static int tournamentCandidates = 10;

    @Getter
    @Setter
    private static int individualsToKeep = 100;

    @Getter
    @Setter
    private static double currentBestFitness = Double.MAX_VALUE;

    @Getter
    @Setter
    private static int bestFitnessUnchangedEpochs = 0;

    //strategies
    @Getter
    @Setter
    private static SelectorStrategy<Solution, Solution, Double> selectorStrategy = new TournamentSelectionStrategy();

    @Getter
    @Setter
    private static LocalSearchStrategy<Solution, Solution> localSearchStrategy = new LocalSearchStrategyPicker();

    @Getter
    @Setter
    private static List<LocalSearchStrategy<Solution, Solution>> localSearchStrategies =
            new ArrayList<>(Arrays.asList(new TwoOptOneTravelerLocalSearchStrategy(), new TwoOptAllTravelersLocalSearchStrategy()));

    @Getter
    @Setter
    private static MutationStrategy<Solution, Solution> mutationStrategy = new SubpathExchangeMutationStrategy();
}

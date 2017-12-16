package localSearch;

import configuration.Configuration;
import model.Solution;
import templates.Individual;
import templates.operations.LocalSearchStrategy;

import java.util.Optional;
import java.util.Random;

/**
 * Used to pick one of the available local search strategies.
 *
 * @author moravja8
 */
public class LocalSearchStrategyPicker implements LocalSearchStrategy<Solution, Solution> {

    private static final Random RANDOM = new Random();

    @Override
    public Optional<Individual<Solution, Solution>> localSearch(Individual<Solution, Solution> individual) {
        Optional<Individual<Solution, Solution>> mutated = Configuration.getLocalSearchStrategies().get(0).localSearch(individual);
        if (RANDOM.nextInt(5000) < Configuration.getBestFitnessUnchangedEpochs()) {
            mutated = Configuration.getLocalSearchStrategies().get(1).localSearch(mutated.get());
        }
        return mutated;
    }
}

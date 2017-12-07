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
        int strategyId =  RANDOM.nextInt(Configuration.getLocalSearchStrategies().size());
        return Configuration.getLocalSearchStrategies().get(strategyId).localSearch(individual);
    }
}

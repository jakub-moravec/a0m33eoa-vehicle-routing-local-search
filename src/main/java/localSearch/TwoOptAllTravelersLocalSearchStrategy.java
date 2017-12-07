package localSearch;

import configuration.Configuration;
import model.Solution;
import templates.Individual;
import templates.operations.LocalSearchStrategy;

import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Two-opt local search strategy.
 * @author moravja8
 */
public class TwoOptAllTravelersLocalSearchStrategy implements LocalSearchStrategy<Solution, Solution> {

    private static final Random RANDOM = new Random();

    @Override
    public Optional<Individual<Solution, Solution>> localSearch(Individual<Solution, Solution> individual) {
        int startInclusive = 0;
        int endExclusive = -1;

        List<Integer> path = individual.getGenes().getCitiesOrder();
        path.add(0);

        if (RANDOM.nextDouble() < Configuration.getProbabilityOfLocalSearch()) {
            path = TwoOptUtils.performTwoOptOnPath(path);
        }

        path.remove(path.size() -1);

        return Optional.of(new Individual<>(new Solution(path)));
    }
}

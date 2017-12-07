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
public class TwoOptOneTravelerLocalSearchStrategy implements LocalSearchStrategy<Solution, Solution> {

    private static final Random RANDOM = new Random();

    @Override
    public Optional<Individual<Solution, Solution>> localSearch(Individual<Solution, Solution> individual) {
        int startInclusive = 0;
        int endExclusive = -1;

        List<Integer> path = individual.getGenes().getCitiesOrder();
        path.add(0);

        for (int i = 0; i < path.size(); i++) {
            if(path.get(i) == 0 || i == path.size() -1) {
                startInclusive = endExclusive + 1;
                endExclusive = i;

                if (RANDOM.nextDouble() < Configuration.getProbabilityOfLocalSearch()) {
                    List<Integer> optimizedPath = TwoOptUtils.performTwoOptOnPath(path.subList(startInclusive, endExclusive));

                    int k = 0;
                    for (int j = startInclusive; j < endExclusive; j++) {
                        path.set(j, optimizedPath.get(k));
                        k++;
                    }
                }
            }
        }

        path.remove(path.size() -1);

        return Optional.of(new Individual<>(new Solution(path)));
    }


}

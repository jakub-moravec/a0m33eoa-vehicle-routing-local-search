package mutation;

import configuration.Configuration;
import model.Solution;
import templates.Individual;
import templates.operations.MutationStrategy;

import java.util.Optional;
import java.util.Random;

/**
 * Exchanges two random elements.
 *
 * @author moravja8
 */
public class OneElementExchangeMutationStrategy implements MutationStrategy<Solution, Solution> {

    private static final Random RANDOM = new Random();

    @Override
    public Optional<Individual<Solution, Solution>> mutation(Individual<Solution, Solution> individual) {
        Solution mutatedSolution = individual.getGenes().clone();
        for (int i = 0; i < mutatedSolution.getCitiesOrder().size(); i++) {
            if (RANDOM.nextDouble() < Configuration.getProbabilityOfMutation()) {
                int j = RANDOM.nextInt(mutatedSolution.getCitiesOrder().size());
                int city = mutatedSolution.getCitiesOrder().get(i);
                mutatedSolution.getCitiesOrder().set(i, mutatedSolution.getCitiesOrder().get(j));
                mutatedSolution.getCitiesOrder().set(j, city);
            }
        }
        return Optional.of(new Individual<>(mutatedSolution));
    }
}

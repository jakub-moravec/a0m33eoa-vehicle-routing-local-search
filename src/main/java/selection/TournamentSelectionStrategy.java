package selection;

import configuration.Configuration;
import model.Solution;
import templates.IndividualWithAssignedFitness;
import templates.operations.SelectorStrategy;

import java.util.List;
import java.util.Random;

/**
 * Tournament selection strategy.
 *
 * @author moravja8
 */
public class TournamentSelectionStrategy implements SelectorStrategy<Solution, Solution, Double> {
    private static final Random RANDOM = new Random();

    @Override
    public IndividualWithAssignedFitness<Solution, Solution, Double> select(List<IndividualWithAssignedFitness<Solution, Solution, Double>> population) {
        //First member of tournament selection.
        int winnerIndex = RANDOM.nextInt(population.size());

        // Try and check another n randomly chosen individuals.
        for (int i = 0; i < Configuration.getTournamentCandidates(); i++) {
            int candidate = RANDOM.nextInt(population.size());
            if (population.get(candidate).getFitness() < population.get(winnerIndex).getFitness()) {
                winnerIndex = candidate;
            }
        }

        return population.get(winnerIndex);
    }
}

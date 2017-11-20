package selection;

import model.Solution;
import templates.IndividualWithAssignedFitness;
import templates.operations.SelectorStrategy;

import java.util.List;
import java.util.Random;
import java.util.stream.DoubleStream;

/**
 * Roulette selection strategy for fitness minimalization.
 *
 * @author moravja8
 */
public class RouletteSelectionStrategy implements SelectorStrategy<Solution, Solution, Double> {
    private static final Random RANDOM = new Random();

    @Override
    public IndividualWithAssignedFitness<Solution, Solution, Double> select(List<IndividualWithAssignedFitness<Solution, Solution, Double>> population) {
        Double maxFitness = population.stream().map(IndividualWithAssignedFitness::getFitness).max(Double::compareTo).get();
        DoubleStream invertedFitness = population.stream().map((i)-> maxFitness - i.getFitness()).mapToDouble(Double::byteValue);
        double invertedFitnessSum = invertedFitness.sum();

        double bullet = RANDOM.nextDouble() * invertedFitnessSum ;

        double actualFitness = 0;

        for (IndividualWithAssignedFitness<Solution, Solution, Double> individual : population) {

            actualFitness += (maxFitness - individual.getFitness());
            if (actualFitness > bullet) {
                return  individual;
            }
        }

        return null;
    }
}

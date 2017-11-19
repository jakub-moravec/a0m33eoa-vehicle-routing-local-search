package algorithm;

import config.Configuration;
import evaluation.EuclideanFitnessEvaluator;
import model.ModelHolder;
import model.Solution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Generates initial solution.
 *
 * @author moravja8
 */
public class RandomSolutionGenerator{

    /**
     * Generates random (initial) solution
     * @return random solution
     */
    public static Solution generateSolution() {
        List<Integer> cities = new ArrayList<>();

        // add cities - except the pivot city
        IntStream.range(1, ModelHolder.getModel().size()).forEach(cities::add);

        // make it random
        Collections.shuffle(cities);

        cities.add(0, 0); // add pivot to start
        cities.add(cities.size(), 0); // add pivot to end

        // get one travelers current cost
        double expectedCostPerTraveler = EuclideanFitnessEvaluator.calculateFitness(new Solution(cities)) / (double) Configuration.getNumberOfTravelers();

        // add breakpoint cities
        double currentPath = 0;
        int travelers = 0;
        for (int i = 0; i < cities.size() - 1; i++) {
            if (currentPath >= expectedCostPerTraveler && travelers < Configuration.getNumberOfTravelers() - 1) {
                cities.add(i, 0);
                currentPath = 0;
                travelers++;
            }

            int cityX = cities.get(i);
            int cityY = cities.get(i+1);

            currentPath += EuclideanFitnessEvaluator.calculateDistance(ModelHolder.getModel().get(cityX), ModelHolder.getModel().get(cityY));
        }

        return new Solution(cities);
    }
}

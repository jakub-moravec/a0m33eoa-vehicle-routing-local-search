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
public class RandomSolutionGenerator implements SolutionGenerator{

    /**
     * TODO
     * @return
     */
    @Override
    public Solution generateSolution() {

        Solution solution = new Solution();


        List<Integer> cities = new ArrayList<>();

        // add cities - except the pivot city
        IntStream.range(1, ModelHolder.getModel().size()).forEach(cities::add);

        // make it random
        Collections.shuffle(cities);

        cities.add(0, 0); // add pivot to start
        cities.add(cities.size(), 0); // add pivot to end

        // get one travelers current cost
        EuclideanFitnessEvaluator.calculateFitness(new Solution(cities));

        //fixme
        // add breakpoint cities
        for (int i = 0; i < Configuration.getNumberOfTravelers(); i++) {
            cities.add(-1);
        }



//        solution.setCitiesOrder();

        return solution;
    }
}

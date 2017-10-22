package algorithm;

import model.Solution;

import java.util.Random;

/**
 * Finds best neighbor solution.
 */
public class BestNeighborAlgorithm {

    private static final int NEIGHBOR_SOLUTION_COUNT = 5;
    private static final int MAX_ORDER_CHANGES = 1;
    private static final double BREAKS_CHANGE_RATIO = 0.1;

    private static final Random RANDOM = new Random();

    /**
     * Finds a best neighbor solution.
     * @param solution old solution
     * @return best neighbor solution
     */
    public static Solution getNextGenerationSolution(Solution solution) {
        int lowestEvaluationIndex = -1;
        double lowestEvaluation = Double.MAX_VALUE;
        Solution[] neighbourSolutions = getNeighborSolutions(solution, NEIGHBOR_SOLUTION_COUNT, MAX_ORDER_CHANGES, BREAKS_CHANGE_RATIO);

        for (int i = 0; i < neighbourSolutions.length; i++) {
            neighbourSolutions[i].setEvaluation(Evaluator.evaluate(neighbourSolutions[i]));
            if (neighbourSolutions[i].getEvaluation() < lowestEvaluation) {
                lowestEvaluation = neighbourSolutions[i].getEvaluation();
                lowestEvaluationIndex = i;
            }
        }

        return neighbourSolutions[lowestEvaluationIndex];
    }

    /**
     * Finds neighbor solutions using 2-opt-like algorithm.
     * @param solution old solution
     * @param solutionsCount how many solutions should be found
     * @param maxOrderChanges maximum number of changes in cities order
     * @param breaksChangeRatio change ration in breakpoints
     * @return neighbor solutions
     */
    private static Solution[] getNeighborSolutions(Solution solution, int solutionsCount, int maxOrderChanges, double breaksChangeRatio) {
        Solution[] neighborSolutions = new Solution[solutionsCount];

        for (int i = 0; i < solutionsCount; i++) {
            Solution newSolution = new Solution(solution.getCitiesOrder(), solution.getBreakpoints());

            // change cities order
            for (int j = 0; j < maxOrderChanges; j++) {
                int[] cities = newSolution.getCitiesOrder();
                int indexA = RANDOM.nextInt(cities.length);
                int indexB = RANDOM.nextInt(cities.length);
                int cityA = cities[indexA];
                int cityB = cities[indexB];
                cities[indexA] = cityB;
                cities[indexB] = cityA;
                newSolution.setCitiesOrder(cities);
            }

            // change breaks
            for (int j = 0; j < newSolution.getBreakpoints().length; j++) {
                double pivot = RANDOM.nextDouble();
                if (pivot <= breaksChangeRatio / 2d) {
                    if(newSolution.getBreakpoints()[j] > 0) {
                        newSolution.getBreakpoints()[j]--;
                    }
                } else if (pivot >= 1d - (breaksChangeRatio / 2d)) {
                    if(newSolution.getBreakpoints()[j] < newSolution.getCitiesOrder().length) {
                        newSolution.getBreakpoints()[j]++;
                    }
                }
            }

            neighborSolutions[i] = newSolution;
        }


        return neighborSolutions;
    }
}

package evaluation;

import model.ModelHolder;
import model.Solution;

/**
 * EuclideanFitnessEvaluator of the solution.
 * Euclidean distance is used to evaluate solution.
 * Tested.
 */
public class EuclideanFitnessEvaluator {

    /**
     * Evaluates solution.
     * @param solution solution
     * @return evaluation
     */
    public static double calculateFitness(Solution solution) {
        double longestPath = 0;

        double currentPath = 0;
        for (int i = 0; i < solution.getCitiesOrder().size() - 1; i++) {
            int cityX = solution.getCitiesOrder().get(i);
            int cityY = solution.getCitiesOrder().get(i+1);

            double distance = calculateDistance(ModelHolder.getModel().get(cityX), ModelHolder.getModel().get(cityY));
            currentPath += distance;

            // end for one traveler and beginning for another
            if (i > 0 && cityY == 0) {
                longestPath = Math.max(longestPath, currentPath);
                currentPath = 0;
            }
        }

        return longestPath;
    }

    /**
     * Calculates distance between two cities.
     * @param x first city
     * @param y second city
     * @return Euclidean distance of cities
     */
    public static double calculateDistance(Integer[] x, Integer[]y) {
        return Math.sqrt(Math.pow(Math.abs(x[0] - y[0]), 2) + Math.pow(Math.abs(x[1] - y[1]), 2));
    }
}

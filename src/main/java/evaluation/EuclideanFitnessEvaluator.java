package evaluation;

import configuration.Configuration;
import model.ModelHolder;
import model.Solution;

import java.util.Arrays;

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
        Configuration.setFitnessEvaluated(Configuration.getFitnessEvaluated() + 1);

        double longestPath = 0;

        int[] visitedCities = new int[ModelHolder.getModel().size()];
        visitedCities[0] = 1;

        double currentPath = calculateDistance(ModelHolder.getModel().get(0), ModelHolder.getModel().get(solution.getCitiesOrder().get(0)));
        for (int i = 0; i < solution.getCitiesOrder().size() - 1; i++) {
            int cityX = solution.getCitiesOrder().get(i);
            int cityY = solution.getCitiesOrder().get(i+1);

            visitedCities[cityX] = 1;
            visitedCities[cityY] = 1;

            double distance = calculateDistance(ModelHolder.getModel().get(cityX), ModelHolder.getModel().get(cityY));
            currentPath += distance;

            // return of last traveler to pivot
            if (i + 1 == solution.getCitiesOrder().size() - 1) {
                currentPath += calculateDistance(ModelHolder.getModel().get(cityY), ModelHolder.getModel().get(0));
                longestPath = Math.max(longestPath, currentPath);
            }

            // end for one traveler and beginning for another
            if (cityY == 0) {
                longestPath = Math.max(longestPath, currentPath);
                currentPath = 0;
            }
        }

        // check all cities were visited
        if (Arrays.stream(visitedCities).sum() < ModelHolder.getModel().size()) {
            return Double.MAX_VALUE;
        }

        return longestPath > 0 ? longestPath : Double.MAX_VALUE;
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

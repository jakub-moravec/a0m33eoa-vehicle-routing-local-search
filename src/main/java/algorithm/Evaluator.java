package algorithm;

import config.Configuration;
import model.ModelHolder;
import model.Solution;

/**
 * Evaluator of the solution.
 * Euclidean distance is used to evaluate solution.
 * Tested.
 * TODO generalize
 */
public class Evaluator {

    /**
     * Evaluates solution.
     * @param solution solution
     * @return evaluation
     */
    public static double evaluate(Solution solution) {
        double longestPath = -1;

        solution.setWeights(new double[solution.getCitiesOrder().length]);

        for (int traveler = 0; traveler < Configuration.getNumberOfTravelers(); traveler++) {
            int start = solution.getTravelersStart(traveler);
            int end = solution.getTravelersEnd(traveler);

            if (start >= solution.getCitiesOrder().length || start >= solution.getCitiesOrder().length) {
                continue;
            }

            double travelersPath = calculateDistance(ModelHolder.getModel().get(0), ModelHolder.getModel().get(solution.getCitiesOrder()[start]));
            solution.getWeights()[start] += travelersPath;
            for (int i = start; i < end ; i++) {
                int cityX = solution.getCitiesOrder()[i];
                int cityY = solution.getCitiesOrder()[i+1];
                double distance = calculateDistance(ModelHolder.getModel().get(cityX), ModelHolder.getModel().get(cityY));
                travelersPath += distance;
                solution.getWeights()[i] += distance;
                solution.getWeights()[i+1] += distance;
            }
            double returnDistance = calculateDistance(ModelHolder.getModel().get(solution.getCitiesOrder()[end]), ModelHolder.getModel().get(0));
            travelersPath += returnDistance;
            solution.getWeights()[end] += returnDistance;

            if (travelersPath > longestPath) {
                longestPath = travelersPath;
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

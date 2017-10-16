import config.Configuration;
import input.InputReader;
import model.ModelHolder;
import model.Solution;

import java.io.IOException;

/**
 * Local search of vehicle routing problem.
 *
 * @author moravja8
 */
public class Main {



    public static void main(String[] args) throws IOException {
        InputReader.read("src/main/resources/data/mTSP_50.data");
    }

    /**
     * TODO
     * Dummy solution.
     * @return initial solution
     */
    private static int[] init() {
        return null;
    }

    /**
     * Evaluates solution.
     * @param solution solution
     * @return evaluation
     */
    private static double evaluate(Solution solution) {
        double longestPath = -1;

        for (int traveler = 0; traveler < Configuration.NUMBER_OF_TRAVELERS; traveler++) {
            int start = Solution.getTravelersStart(traveler);
            int end = Solution.getTravelersEnd(traveler);

            double travelersPath = calculateDistance(ModelHolder.getModel().get(0), ModelHolder.getModel().get(start));
            for (int i = start; i < end; i++) {
                travelersPath += calculateDistance(ModelHolder.getModel().get(i), ModelHolder.getModel().get(i + 1));
            }
            travelersPath += calculateDistance(ModelHolder.getModel().get(end), ModelHolder.getModel().get(0));

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
    private static double calculateDistance(Integer[] x, Integer[]y) {
        return Math.sqrt(Math.pow(Math.abs(x[0] - y[0]), 2) + Math.pow(Math.abs(x[1] - y[1]), 2));
    }


}

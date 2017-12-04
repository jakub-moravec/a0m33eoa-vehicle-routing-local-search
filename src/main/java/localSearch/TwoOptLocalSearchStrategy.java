package localSearch;

import configuration.Configuration;
import model.ModelHolder;
import model.Solution;
import templates.Individual;
import templates.operations.LocalSearchStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Two-opt local search strategy.
 * @author moravja8
 */
public class TwoOptLocalSearchStrategy implements LocalSearchStrategy<Solution, Solution> {

    private static final Random RANDOM = new Random();

    @Override
    public Optional<Individual<Solution, Solution>> localSearch(Individual<Solution, Solution> individual) {
        int startInclusive = 0;
        int endExclusive = -1;

        List<Integer> path = individual.getGenes().getCitiesOrder();
        path.add(0);

        for (int i = 0; i < path.size(); i++) {
            if(path.get(i) == 0 || i == path.size() -1) {
                startInclusive = endExclusive + 1;
                endExclusive = i;

                if (RANDOM.nextDouble() < Configuration.getProbabilityOfLocalSearch()) {
                    List<Integer> optimizedPath = performTwoOptOnPath(path.subList(startInclusive, endExclusive));

                    int k = 0;
                    for (int j = startInclusive; j < endExclusive; j++) {
                        path.set(j, optimizedPath.get(k));
                        k++;
                    }
                }
            }
        }

        path.remove(path.size() -1);

        return Optional.of(new Individual<>(new Solution(path)));
    }

    /**
     * Takes a simple TSP path a performs 2-opt on it. Pivots of original mTSP should not be included.
     *
     * @param path path taken by one traveler
     * @return optimized path
     */
    public List<Integer> performTwoOptOnPath(List<Integer> path) {
        List<Integer> clonePath = new ArrayList<>();
        clonePath.addAll(path);

        // add pivots to beginning and end
        clonePath.add(0, 0);
        clonePath.add(0);

        // perform 2-opt
        for (int i = 0; i < clonePath.size() - 2; i++) {
            for (int j = i + 1; j < clonePath.size() - 1; j++) {
                if (linesCrosses(clonePath, i, i + 1, j, j + 1)) {
                    clonePath = swapLines(clonePath,  i, i + 1, j, j + 1);
                }
            }
        }

        // remove pivots again
        clonePath.remove(0);
        clonePath.remove(clonePath.size() -1);

        return clonePath;
    }

    /**
     * Swaps crossed lines
     *
     * @param clonePath original path
     * @param fromA first line start city
     * @param toA first line end city
     * @param fromB second line start city
     * @param toB second line end city
     * @return new path
     */
    public List<Integer> swapLines(List<Integer> clonePath, int fromA, int toA, int fromB, int toB) {
        List<Integer> swapped = new ArrayList<>();

        swapped.addAll(clonePath.subList(0, fromA + 1));

        List<Integer> middlePart = clonePath.subList(toA, fromB + 1);
        Collections.reverse(middlePart);
        swapped.addAll(middlePart);

        swapped.addAll(clonePath.subList(fromB + 1, toB + 1));

        swapped.addAll(clonePath.subList(toB + 1, clonePath.size()));

        return swapped;
    }


    /**
     * Detect, whether two 2D lines crosses.
     *
     * @param clonePath original path
     * @param fromA first line start city
     * @param toA first line end city
     * @param fromB second line start city
     * @param toB second line end city
     * @return flag, whether lines are crossed
     */
    public boolean linesCrosses(List<Integer> clonePath,  int fromA, int toA, int fromB, int toB) {
        Integer[] cityFromA = ModelHolder.getModel().get(clonePath.get(fromA));
        Integer[] cityToA = ModelHolder.getModel().get(clonePath.get(toA));
        Integer[] cityFromB = ModelHolder.getModel().get(clonePath.get(fromB));
        Integer[] cityToB = ModelHolder.getModel().get(clonePath.get(toB));

        int s1_x = cityToA[0] - cityFromA[0]; //p1_x - p0_x;
        int s1_y = cityToA[1] - cityFromA[1]; //p1_y - p0_y;
        int s2_x = cityToB[0] - cityFromB[0]; //p3_x - p2_x;
        int s2_y = cityToB[1] - cityFromB[1]; //p3_y - p2_y;

        double divisor = -s2_x * s1_y + s1_x * s2_y;

        if (divisor == 0) {
           return false;
        }

        double s, t;
        s = (-s1_y * (cityFromA[0] - cityFromB[0]) + s1_x * (cityFromA[1] - cityFromB[1])) / (divisor);
        t = ( s2_x * (cityFromA[1] - cityFromB[1]) - s2_y * (cityFromA[0] - cityFromB[0])) / (divisor);

        return s >= 0 && s <= 1 && t >= 0 && t <= 1;
    }
}

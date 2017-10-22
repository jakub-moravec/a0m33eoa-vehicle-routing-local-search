import algorithm.Evaluator;
import input.InputReader;
import model.ModelHolder;
import model.Solution;

import java.io.IOException;
import java.util.List;

/**
 * Local search of vehicle routing problem.
 *
 * @author moravja8
 */
public class Main {



    public static void main(String[] args) throws IOException {
        InputReader.read("src/main/resources/data/mTSP_50.data");

        Solution initialSolution = getInitialSolution();
        printSolution(initialSolution, Evaluator.evaluate(initialSolution));
    }

    /**
     * Initial dummy solution.
     * Cities are in the same order as in input.
     * Breakpoints are in regular intervals.
     * @return initial solution
     */
    private static Solution getInitialSolution() {
        List<Integer[]> model = ModelHolder.getModel();

        int[] initialCities = new int[model.size() - 1];
        for (int i = 0; i < model.size(); i++) {
            if (i > 0) {
                initialCities[i-1] = i;
            }
        }

        int n = config.Configuration.getNumberOfTravelers();
        int breakInterval = (model.size() - 1) / n;

        int[] initialBreakpoints = new int[n - 1];
        for (int i = 0; i < n; i++) {
            if (i > 0) {
                initialBreakpoints[i-1] = breakInterval * i;
            }
        }

        return new Solution(initialCities, initialBreakpoints);
    }

    private static void printSolution (Solution solution, double evaluation) {
        System.out.println("0: " + solution.toString()+ "[" + evaluation + "]");
    }





}

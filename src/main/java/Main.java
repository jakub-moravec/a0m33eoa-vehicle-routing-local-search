import algorithm.BestNeighborAlgorithm;
import algorithm.Evaluator;
import config.Configuration;
import input.InputReader;
import model.ModelHolder;
import model.Solution;
import ui.DrawGraph;

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

        Solution solution = getInitialSolution();
        printSolution(0, solution);
        DrawGraph.createAndShowGui(solution);

        int generation = 0;
        do {
            Solution candidate = BestNeighborAlgorithm.getNextGenerationSolution(solution);
            if (candidate.getEvaluation() < solution.getEvaluation()) {
                solution = candidate;
                printSolution(generation+1, solution);
            }
            generation++;
        } while (generation < 5000);

        DrawGraph.createAndShowGui(solution);

        System.out.println(Configuration.FITNESS_EVALUATED);
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
        int breakInterval = (model.size() - 1) / n; // todo podle vzdalenosti, ne poctu

        int[] initialBreakpoints = new int[n - 1];
        for (int i = 0; i < n; i++) {
            if (i > 0) {
                initialBreakpoints[i-1] = breakInterval * i;
            }
        }

        Solution initialSolution = new Solution(initialCities, initialBreakpoints);
        initialSolution.setEvaluation(Evaluator.evaluate(initialSolution));

        return initialSolution;
    }

    private static void printSolution(int generation, Solution solution) {
        System.out.println(generation + ": " + solution.toString());
    }
}

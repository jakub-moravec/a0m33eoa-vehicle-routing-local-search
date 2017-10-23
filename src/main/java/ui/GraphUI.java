package ui;

import algorithm.Evaluator;
import input.InputReader;
import model.ModelHolder;
import model.Solution;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

/**
 * Created by jmoravec on 23.10.2017.
 */
public class GraphUI {


    /**
     * Test FIXME
     * @param args
     */
    public static void main(String[] args) throws IOException {
        InputReader.read("src/main/resources/data/mTSP_50.data");

        Solution solution = getInitialSolution();

        drawGraph(solution);
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

        Solution initialSolution = new Solution(initialCities, initialBreakpoints);
        initialSolution.setEvaluation(Evaluator.evaluate(initialSolution));

        return initialSolution;
    }

    public static void drawGraph(Solution solution) {
        JFrame okno = new JFrame();


        JPanel panel = new JPanel();

        okno.add(panel )   ;
        okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        okno.setVisible(true);
        okno.setSize(300, 200);
    }
}

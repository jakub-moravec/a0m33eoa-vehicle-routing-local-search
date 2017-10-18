package algorithm;

import config.Configuration;
import model.ModelHolder;
import model.Solution;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class EvaluatorTest {

    @Test
    public void evaluate() throws Exception {
        Integer[][] model = new Integer[][] {
                new Integer[]{2, 2},
                new Integer[]{0, 0},
                new Integer[]{0, 4},
                new Integer[]{4, 0},
                new Integer[]{4, 4},
        };
        ModelHolder.setModel(Arrays.asList(model));

        Solution[] inputs = new Solution[] {
                new Solution(new int[]{1, 2, 3, 4}, new int[]{2}),
                new Solution(new int[]{1, 2, 3, 4}, new int[]{1,2}),
                new Solution(new int[]{1, 2, 4, 3}, new int[]{4})
        };
        double[] expectedResults = new double[] {
                9.65685425,
                9.65685425,
                17.65685425
        };

        System.out.println(Arrays.deepToString(ModelHolder.getModel().toArray()));

        for (int i = 0; i < inputs.length; i++) {
            Solution solution = inputs[i];
            int numberOfTravelers = solution.getBreakpoints().length + 1;
            Configuration.setNumberOfTravelers(numberOfTravelers);
            double evaluation = Evaluator.evaluate(solution);
            Assert.assertEquals("Bad result in testcase " + i + ".", expectedResults[i], evaluation, 0.00001);
        }
    }

    @Test
    public void calculateDistance() throws Exception {
        //x1, y1, x2, y2
        int[][] inputs = new int[][] {
                new int[]{0, 0, 10000, 10000},
                new int[]{0, 0, 1, 1},
                new int[]{0, 0, 9, 9},
                new int[]{54, 76, 41, 82},
                new int[]{54, 76, 76, 54},
                new int[]{0, 0, 9999, 9999}
        };

        double[] expectedResults = new double[] {
                14142.135623730950488016887242097,
                1.4142135623730950488016887242097,
                12.727922061357855439215198517887,
                14.317821063276353154439601231034,
                31.112698372208091073637151932613,
                14140.721410168577392968085553373
        };

        for (int i = 0; i < inputs.length; i++) {
            double result = Evaluator.calculateDistance(new Integer[] {inputs[i][0], inputs[i][1]}, new Integer[] {inputs[i][2], inputs[i][3]});
            Assert.assertEquals(expectedResults[i], result, 0.00001);
        }
    }
}
package algorithm;

import config.Configuration;
import junit.framework.Assert;
import model.ModelHolder;
import model.Solution;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

/**
 * Test class.
 *
 * @author moravja8
 */
public class RandomSolutionGeneratorTest {

    private static final Random RANDOM = new Random();

    @Test
    public void generateSolution() throws Exception {
        Integer[][] model = new Integer[][] {
                new Integer[]{2, 2},
                new Integer[]{0, 0},
                new Integer[]{0, 4},
                new Integer[]{4, 0},
                new Integer[]{4, 4},
        };

        ModelHolder.setModel(Arrays.asList(model));

        for (int i = 0; i < 15; i++) {
            int travelers = RANDOM.nextInt(4) + 1;
            Configuration.setNumberOfTravelers(travelers);
            Solution solution = RandomSolutionGenerator.generateSolution();
            long pivotsNbr = solution.getCitiesOrder().stream().filter((city)-> city == 0).count();


            System.out.println(travelers + ": " + solution);

            // solution should contain (numberOfTravelers + 1) times pivot
            Assert.assertEquals(travelers + 1, pivotsNbr);
        }

    }

}
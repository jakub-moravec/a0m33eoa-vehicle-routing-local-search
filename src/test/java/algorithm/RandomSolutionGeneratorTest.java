package algorithm;

import configuration.Configuration;
import input.InputReader;
import junit.framework.Assert;
import model.Solution;
import org.junit.Test;

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
        InputReader.read("src/main/resources/data/mTSP_200.data");

        for (int i = 0; i < 40; i++) {
            int travelers = RANDOM.nextInt(50) + 1;
            Configuration.setNumberOfTravelers(travelers);
            Solution solution = RandomSolutionGenerator.generateSolution();
            long pivotsNbr = solution.getCitiesOrder().stream().filter((city)-> city == 0).count();


            System.out.println(travelers + ": " + solution);

            // solution should contain (numberOfTravelers + 1) times pivot
            Assert.assertEquals(travelers - 1, pivotsNbr);
            Assert.assertEquals(200 + (travelers - 2), solution.getCitiesOrder().size());
        }
    }
}
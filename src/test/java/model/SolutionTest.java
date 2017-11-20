package model;

import junit.framework.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * Test class.
 *
 * @author moravja8
 */
public class SolutionTest {

    @Test
    public void cloneTest() throws Exception {
        Solution solution = new Solution(Arrays.asList(1, 2, 0, 3, 4));
        Solution clone = solution.clone();
        clone.getCitiesOrder().set(0, 2);
        clone.getCitiesOrder().set(1, 1);

        Assert.assertEquals(new Integer(1), solution.getCitiesOrder().get(0));
        Assert.assertEquals(new Integer(2), solution.getCitiesOrder().get(1));
    }
}
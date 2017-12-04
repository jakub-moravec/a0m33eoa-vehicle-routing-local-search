package localsearch;

import localSearch.TwoOptLocalSearchStrategy;
import model.ModelHolder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jmoravec on 04.12.2017.
 */
public class TwoOptLocalSearchStrategyTest {
    private final TwoOptLocalSearchStrategy strategy = new TwoOptLocalSearchStrategy();

    @Before
    public void setUp() throws Exception {
        List<Integer[]> model = new ArrayList<>();
        model.add(new Integer[]{0, 0});
        model.add(new Integer[]{0, 4});
        model.add(new Integer[]{4, 4});
        model.add(new Integer[]{4, 0});
        model.add(new Integer[]{2, 2});
        ModelHolder.setModel(model);
    }

    @Test
    public void localSearch() throws Exception {

    }

    @Test
    public void performTwoOptOnPath() throws Exception {

    }

    @Test
    public void swapLines() throws Exception {

    }

    @Test
    public void linesCrosses() throws Exception {
        List<Integer> path = Arrays.asList(0, 2, 1, 3, 0);
        Assert.assertTrue(strategy.linesCrosses(path, 0, 1, 2, 3));

        List<Integer> path2 = Arrays.asList(0, 1, 2, 3, 0);
        Assert.assertFalse(strategy.linesCrosses(path2, 0, 1, 2, 3));
    }

}
package model;

import com.sun.istack.internal.Nullable;
import config.Configuration;

/**
 * Solution.
 *
 * Contains cities order and breakpoints.
 */
public class Solution {

    private static int[] citiesOrder;

    private static int[] breakpoints;

    @Nullable
    public static int[] getCitiesOrder() {
        return citiesOrder;
    }

    public static void setCitiesOrder(int[] citiesOrder) {
        Solution.citiesOrder = citiesOrder;
    }

    @Nullable
    public static int[] getBreakpoints() {
        return breakpoints;
    }

    public static void setBreakpoints(int[] breakpoints) {
        Solution.breakpoints = breakpoints;
    }

    public static int getTravelersStart(int traveler) {
        if (traveler == 0) {
           return 0;
        }
        return breakpoints[traveler];
    }

    public static int getTravelersEnd(int traveler) {
        if (traveler == Configuration.NUMBER_OF_TRAVELERS) {
            return ModelHolder.getModel().size() - 1;
        }
        return breakpoints[traveler + 1];
    }
}

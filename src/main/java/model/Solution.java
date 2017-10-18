package model;

import com.sun.istack.internal.Nullable;
import config.Configuration;

/**
 * Solution.
 *
 * Contains cities order and breakpoints.
 */
public class Solution {

    private int[] citiesOrder;

    private int[] breakpoints;

    public Solution(int[] citiesOrder, int[] breakpoints) {
        this.citiesOrder = citiesOrder;
        this.breakpoints = breakpoints;
    }

    @Nullable
    public int[] getCitiesOrder() {
        return citiesOrder;
    }

    public void setCitiesOrder(int[] citiesOrder) {
        this.citiesOrder = citiesOrder;
    }

    @Nullable
    public int[] getBreakpoints() {
        return breakpoints;
    }

    public void setBreakpoints(int[] breakpoints) {
        this.breakpoints = breakpoints;
    }

    public int getTravelersStart(int traveler) {
        if (traveler == 0) {
           return 0;
        }
        return breakpoints[traveler - 1];
    }

    public int getTravelersEnd(int traveler) {
        if (traveler == Configuration.getNumberOfTravelers() - 1) {
            return ModelHolder.getModel().size() - 2; // 1 + depot
        }
        return breakpoints[traveler] - 1;
    }
}

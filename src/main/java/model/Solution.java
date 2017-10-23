package model;

import com.sun.istack.internal.Nullable;
import config.Configuration;

import java.util.Arrays;

/**
 * Solution.
 *
 * Contains cities order and breakpoints.
 */
public class Solution {

    private int[] citiesOrder;

    private int[] breakpoints;

    private double[] weights;

    private double evaluation;

    public Solution() {
    }

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
        return Math.max(0, breakpoints[traveler] - 1);
    }

    public double getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(double evaluation) {
        this.evaluation = evaluation;
    }

    public double[] getWeights() {
        return weights;
    }

    public void setWeights(double[] weights) {
        this.weights = weights;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        int traveler = 0;

        // cities orders
        sb.append("[");
        for (int i = 0; i < citiesOrder.length; i++) {
            // add travelers break
            int currentTravelersEnd = traveler < Configuration.getNumberOfTravelers() - 1 ? breakpoints[traveler] : citiesOrder.length;
            if (currentTravelersEnd == i) {
                traveler++;
                sb.append("|");
            } else if(i != 0) {
                sb.append("-");
            }

            sb.append(citiesOrder[i]);
        }
        sb.append("]");

        //breakpoints
        sb.append("[");
        for (int i = 0; i < breakpoints.length; i++) {
            if (i != 0) {
                sb.append(",");
            }
            sb.append(breakpoints[i]);
        }
        sb.append("]");

        //evaluation
        sb.append("[").append(evaluation).append("]");

        return sb.toString();
    }

    @Override
    public Object clone() {
        Solution clone = new Solution();
        clone.setCitiesOrder(Arrays.copyOf(this.getCitiesOrder(), this.getCitiesOrder().length));
        clone.setBreakpoints(Arrays.copyOf(this.getBreakpoints(), this.getBreakpoints().length));
        clone.setWeights(Arrays.copyOf(this.getWeights(), this.getWeights().length));
        clone.setEvaluation(this.getEvaluation());
        return clone;
    }
}

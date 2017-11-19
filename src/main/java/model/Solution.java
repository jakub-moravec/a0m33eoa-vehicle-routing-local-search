package model;

import lombok.Getter;

import java.util.List;

/**
 * Solution.
 *
 * Contains cities order.
 *
 * TODO add breakpoints as special cities
 */
@Getter
public class Solution {

    private List<Integer> citiesOrder;

    private double evaluation;

    public Solution() {
    }

    public Solution(List<Integer> citiesOrder) {
        this.citiesOrder = citiesOrder;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();

        // cities orders
        sb.append("[");
        for (int i = 0; i < citiesOrder.size(); i++) {
            sb.append(citiesOrder.get(i));

            if(i < citiesOrder.size() -1) {
                sb.append("-");
            }
        }
        sb.append("]");

        //evaluation
        sb.append("[").append(evaluation).append("]");

        return sb.toString();
    }
}

package model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Solution.
 *
 * Contains cities order.
 */
@Getter
@Setter
public class Solution {

    private List<Integer> citiesOrder;

    private double evaluation;

    public Solution() {
        citiesOrder = new ArrayList<>();
    }

    public Solution(List<Integer> citiesOrder) {
        this.citiesOrder = citiesOrder;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

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

    @Override
    public Solution clone() {
        Solution clone = new Solution();
        clone.setEvaluation(this.getEvaluation());
        clone.setCitiesOrder(new ArrayList<>(this.citiesOrder));
        return clone;
    }
}

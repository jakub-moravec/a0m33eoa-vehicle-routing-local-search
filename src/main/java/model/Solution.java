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

        return sb.toString();
    }

    @Override
    public Solution clone() {
        Solution clone = new Solution();
        clone.setCitiesOrder(new ArrayList<>(this.citiesOrder));
        return clone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Solution)) return false;

        Solution solution = (Solution) o;

        return citiesOrder.equals(solution.citiesOrder);
    }

    @Override
    public int hashCode() {
        return citiesOrder.hashCode();
    }
}

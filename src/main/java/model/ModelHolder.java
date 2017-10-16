package model;

import com.sun.istack.internal.Nullable;

import java.util.List;

/**
 * Model holder.
 *
 * Contains the depot and the cities.
 */
public class ModelHolder {

    // cities
    private static List<Integer[]> model;

    @Nullable
    public static List<Integer[]> getModel() {
        return model;
    }

    public static void setModel(List<Integer[]> model) {
        ModelHolder.model = model;
    }
}

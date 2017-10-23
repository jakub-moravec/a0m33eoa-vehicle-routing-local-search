package input;

import model.ModelHolder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Input loader.
 *
 * Each row of input represents a city with its coordinates [x,y].
 * Format of the data is: x\ty.
 *
 * First row of input is the depot city.
 */
public class InputReader {

    private static final String DELIMITER = "\t";

    public static void read(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName))));

        List<Integer[]> model = new ArrayList<Integer[]>();

        String line;
        while ((line = br.readLine()) != null) {
            String[] entry = line.split(DELIMITER);
            model.add(new Integer[]{Integer.valueOf(entry[0]), Integer.valueOf(entry[1])});
        }

        ModelHolder.setModel(model);
    }
}

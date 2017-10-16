package input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

/**
 * Input loader.
 *
 * Each row of input represents a city with its coordinates [x,y].
 * Format of the data is: x\ty.
 *
 * First row of input is the depot city.
 */
public class InputReader {

    public static void read(String fileName) throws FileNotFoundException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName))));
    }
}

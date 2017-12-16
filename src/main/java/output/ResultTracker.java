package output;

import config.Configuration;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ResultTracker {

    public static double[][] results = new double[100000][Configuration.numberOfTries * 2];

    public static void addResult (int epocha, double bestResult) {
        results[epocha / Configuration.epochSampleModulo][Configuration.currentTry] = Configuration.fitnessEvaluated;
        results[epocha / Configuration.epochSampleModulo][Configuration.numberOfTries + Configuration.currentTry] = bestResult;
    }

    public static void writeResults() throws IOException {
        File file = new File(Configuration.outputFile);
        BufferedOutputStream bw = new BufferedOutputStream(new FileOutputStream(file));
        for (double[] resultLine : results) {
            for (double value : resultLine) {
                bw.write(Double.toString(value).getBytes());
                bw.write("\t".getBytes());
            }
            bw.write("\n".getBytes());
        }
    }
}
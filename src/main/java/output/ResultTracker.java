package output;

import configuration.Configuration;
import lombok.Getter;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ResultTracker {

    @Getter
    private static double[][] results = new double[(int) Math.ceil(Configuration.getMaxEpoch() / Configuration.getEpochSampleModulo())][Configuration.getNumberOfTries() * 2];

    public static void addResult (int epocha, double bestResult) {
            results[epocha / Configuration.getEpochSampleModulo()][Configuration.getCurrentTry()] = Configuration.getFitnessEvaluated();
            results[epocha / Configuration.getEpochSampleModulo()][Configuration.getNumberOfTries() + Configuration.getCurrentTry()] = bestResult;
    }

    public static void writeResults() throws IOException {
        File file = new File(Configuration.getOutputFile());
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

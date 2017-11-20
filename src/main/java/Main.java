import algorithm.RandomSolutionGenerator;
import config.Configuration;
import configuration.EvolutionConfiguration;
import configuration.EvolutionConfigurationBuilder;
import cycle.EvolutionExecutor;
import evaluation.EuclideanFitnessEvaluator;
import input.InputReader;
import model.Solution;
import templates.Individual;
import templates.IndividualWithAssignedFitness;
import templates.StatisticsPerEpoch;
import ui.DrawGraph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Logger;

/**
 * TODO javadoc
 */
public class Main {

    //parameters + configuration
    private static final Random RANDOM = new Random();
    private final static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws IOException {
        InputReader.read("src/main/resources/data/mTSP_50.data");
        DrawGraph.createAndShowGui(RandomSolutionGenerator.generateSolution());

        //types by order: genes, decoded genes - solution, fitness, container with statistics
        EvolutionConfiguration<Solution, Solution, Double, MyStatisticsPerEpoch> evolutionConfiguration = (new EvolutionConfigurationBuilder<Solution, Solution, Double, MyStatisticsPerEpoch>())
                //uniform crossover
                .crossover((firstParent, secondParent) -> {
                    Solution firstSetOfGenes = firstParent.getGenes().clone();
                    Solution secondSetOfGenes = secondParent.getGenes().clone();
                    // Each gene is inherited either from the 1st or the 2nd parent
                    for (int i = 0; i < firstParent.getGenes().getCitiesOrder().size(); i++) {
                        if (RANDOM.nextBoolean()) {
                            firstSetOfGenes.getCitiesOrder().set(i, secondParent.getGenes().getCitiesOrder().get(i));
                            secondSetOfGenes.getCitiesOrder().set(i, firstParent.getGenes().getCitiesOrder().get(i));
                        }
                    }

                    List<Individual<Solution, Solution>> returnList = new ArrayList<>();
                    returnList.add(new Individual<>(firstSetOfGenes));
                    returnList.add(new Individual<>(secondSetOfGenes));
                    return returnList;
                })
                // Mutation switches two cities
                .mutation(individual -> {
                    Solution mutatedSolution = individual.getGenes().clone();
                    for (int i = 0; i < mutatedSolution.getCitiesOrder().size(); i++) {
                        if (RANDOM.nextDouble() < Configuration.getProbabilityOfMutation()) {
                            int j = RANDOM.nextInt(mutatedSolution.getCitiesOrder().size());
                            int city = mutatedSolution.getCitiesOrder().get(i);
                            mutatedSolution.getCitiesOrder().set(i, mutatedSolution.getCitiesOrder().get(j));
                            mutatedSolution.getCitiesOrder().set(j, city);
                        }
                    }
                    return Optional.of(new Individual<>(mutatedSolution));
                })
                // selection
                .selector(population -> {
                    /*
                     * roulette - FIXME opravit vzhledem k minimalizaci
                     */
                    double overallFitness = 0;
                    for (IndividualWithAssignedFitness<Solution, Solution, Double> individual : population) {
                        overallFitness += individual.getFitness();
                    }

                    double bullet = RANDOM.nextDouble() * overallFitness ;

                    double actualFitness = 0;
                    for (IndividualWithAssignedFitness<Solution, Solution, Double> individual : population) {

                        actualFitness += individual.getFitness();
                        if (actualFitness > bullet) {
                            return  individual;
                        }
                    }

                    return null;
                })
                //generational replacement strategy. keep nothing from previous population // todo maybe keep something
                .replacement(currentPopulation -> new ArrayList<>())
                //strategy to initialize single individual - do it randomly
                .populationInitialization(() -> new Individual<>(RandomSolutionGenerator.generateSolution()))
                //strategy how to decode genes
                .decoding((x)->x)
                //how fitness is computed
                .fitnessAssessment(EuclideanFitnessEvaluator::calculateFitness)
                .fitnessIsMaximized(false)
                .parallel(false)
                .probabilityOfCrossover(Configuration.getProbabilityOfCrossover())
                .populationSize(Configuration.getPopulationSize())
                //when to terminate evolution
                .terminationCondition(epochs -> epochs.size() < Configuration.getMaxEpoch())
                //use own statistics
                .statisticsCreation(MyStatisticsPerEpoch::new)
                .build();
        EvolutionExecutor<Solution, Solution, Double, MyStatisticsPerEpoch> evolutionExecutor = new EvolutionExecutor<>(evolutionConfiguration);
        List<MyStatisticsPerEpoch> statistics = evolutionExecutor.run();
        long time = statistics.stream().mapToLong(StatisticsPerEpoch::getExecution).sum();
        MyStatisticsPerEpoch bestEpoch = statistics.stream().max(Comparator.comparing(stats -> stats.getBestIndividual().getFitness())).get();
        logger.info("Executed in " + time + ", best solution in epoch " + bestEpoch.getEpoch());
        DrawGraph.createAndShowGui(bestEpoch.getBestIndividual().getGenes());
    }

    /**
     * Own implementation of class with statistics, most important is method getSummary(). It is used to store and print results
     */
    private static class MyStatisticsPerEpoch extends StatisticsPerEpoch<Solution, Solution, Double> {

        MyStatisticsPerEpoch(int epoch, long execution, int countOfFitnessEvaluations, IndividualWithAssignedFitness<Solution, Solution, Double> bestIndividual, List<IndividualWithAssignedFitness<Solution, Solution, Double>> population) {
            super(epoch, execution, countOfFitnessEvaluations, bestIndividual, population);
        }

        public String getSummary() {
            return "Epoch " + epoch + ", avg. fitness: " + population.stream().mapToDouble(IndividualWithAssignedFitness::getFitness).average().orElse(0) + ", #fitness evaluations: " + countOfFitnessEvaluations + ", execution time:" + execution + "\n"
                    + "result: " + bestIndividual.getGenes() + ", best fitness: " + bestIndividual.getFitness().toString();
        }
    }

}

import algorithm.RandomSolutionGenerator;
import configuration.EvolutionConfiguration;
import configuration.EvolutionConfigurationBuilder;
import cycle.EvolutionExecutor;
import evaluation.EuclideanFitnessEvaluator;
import model.Solution;
import templates.Individual;
import templates.IndividualWithAssignedFitness;
import templates.StatisticsPerEpoch;

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

    public static void main(String[] args) {
        //types by order: genes, decoded genes - solution, fitness, container with statistics
        EvolutionConfiguration<Solution, Solution, Double, MyStatisticsPerEpoch> evolutionConfiguration = (new EvolutionConfigurationBuilder<Solution, Solution, Double, MyStatisticsPerEpoch>())
                //uniform crossover
                .crossover((firstParent, secondParent) -> {
                    Solution firstSetOfGenes = new Solution(), secondSetOfGenes = new Solution();
                    // Each gene is inherited either from the 1st or the 2nd parent
                    for (int i = 0; i < firstParent.getGenes().getCitiesOrder().size(); i++) {
                        if (RANDOM.nextBoolean()) {
                            firstSetOfGenes.getCitiesOrder().set(i, firstParent.getGenes().getCitiesOrder().get(i));
                        } else {
                            firstSetOfGenes.getCitiesOrder().set(i, secondParent.getGenes().getCitiesOrder().get(i));
                        }
                        if (RANDOM.nextBoolean()) {
                            secondSetOfGenes.getCitiesOrder().set(i, secondParent.getGenes().getCitiesOrder().get(i));
                        } else {
                            secondSetOfGenes.getCitiesOrder().set(i, firstParent.getGenes().getCitiesOrder().get(i));
                        }
                    }

                    List<Individual<Solution, Solution>> returnList = new ArrayList<>();
                    returnList.add(new Individual<>(firstSetOfGenes));
                    returnList.add(new Individual<>(secondSetOfGenes));
                    return returnList;
                })
                //simple bit-flip mutation
                .mutation(individual -> {
                    Solution genes = individual.getGenes().clone();
                    for (int i = 0; i < genes.getCitiesOrder().size(); i++) {
                        // Mutate each gene with probability Pm.
                        if (RANDOM.nextDouble() < 0.01) {
                            genes.getCitiesOrder().set(i, (genes.getCitiesOrder().get(i) + 1) % 2);    // swap between 0 and 1 FIXME záměna dvou bitů, ne bitflip
                        }
                    }
                    return Optional.of(new Individual<>(genes));
                })
                // selection
                .selector(population -> {
                    /*
                     * roulette
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
                .decoding(Main::decode)
                //how fitness is computed
                .fitnessAssessment(EuclideanFitnessEvaluator::calculateFitness)
                .fitnessIsMaximized(true)
                .parallel(true)
                .probabilityOfCrossover(0.75)
                .populationSize(50)
                //when to terminate evolution, after 100 epochs has been reached
                .terminationCondition(epochs -> epochs.size() < 100)
                //use own statistics
                .statisticsCreation(MyStatisticsPerEpoch::new)
                .build();
        EvolutionExecutor<Solution, Solution, Double, MyStatisticsPerEpoch> evolutionExecutor = new EvolutionExecutor<>(evolutionConfiguration);
        List<MyStatisticsPerEpoch> statistics = evolutionExecutor.run();
        long time = statistics.stream().mapToLong(StatisticsPerEpoch::getExecution).sum();
        MyStatisticsPerEpoch bestEpoch = statistics.stream().max(Comparator.comparing(stats -> stats.getBestIndividual().getFitness())).get();
        logger.info("Executed in " + time + ", best solution in epoch " + bestEpoch.getEpoch());
    }

    /**
     * Decodes genotype stored in 'genes'.
     *
     * @return an value coded by binary genes
     */
    private static Solution decode(Solution solution) {
        return solution;
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
                    + "result: " + decode(bestIndividual.getGenes()) + ", best fitness: " + bestIndividual.getFitness().toString();
        }
    }

}

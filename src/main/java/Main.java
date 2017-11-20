import algorithm.RandomSolutionGenerator;
import configuration.Configuration;
import configuration.EvolutionConfiguration;
import configuration.EvolutionConfigurationBuilder;
import cycle.EvolutionExecutor;
import evaluation.EuclideanFitnessEvaluator;
import input.InputReader;
import lombok.Getter;
import lombok.Setter;
import model.Solution;
import selection.TournamentSelectionStrategy;
import templates.Individual;
import templates.IndividualWithAssignedFitness;
import templates.StatisticsPerEpoch;
import templates.operations.SelectorStrategy;
import ui.DrawGraph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    //strategies
    @Getter
    @Setter
    private static SelectorStrategy<Solution, Solution, Double> selectorStrategy = new TournamentSelectionStrategy();

    public static void main(String[] args) throws IOException {
        InputReader.read("src/main/resources/data/mTSP_50.data");
        DrawGraph.createAndShowGui(RandomSolutionGenerator.generateSolution());

        //types by order: genes, decoded genes - solution, fitness, container with statistics
        EvolutionConfiguration<Solution, Solution, Double, MyStatisticsPerEpoch> evolutionConfiguration = (new EvolutionConfigurationBuilder<Solution, Solution, Double, MyStatisticsPerEpoch>())
                //ordered crossover
                .crossover((firstParent, secondParent) -> {
                    // choose two random numbers for the start and end indices of the slice
                    final int number1 = RANDOM.nextInt(firstParent.getGenes().getCitiesOrder().size());
                    final int number2 = RANDOM.nextInt(firstParent.getGenes().getCitiesOrder().size() + 1);

                    // make the smaller the start and the larger the end
                    final int start = Math.min(number1, number2);
                    final int end = Math.max(number1, number2);

                    // instantiate two child tours
                    final List<Integer> child1 = new ArrayList<>();
                    final List<Integer> child2 = new ArrayList<>();

                    // add the sublist in between the start and end points to the children
                    child1.addAll(firstParent.getGenes().getCitiesOrder().subList(start, end));
                    child2.addAll(secondParent.getGenes().getCitiesOrder().subList(start, end));

                    // pivots (0) need special handle
                    long pivotsInChild1 = child1.stream().filter((x) -> x ==0).count();
                    long pivotsInChild2 = child2.stream().filter((x) -> x ==0).count();

                    // iterate over each city in the parent tours
                    int currentCityIndex = 0;
                    int currentCityInTour1 = 0;
                    int currentCityInTour2 = 0;

                    for (int i = 0; i < firstParent.getGenes().getCitiesOrder().size(); i++) {
                        // get the index of the current city
                        currentCityIndex = (end + i) % firstParent.getGenes().getCitiesOrder().size();

                        // get the city at the current index in each of the two parent tours
                        currentCityInTour1 = firstParent.getGenes().getCitiesOrder().get(currentCityIndex);
                        currentCityInTour2 = secondParent.getGenes().getCitiesOrder().get(currentCityIndex);

                        // if child 1 does not already contain the current city in tour 2, add it
                        if (currentCityInTour2 != 0 && !child1.contains(currentCityInTour2)) {
                            child1.add(currentCityInTour2);
                        } else if (currentCityInTour2 == 0 && ++pivotsInChild1 < Configuration.getNumberOfTravelers()) {
                            child1.add(0);
                        }

                        // if child 2 does not already contain the current city in tour 1, add it
                        if (currentCityInTour1 != 0 && !child2.contains(currentCityInTour1)) {
                            child2.add(currentCityInTour1);
                        } else if (currentCityInTour1 == 0 && ++pivotsInChild2 < Configuration.getNumberOfTravelers()) {
                            child2.add(0);
                        }
                    }

                    // rotate the lists so the original slice is in the same place as in the
                    // parent tours
                    Collections.rotate(child1, start);
                    Collections.rotate(child2, start);

                    return Arrays.asList(new Individual<>(new Solution(child1)), new Individual<>(new Solution(child2)));
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
                // tournament selection
                .selector(population -> selectorStrategy.select(population))
                //generational replacement strategy. keep nothing from previous population
                .replacement(currentPopulation -> {
                    List<IndividualWithAssignedFitness<Solution, Solution, Double>> newPopulation = new ArrayList<>();
                    for (int i = 0; i < Configuration.getIndividualsToKeep(); i++) {
                        newPopulation.add(selectorStrategy.select(currentPopulation));
                    }
                    return newPopulation;
                })
                //strategy to initialize single individual - do it randomly
                .populationInitialization(() -> new Individual<>(RandomSolutionGenerator.generateSolution()))
                //strategy how to decode genes
                .decoding((x)->x)
                //how fitness is computed
                .fitnessAssessment(EuclideanFitnessEvaluator::calculateFitness)
                .fitnessIsMaximized(false)
                .parallel(true)
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
        LOGGER.info("Executed in " + time + ".");
        DrawGraph.createAndShowGui(statistics.get(statistics.size() - 1).getBestIndividual().getGenes());
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

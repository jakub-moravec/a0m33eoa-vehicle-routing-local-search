package mutation;

import configuration.Configuration;
import model.ModelHolder;
import model.Solution;
import templates.Individual;
import templates.operations.MutationStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Mutates solution by taking a subpath from start or end of on travelers path and changing it for start or end of another ones.
 *
 * @author moravja8
 */
public class SubpathExchangeMutationStrategy implements MutationStrategy<Solution, Solution> {

    private static final Random RANDOM = new Random();

    @Override
    public Optional<Individual<Solution, Solution>> mutation(Individual<Solution, Solution> individual) {

        //fixme
        int initialLength = individual.getGenes().getCitiesOrder().size();

        // determines, between which two paths subpaths will be exchanged
        int travelerFromId = RANDOM.nextInt(Configuration.getNumberOfTravelers());
        int travelerToId = RANDOM.nextInt(Configuration.getNumberOfTravelers());
        if (travelerFromId > travelerToId) {
            int foo = travelerToId;
            travelerToId = travelerFromId;
            travelerFromId = foo;
        }

        // determines, whether nodes will be taken and exchanged from beginning or end of the path
        // if changing from a path to the same, both start and end of subpath is always taken
        boolean nodesFromBeginning = RANDOM.nextBoolean();
        boolean nodesToBeginning = travelerToId != travelerFromId ? RANDOM.nextBoolean() : !nodesFromBeginning;

        // determines, how long are the exchanged subpaths going to be
        int numberOfExchangedNodes = RANDOM.nextInt(ModelHolder.getModel().size() / (Configuration.getNumberOfTravelers() * 2));

        int fromStartId = -1, fromEndId = -1, toStartId = -1, toEndId = -1;
        int startInclusive;
        int endExclusive = -1;
        int traveler = -1;
        List<Integer> path = new ArrayList<>(individual.getGenes().getCitiesOrder());
        path.add(0);

        for (int i = 0; i < path.size(); i++) {
            if(path.get(i) == 0 || i == path.size() -1) {
                startInclusive = endExclusive + 1;
                endExclusive = i;
                traveler++;

                if (traveler == travelerFromId) {
                    fromStartId = startInclusive;
                    fromEndId = endExclusive;
                    if (fromEndId - fromStartId - 1 < numberOfExchangedNodes) {
                        numberOfExchangedNodes = fromEndId - fromStartId - 1;
                    }
                }

                if (traveler == travelerToId) {
                    toStartId = startInclusive;
                    toEndId = endExclusive;
                    if (toEndId - toStartId - 1 < numberOfExchangedNodes) {
                        numberOfExchangedNodes = toEndId - toStartId - 1;
                    }
                    break;
                }
            }
        }

        // fixme
        if(Arrays.stream(new int[]{fromStartId, fromEndId, toStartId, toEndId}).filter(x -> x < 0).toArray().length > 0) {
            System.out.println(Arrays.deepToString(path.toArray()));
            System.out.println(nodesFromBeginning + " " + nodesToBeginning);
            System.out.println(Arrays.deepToString(new Object[]{fromStartId, fromEndId, toStartId, toEndId}));
        }

        if (nodesFromBeginning) {
            fromEndId = fromStartId + numberOfExchangedNodes + 1;
        } else {
            // fixme
            if (fromEndId - numberOfExchangedNodes - 1 < 0) {
                System.out.println("");
            }
            fromStartId = fromEndId - numberOfExchangedNodes - 1;
        }

        if (nodesToBeginning) {
            toEndId = toStartId + numberOfExchangedNodes + 1;
        } else {
            toStartId = toEndId - numberOfExchangedNodes - 1;
        }

        // fixme
        if(Arrays.stream(new int[]{fromStartId, fromEndId, toStartId, toEndId}).filter(x -> x < 0).toArray().length > 0) {
            System.out.println(Arrays.deepToString(path.toArray()));
            System.out.println(nodesFromBeginning + " " + nodesToBeginning);
            System.out.println(Arrays.deepToString(new Object[]{fromStartId, fromEndId, toStartId, toEndId}));
        }

        swapSubpaths(path, fromStartId, fromEndId, toStartId, toEndId);

        // remove zero at the end
        Solution mutated = new Solution(path.subList(0, path.size() -1));

        // fixme
        if(initialLength != mutated.getCitiesOrder().size()) {
            System.out.println(Arrays.deepToString(individual.getGenes().getCitiesOrder().toArray()));
            System.out.println(Arrays.deepToString(path.toArray()));
            System.out.println("ERRR");
        }

        return Optional.of(new Individual<>(mutated));
    }

    private void swapSubpaths(List<Integer> originalPath, int fromStartId, int fromEndId, int toStartId, int toEndId) {
        // backup the first subpath
        List<Integer> fromSubpath = new ArrayList<>();
        fromSubpath.addAll(originalPath.subList(fromStartId, fromEndId));

        // move second subpath to place of first one
        int j = fromStartId;
        for (int i = toStartId; i < toEndId; i++) {
            originalPath.set(j++, originalPath.get(i));
        }

        // move backed subpath to place of second one
        int k = toStartId;
        for (Integer e : fromSubpath) {
            originalPath.set(k++, e);
        }

        // thats it
    }
}

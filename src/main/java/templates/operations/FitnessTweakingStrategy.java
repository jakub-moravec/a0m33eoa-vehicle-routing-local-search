package templates.operations;


import templates.IndividualWithAssignedFitness;

import java.util.List;

public interface FitnessTweakingStrategy<V, T, K extends Comparable<K>> {
    IndividualFitnessUpdater<V, T, K> getIndividualUpdater(List<IndividualWithAssignedFitness<V, T, K>> population);
}

package templates.operations;


import templates.Individual;

import java.util.Optional;

public interface MutationStrategy<V, T> {
    Optional<Individual<V, T>> mutation(Individual<V, T> individual);
}

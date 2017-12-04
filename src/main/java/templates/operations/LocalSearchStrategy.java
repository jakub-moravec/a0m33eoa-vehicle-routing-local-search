package templates.operations;

import templates.Individual;

import java.util.Optional;

/**
 * For sake of memetic algorithms we add local search after crossover and mutation phase.
 * @author moravja8
 */
public interface LocalSearchStrategy<V, T> {
        Optional<Individual<V, T>> localSearch(Individual<V, T> individual);
}

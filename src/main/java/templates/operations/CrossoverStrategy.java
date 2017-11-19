package templates.operations;

import templates.Individual;

import java.util.List;

public interface CrossoverStrategy<V, T> {
    List<Individual<V, T>> crossover(Individual<V, T> firstParent, Individual<V, T> secondParent);
}

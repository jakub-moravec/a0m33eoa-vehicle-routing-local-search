package templates.operations;


import templates.Individual;

public interface PopulationInitializationStrategy<V, T> {
    Individual<V, T> initialize();
}

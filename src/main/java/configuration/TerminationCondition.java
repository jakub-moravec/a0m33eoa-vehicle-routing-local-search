package configuration;

import templates.StatisticsPerEpoch;

import java.util.List;

public interface TerminationCondition<V, T, K extends Comparable<K>, L extends StatisticsPerEpoch<V, T, K>> {
    boolean shouldTerminate(List<L> epochs);
}

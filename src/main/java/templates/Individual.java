package templates;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import templates.operations.DecodingStrategy;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "genes")
public class Individual<V, T> {
    private final V genes;

    public T decode(DecodingStrategy<V, T> decodingStrategy) {
        return decodingStrategy.decode(genes);
    }

}

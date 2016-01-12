package Interafaces;

import java.util.Collection;
import java.util.Optional;

public interface IKnowledge {
    Optional<String> getThesis();
    Optional<Collection<IFormula>> getFormulas();
}

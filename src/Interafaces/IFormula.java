package Interafaces;

import java.util.Collection;
import java.util.Optional;

public interface IFormula {
    Optional<Collection<String>> getPresumptions();
    Optional<String> getConsequent();
}

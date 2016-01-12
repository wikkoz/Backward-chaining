package Interafaces;

import java.util.List;
import java.util.Optional;

public interface ISentence {
    Optional<List<ISentence>> getAntecedents();
    Optional<String> getSentence();
}

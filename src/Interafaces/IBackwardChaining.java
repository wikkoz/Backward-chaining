package Interafaces;

import java.util.Optional;

public interface IBackwardChaining {
    Optional<ISentence> deduce(IKnowledge IKnowledge);
}

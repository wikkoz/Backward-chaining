package Interafaces;

import java.util.List;

public interface ISentence {
    List<ISentence> getAntecedents();
    String getSentence();
}

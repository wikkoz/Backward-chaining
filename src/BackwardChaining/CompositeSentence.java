package BackwardChaining;

import Interafaces.ISentence;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompositeSentence implements ISentence {

    private List<ISentence> antecedents;
    private Sentence sentence;

    public CompositeSentence(Sentence sentence) {
        this.sentence = sentence;
        this.antecedents = new ArrayList<>();
    }

    @Override
    public Optional<List<ISentence>> getAntecedents() {
        return Optional.of(antecedents);
    }

    @Override
    public Optional<String> getSentence() {
        return sentence.getSentence();
    }

    public void addAntecedent(ISentence sentence) {
        antecedents.add(sentence);
    }

}

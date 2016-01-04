package BackwardChaining;

import Interafaces.ISentence;

import java.util.ArrayList;
import java.util.List;

public class CompositeSentence implements ISentence {

    private List<ISentence> antecedents;
    private Sentence sentence;


    public CompositeSentence(Sentence sentence) {
        this.sentence = sentence;
        antecedents = new ArrayList<>();
    }

    @Override
    public List<ISentence> getAntecedents() {
        return antecedents;
    }

    @Override
    public String getSentence() {
        return sentence.getSentence();
    }

    public void addAntecedent(ISentence sentence) {
        antecedents.add(sentence);
    }

}

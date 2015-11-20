package BackwardChaining;

import Interafaces.ISentence;

import java.util.Collections;
import java.util.List;

public class Sentence {

    private String sentence;
    private boolean negated = false;

    public Sentence(String sentence) {
        this.sentence = sentence;
    }

    public Sentence(Sentence sentence) {
        this.sentence = sentence.getSentence();
    }

    public String getSentence() {
        return (negated ? "~" : "") + sentence;
    }

    public Sentence negate(){
        negated = ! negated;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sentence sentence1 = (Sentence) o;

        if (negated != sentence1.negated) return false;
        return sentence.equals(sentence1.sentence);

    }

    @Override
    public int hashCode() {
        int result = sentence.hashCode();
        result = 31 * result + (negated ? 1 : 0);
        return result;
    }
}

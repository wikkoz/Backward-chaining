package BackwardChaining;

import Interafaces.ISentence;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Sentence implements ISentence {

    private String sentenceString;
    private boolean negated = false;
    private Set<BCFormula> usedFormulas = new HashSet<>();

    public Sentence() {
    }

    public Sentence(String sentence) {
        if (sentence.charAt(0) == '~') {
            negated = true;
            this.sentenceString = sentence.substring(1);
        } else
            this.sentenceString = sentence;
    }

    public Sentence(Sentence sentence) {
        this.sentenceString = sentence.sentenceString;
        this.negated = sentence.negated;
    }

    @Override
    public List<ISentence> getAntecedents() {
        return Collections.emptyList();
    }

    public String getSentence() {
        return (negated ? "~" : "") + sentenceString;
    }

    public Sentence negate() {
        negated = !negated;
        return this;
    }


    public void usedFormula(BCFormula formula) {
        if(!formula.getPresumptions().isEmpty())
        usedFormulas.add(formula);
    }

    public boolean ifHasNotUsedFormula(BCFormula formula) {
        return !usedFormulas.contains(formula);
    }

    public void clearUsedFormulas() {
        usedFormulas.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sentence sentence1 = (Sentence) o;

        return negated == sentence1.negated && sentenceString.equals(sentence1.sentenceString);

    }

    @Override
    public int hashCode() {
        int result = sentenceString.hashCode();
        result = 31 * result + (negated ? 1 : 0);
        return result;
    }
}

package BackwardChaining;

import Interafaces.IFormula;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class BCFormula {

    private Set<Sentence> presumptions;
    private Sentence consequent;
    private boolean used;

    public BCFormula(IFormula formulas) {
        presumptions = new HashSet<>();
        addSentences(formulas);
        consequent = new Sentence(formulas.getConsequent());
    }

    private void addSentences(IFormula formulas) {
        Optional.ofNullable(formulas.getPresumptions())
                .orElse(Collections.emptySet())
                .forEach(s -> presumptions.add(new Sentence(s)));
    }

    public BCFormula(Set<Sentence> presumptions, Sentence consequent) {
        this.presumptions = presumptions;
        this.consequent = consequent;
    }

    public Set<Sentence> getPresumptions() {
        return presumptions;
    }

    public Sentence getConsequent() {
        return consequent;
    }

    public boolean isNotUsed() {
        return !used;
    }

    public void setUsed(boolean used) {
        if (!presumptions.isEmpty())
            this.used = used;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BCFormula BCFormula = (BCFormula) o;

        return consequent.equals(BCFormula.consequent) && presumptions.equals(BCFormula.presumptions);

    }

    @Override
    public int hashCode() {
        int result = presumptions.hashCode();
        result = 31 * result + consequent.hashCode();
        return result;
    }
}

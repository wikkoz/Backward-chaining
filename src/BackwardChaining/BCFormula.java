package BackwardChaining;

import Interafaces.IFormula;

import java.util.*;

public class BCFormula {

    private Set<Sentence> presumptions = new HashSet<>();
    private Sentence consequent;
    private boolean used;

    public BCFormula(IFormula formulas) {

        Optional.ofNullable(formulas.getPresumptions())
                .orElse(Collections.emptySet())
                .forEach(s -> presumptions.add(new Sentence(s)));
        consequent = new Sentence(formulas.getConsequent());
        used = false;
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
        if(!presumptions.isEmpty())
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

package BackwardChaining;

import Interafaces.IFormula;

import java.util.*;

public class Formula{

    private Set<Sentence> antecedents = new HashSet<>();
    private Sentence consequent;

    public Formula(IFormula formulas){
        formulas.getPresumptions()
                .forEach((s) -> antecedents.add(new Sentence(s)));
        consequent = new Sentence(formulas.getConsequent());
    }

    public Formula(Set<Sentence> antecedents, Sentence consequent) {
        this.antecedents = antecedents;
        this.consequent = consequent;
    }

    public Iterable<Sentence> getAntecedents() {
        return antecedents;
    }

    public Sentence getConsequent() {
        return consequent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Formula formula = (Formula) o;

        if (!antecedents.equals(formula.antecedents)) return false;
        return consequent.equals(formula.consequent);

    }

    @Override
    public int hashCode() {
        int result = antecedents.hashCode();
        result = 31 * result + consequent.hashCode();
        return result;
    }
}

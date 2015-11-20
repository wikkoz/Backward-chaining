package BackwardChaining;

import Interafaces.IFormula;

import java.util.*;
import java.util.stream.StreamSupport;

public class Implications {

    Map<Sentence,List<Formula>> implications = new HashMap<>();

    public void makeAllPossibleImplications(Formula formula){
        addElement(formula);
        StreamSupport.stream(formula.getAntecedents().spliterator(), false)
                .map((s) -> makeAntecedentAsConsequent(s, formula))
                .forEach(this::addElement);
    }

    private Formula makeAntecedentAsConsequent(Sentence antecedent, Formula formula){
        Set<Sentence> newAntecedents = new HashSet<>();
        formula.getAntecedents().forEach(newAntecedents::add);
        newAntecedents.remove(antecedent);
        newAntecedents.add(new Sentence(formula.getConsequent().negate()));
        return new Formula(newAntecedents, new Sentence(antecedent).negate());
    }

    private void addElement(Formula formula){
        Sentence sentence = formula.getConsequent();
        implications.compute(sentence, (k, v) -> (v == null) ? new ArrayList<>() : v).add(formula);
    }
}

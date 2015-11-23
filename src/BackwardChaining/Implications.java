package BackwardChaining;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Implications {

    private Map<Sentence,List<Formula>> implications = new HashMap<>();

    public void makeAllPossibleImplications(Formula formula){
        addElement(formula);
        StreamSupport.stream(formula.getPresumptions().spliterator(), false)
                .map((s) -> makeAntecedentAsConsequent(s, formula))
                .forEach(this::addElement);
    }

    private Formula makeAntecedentAsConsequent(Sentence antecedent, Formula formula){
        Set<Sentence> newAntecedents = new HashSet<>();
        formula.getPresumptions().forEach(newAntecedents::add);
        newAntecedents.remove(antecedent);
        newAntecedents.add(new Sentence(formula.getConsequent().negate()));
        return new Formula(newAntecedents, new Sentence(antecedent).negate());
    }

    private void addElement(Formula formula){
        Sentence sentence = formula.getConsequent();
        implications.compute(sentence, (k, v) -> (v == null) ? new ArrayList<>() : v).add(formula);
    }

    public Stream<Formula> findUnusedFormulasWithConsequent(Sentence sentence){
        return implications.get(sentence).stream()
                .filter(Formula::isUsed);
    }
}

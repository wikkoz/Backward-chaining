package BackwardChaining;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Implications {

    private Map<Sentence,List<BCFormula>> implications = new HashMap<>();

    public Map<Sentence,List<BCFormula>> makeAllPossibleImplications(BCFormula formula){
        addElement(formula);
        StreamSupport.stream(formula.getPresumptions().spliterator(), false)
                .map(s -> makeAntecedentAsConsequent(s, formula))
                .forEach(this::addElement);
        return implications;
    }

    private BCFormula makeAntecedentAsConsequent(Sentence antecedent, BCFormula formula){
        Set<Sentence> newAntecedents = new HashSet<>();
        formula.getPresumptions().forEach(newAntecedents::add);
        newAntecedents.remove(antecedent);
        newAntecedents.add(new Sentence(formula.getConsequent()).negate());
        return new BCFormula(newAntecedents, new Sentence(antecedent).negate());
    }

    private void addElement(BCFormula formula){
        Sentence sentence = formula.getConsequent();
        implications.compute(sentence, (k, v) -> (v == null) ? new ArrayList<>() : v).add(formula);
    }

    public Stream<BCFormula> findUnusedFormulasWithConsequent(Sentence sentence){
        return implications.getOrDefault(sentence, Collections.emptyList()).stream()
                .filter(BCFormula::isNotUsed);
    }
}

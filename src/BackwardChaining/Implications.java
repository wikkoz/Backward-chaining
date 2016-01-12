package BackwardChaining;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Implications {

    private Map<Sentence, List<BCFormula>> implications = new HashMap<>();

    public Optional<Map<Sentence, List<BCFormula>>> makeAllPossibleImplications(BCFormula formula) {
        addElement(formula);
        addFormulas(formula);
        return Optional.of(implications);
    }

    private void addFormulas(BCFormula formula) {
        StreamSupport.stream(formula.getPresumptions().spliterator(), false)
                .map(s -> makeAntecedentAsConsequent(s, formula))
                .forEach(s -> addElement(s.get()));
    }

    private Optional<BCFormula> makeAntecedentAsConsequent(Sentence antecedent, BCFormula formula) {
        Set<Sentence> newAntecedents = new HashSet<>();
        formula.getPresumptions().forEach(newAntecedents::add);
        newAntecedents.remove(antecedent);
        newAntecedents.add(new Sentence(formula.getConsequent()).negate());
        return Optional.of(new BCFormula(newAntecedents, new Sentence(antecedent).negate()));
    }

    private void addElement(BCFormula formula) {
        Sentence sentence = formula.getConsequent();
        implications.compute(sentence, (k, v) -> (v == null) ? new ArrayList<>() : v).add(formula);
    }

    public Stream<BCFormula> findUnusedFormulasWithConsequent(Sentence sentence) {
        return implications.getOrDefault(sentence, Collections.emptyList()).stream()
                .filter(BCFormula::isNotUsed);
    }

    public void clear() {
        implications.clear();
    }

}

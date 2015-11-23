package BackwardChaining;

import Interafaces.IBackwardChaining;
import Interafaces.IFormula;
import Interafaces.ISentence;
import Interafaces.Knowledge;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class BackwardChaining implements IBackwardChaining {

    private Implications implications = new Implications();
    private Map<Sentence, Integer> unconfirmed = new HashMap<>();
    private Map<Sentence, Integer> confirmed = new HashMap<>();
    private Stack<Formula> usedFormulas = new Stack<>();

    private void makeCompleteKnowledge(Iterable<IFormula> knowledge){
        StreamSupport.stream(knowledge.spliterator(), false)
                .map(Formula::new)
                .forEach(implications::makeAllPossibleImplications);
    }

    private void pullOutKnowlegde(Knowledge knowledge) {
        makeCompleteKnowledge(knowledge.getFormulas());
        unconfirmed.put(new Sentence(knowledge.getThesis()), 1);
    }

    private boolean ifHasNotConfirmedPresumptions(Formula formula){
        return StreamSupport.stream(formula.getPresumptions().spliterator(),false)
                .noneMatch(confirmed.keySet()::contains);
    }

    private Stream<Formula> checkUnconfirmedSentence(Sentence s){
        return implications.findUnusedFormulasWithConsequent(s)
                .filter(this::ifHasNotConfirmedPresumptions);
    }

    private void incrementUnconfirmed(Sentence sentence){
        unconfirmed.compute(sentence, (k, v) -> v==null ? 0: ++v);
    }

    private void decrementUnconfirmed(Sentence sentence){
        unconfirmed.compute(sentence, (k,v) -> --v);
        unconfirmed.remove(sentence, 0);
    }

    private Optional<Formula> findFormula(){
        return unconfirmed.keySet().stream()
                .flatMap(this::checkUnconfirmedSentence)
                .findAny();
    }

    private void confirmConsequent(Formula formula){
        formula.setUsed(true);
        Sentence consequent = formula.getConsequent();
        confirmed.put(consequent, unconfirmed.get(consequent));
        unconfirmed.remove(consequent);
        usedFormulas.add(formula);
        formula.getPresumptions().iterator().forEachRemaining(this::incrementUnconfirmed);
    }

    private void reverseLastStep() throws EmptyStackException{
        Formula formula = usedFormulas.pop();
        formula.getPresumptions().iterator().forEachRemaining(this::decrementUnconfirmed);
        Sentence consequent = formula.getConsequent();
        unconfirmed.put(consequent, confirmed.get(consequent));
        confirmed.remove(formula.getConsequent());

    }

    private boolean confirmThesis(){
        while(unconfirmed.size()!=0) {
            if (findFormula().isPresent())
                confirmConsequent(findFormula().get());
            else {
                try {
                    reverseLastStep();
                }
                catch (EmptyStackException e){
                    return false;
                }
            }
        }
        return  true;
    }

    @Override
    public ISentence deduce(Knowledge knowledge) {
        pullOutKnowlegde(knowledge);
        confirmThesis();
        return null;
    }
}

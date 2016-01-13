package BackwardChaining;

import Interafaces.IBackwardChaining;
import Interafaces.IFormula;
import Interafaces.IKnowledge;
import Interafaces.ISentence;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class BackwardChaining implements IBackwardChaining {

    private Implications implications = new Implications();
    private Map<Sentence, Integer> unconfirmed = new HashMap<>();
    private Map<Sentence, Integer> confirmed = new HashMap<>();
    private Stack<BCFormula> usedBCFormulas = new Stack<>();

    private void makeCompleteKnowledge(Iterable<IFormula> knowledge) {
        StreamSupport.stream(knowledge.spliterator(), false)
                .map(BCFormula::new)
                .forEach(implications::makeAllPossibleImplications);
    }

    public void pullOutKnowledge(IKnowledge IKnowledge) {
        makeCompleteKnowledge(IKnowledge.getFormulas());
        unconfirmed.put(new Sentence(IKnowledge.getThesis()), 1);
    }


    private Stream<BCFormula> checkUnconfirmedSentence(Sentence s) {
        return implications.findUnusedFormulasWithConsequent(s)
                .filter(s::ifHasNotUsedFormula);
    }

    private void incrementUnconfirmed(Sentence sentence) {
        unconfirmed.compute(sentence, (k, v) -> v == null ? 1 : ++v);
    }

    private void decrementUnconfirmed(Sentence sentence) {
        unconfirmed.compute(sentence, (k, v) -> --v);
        unconfirmed.remove(sentence, 0);
    }

    public Optional<BCFormula> findFormula() {
        return unconfirmed.keySet().stream()
                .flatMap(this::checkUnconfirmedSentence)
                .findAny();
    }
    private void confirmConsequent(BCFormula formula) {
        formula.setUsed(true);
        Sentence consequent = formula.getConsequent();
        consequent.usedFormula(formula);
        confirmed.compute(consequent, (k, v) -> v == null ?unconfirmed.get(consequent) : unconfirmed.get(consequent) + v);
        unconfirmed.remove(consequent);
        usedBCFormulas.add(formula);
        formula.getPresumptions().iterator().forEachRemaining(this::incrementUnconfirmed);
        allowUseFormulasIfConfirmed();
    }

    private void allowUseFormulasIfConfirmed(){
        List<Sentence> confirmedPresumptions = new ArrayList<>();
        List<BCFormula> reverse = new ArrayList<>(usedBCFormulas);
        Collections.reverse(reverse);
        for(BCFormula formula:reverse){
            if(formula.isNotUsed())
                confirmedPresumptions.add(formula.getConsequent());
            else if (formula.getPresumptions().stream().allMatch(confirmedPresumptions::contains)) {
                formula.getConsequent().clearUsedFormulas();
                formula.setUsed(false);
                confirmedPresumptions.add(formula.getConsequent());
            }
            else
                return;
        }
    }

    private void reverseUnusedSteps() throws EmptyStackException {
        BCFormula formula;
        do {
            formula = usedBCFormulas.pop();
            formula.getPresumptions().iterator().forEachRemaining(this::decrementUnconfirmed);
            Sentence consequent = formula.getConsequent();
            unconfirmed.compute(consequent, (k,v)-> v==null ? confirmed.get(consequent): v + confirmed.getOrDefault(consequent, 0));
            confirmed.remove(consequent);
        } while (formula.isNotUsed() || checkIfFormulaCannotBeProved(formula));
    }

    private boolean checkIfFormulaCannotBeProved(BCFormula formula) {
        return !checkUnconfirmedSentence(formula.getConsequent())
                        .findFirst().isPresent() ;
    }

    private void findOutIfThesisCanBeConfirmed() throws EmptyStackException {
        while (unconfirmed.size() != 0) {
            Optional<BCFormula> formula = findFormula();
            if (formula.isPresent())
                confirmConsequent(formula.get());
            else {
                reverseUnusedSteps();
            }
        }
    }

    public boolean confirmThesis() {
        try {
            findOutIfThesisCanBeConfirmed();
        } catch (EmptyStackException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void clear() {
        implications.clear();
        unconfirmed.clear();
        confirmed.clear();
        usedBCFormulas.clear();
    }

    @Override
    public ISentence deduce(IKnowledge IKnowledge) {
        clear();
        pullOutKnowledge(IKnowledge);
        if (confirmThesis()) {
            Tree tree = new Tree(usedBCFormulas, new Sentence(IKnowledge.getThesis()));
            return tree.makeTree();
        }
        return new Sentence();
    }
}

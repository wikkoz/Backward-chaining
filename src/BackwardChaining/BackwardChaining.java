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

    public void pullOutKnowlegde(IKnowledge IKnowledge) {
        makeCompleteKnowledge(IKnowledge.getFormulas());
        unconfirmed.put(new Sentence(IKnowledge.getThesis()), 1);
    }

    private boolean ifHasNotConfirmedPresumptions(BCFormula formula) {
        return StreamSupport.stream(formula.getPresumptions().spliterator(), false)
                .noneMatch(confirmed.keySet()::contains);
    }

    private Stream<BCFormula> checkUnconfirmedSentence(Sentence s) {
        return implications.findUnusedFormulasWithConsequent(s)
                .filter(s::ifHasNotUsedFormula);
               // .filter(this::ifHasNotConfirmedPresumptions);
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
        confirmed.put(consequent, unconfirmed.get(consequent));
        unconfirmed.remove(consequent);
        usedBCFormulas.add(formula);
        formula.getPresumptions().iterator().forEachRemaining(this::incrementUnconfirmed);
    }

    private void reverseLastStep() throws EmptyStackException {
        BCFormula formula;
        List<Sentence> confirmedPresumptions = new ArrayList<>();
        do {
            formula = usedBCFormulas.pop();
            formula.getPresumptions().iterator().forEachRemaining(this::decrementUnconfirmed);
            Sentence consequent = formula.getConsequent();
            unconfirmed.put(consequent, confirmed.get(consequent));
            confirmed.remove(formula.getConsequent());
            confirmedPresumptions.add(formula.getConsequent());
            formula.getPresumptions().stream().forEach(Sentence::clearUsedFormulas);
        } while (formula.isNotUsed());
        if (formula.getPresumptions().stream().allMatch(confirmedPresumptions::contains)) {
            formula.getConsequent().clearUsedFormulas();
            formula.setUsed(false);
        }
    }

    private boolean checkIfPeekHasNotAnotherFormula() {
        return !usedBCFormulas.isEmpty() &&
                !checkUnconfirmedSentence(usedBCFormulas.peek().getConsequent())
                        .findFirst().isPresent();
    }

    private void findOutIfThesisCanBeConfirmed() throws EmptyStackException {
        while (unconfirmed.size() != 0) {
            Optional<BCFormula> formula = findFormula();
            if (formula.isPresent())
                confirmConsequent(formula.get());
            else {
                do {
                    reverseLastStep();
                } while (checkIfPeekHasNotAnotherFormula());
                reverseLastStep();
            }
        }
    }

    public boolean confirmThesis() {
        try {
            findOutIfThesisCanBeConfirmed();
        } catch (EmptyStackException e) {
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
        pullOutKnowlegde(IKnowledge);
        if (confirmThesis()) {
            Tree tree = new Tree(usedBCFormulas, new Sentence(IKnowledge.getThesis()));
            return tree.makeTree();
        }
        return new Sentence();
    }
}

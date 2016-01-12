package BackwardChaining;

import Interafaces.ISentence;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;

public class Tree {

    private ISentence root;
    private Sentence thesis;
    private Map<Sentence, BCFormula> usedImplications = new HashMap<>();

    public Tree(Stack<BCFormula> usedFormulas, Sentence thesis) {
        usedFormulas.stream()
                .forEach(f -> usedImplications.put(f.getConsequent(), f));
        this.thesis = thesis;
    }

    private Optional<ISentence> addNode(Sentence sentence) {
        BCFormula formula = usedImplications.get(sentence);
        if (!formula.getPresumptions().isEmpty()) {
            CompositeSentence compositeSentence = new CompositeSentence(sentence);
            for (Sentence presumption : formula.getPresumptions()) {
                compositeSentence.addAntecedent(addNode(presumption).get());
            }
            return Optional.of(compositeSentence);
        }
        return Optional.of(sentence);
    }

    public Optional<ISentence> makeTree() {
        root = addNode(thesis).get();
        return Optional.of(root);
    }
}

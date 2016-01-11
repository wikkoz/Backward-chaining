package BackwardChaining;

import Interafaces.ISentence;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Tree {

    private ISentence root;
    private Sentence thesis;
    private Map<Sentence, BCFormula> usedImplications;

    public Tree(Stack<BCFormula> usedFormulas, Sentence thesis) {
        usedImplications = new HashMap<>();
        usedFormulas.stream().forEach(f -> usedImplications.put(f.getConsequent(), f));
        this.thesis = thesis;
    }

    private ISentence addNode(Sentence sentence) {
        BCFormula formula = usedImplications.get(sentence);
        if (!formula.getPresumptions().isEmpty()) {
            CompositeSentence compositeSentence = new CompositeSentence(sentence);
            for (Sentence presumption : formula.getPresumptions()) {
                compositeSentence.addAntecedent(addNode(presumption));
            }
            return compositeSentence;
        }
        return sentence;
    }

    public ISentence makeTree() {
        root = addNode(thesis);
        return root;
    }
}

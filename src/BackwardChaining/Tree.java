package BackwardChaining;

import Interafaces.ISentence;
import Model.Formula;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Tree {

    private ISentence root;
    private Sentence thesis;
    private Map<Sentence, BCFormula> usedImplications = new HashMap<>();

    public Tree(Stack<BCFormula> usedFormulas, Sentence thesis) {
        usedFormulas.stream().forEach(f-> usedImplications.put(f.getConsequent(),f));
        this.thesis = thesis;
    }

    private ISentence addNode(Sentence sentence){
        BCFormula formula = usedImplications.remove(sentence);
        if(!formula.getPresumptions().isEmpty()){
            CompositeSentence compositeSentence = new CompositeSentence(sentence);
            for(Sentence presumption: formula.getPresumptions()){
                compositeSentence.addAntecedent(addNode(presumption));
            }
            return compositeSentence;
        }
        return sentence;
    }

    public ISentence makeTree(){
        root = addNode(thesis);
        return root;
    }
}

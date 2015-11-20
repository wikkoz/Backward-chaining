package BackwardChaining;

import Interafaces.IBackwardChaining;
import Interafaces.IFormula;
import Interafaces.ISentence;
import Interafaces.Knowledge;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.StreamSupport;

public class BackwardChaining implements IBackwardChaining {

    Implications implications = new Implications();

    private void makeCompleteKnowledge(Iterable<IFormula> knowledge){
        StreamSupport.stream(knowledge.spliterator(), false)
                .map(Formula::new)
                .forEach(implications::makeAllPossibleImplications);
    }

    @Override
    public ISentence deduce(Knowledge knowledge) {
        return null;
    }
}

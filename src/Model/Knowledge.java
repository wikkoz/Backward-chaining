package Model;

import Interafaces.IKnowledge;
import Interafaces.IFormula;

import java.util.Collection;
import java.util.List;

public class Knowledge implements IKnowledge {
    public String thesis;
    public List<IFormula> formulas;


    @Override
    public String getThesis() {
        return thesis;
    }

    @Override
    public Collection<IFormula> getFormulas() {
        return formulas;
    }
}

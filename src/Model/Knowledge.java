package Model;

import Interafaces.IKnowledge;
import Interafaces.IFormula;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Knowledge implements IKnowledge {
    private String thesis;
    private List<IFormula> formulas;

    public Knowledge() {}

    public Knowledge(String input) {
        String[] lines = input.split(System.getProperty("line.separator"));
        thesis=lines[0];
        formulas= new ArrayList<>();
        for(int i=1; i<lines.length;++i){
            formulas.add(new Formula(lines[i]));
        }
    }

    public void setThesis(String thesis) {
        this.thesis = thesis;
    }

    public void setFormulas(List<IFormula> formulas) {
        this.formulas = formulas;
    }

    @Override
    public String getThesis() {
        return thesis;
    }

    @Override
    public Collection<IFormula> getFormulas() {
        return formulas;
    }
}

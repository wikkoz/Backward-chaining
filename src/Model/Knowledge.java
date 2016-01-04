package Model;

import Interafaces.IFormula;
import Interafaces.IKnowledge;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Knowledge implements IKnowledge {
    private String thesis;
    private List<IFormula> formulas;

    public Knowledge() {
    }

    public Knowledge(String input) {
        String[] lines = input.split(System.getProperty("line.separator"));
        thesis = lines[0];
        formulas = new ArrayList<>();
        for (int i = 1; i < lines.length; ++i) {
            formulas.add(new Formula(lines[i]));
        }
    }

    @Override
    public String getThesis() {
        return thesis;
    }

    public void setThesis(String thesis) {
        this.thesis = thesis;
    }

    @Override
    public Collection<IFormula> getFormulas() {
        return formulas;
    }

    public void setFormulas(List<IFormula> formulas) {
        this.formulas = formulas;
    }
}

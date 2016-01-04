package Model;

import Interafaces.IFormula;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Formula implements IFormula {

    private String consequent;
    private List<String> presumptions = new ArrayList<>();

    public Formula(String formula) {
        String[] elements = formula.split("=>");
        if (elements.length == 2) {
            consequent = elements[1];
            Collections.addAll(presumptions, elements[0].split("&"));
        } else
            consequent = elements[0];
    }

    @Override
    public Collection<String> getPresumptions() {
        return presumptions;
    }

    @Override
    public String getConsequent() {
        return consequent;
    }
}

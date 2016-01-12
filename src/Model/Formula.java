package Model;

import Interafaces.IFormula;

import java.util.*;

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
    public Optional<Collection<String>> getPresumptions() {
        return Optional.of(presumptions);
    }

    @Override
    public Optional<String> getConsequent() {
        return Optional.of(consequent);
    }
}

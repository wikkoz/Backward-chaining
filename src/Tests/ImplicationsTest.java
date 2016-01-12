package Tests;

import BackwardChaining.BCFormula;
import BackwardChaining.Implications;
import BackwardChaining.Sentence;
import Model.Formula;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ImplicationsTest {

    Implications implications = new Implications();

    @Test
    public void testConsequent() {
        BCFormula formula = new BCFormula(new Formula("A"));
        Map<Sentence, List<BCFormula>> result;
        result = implications.makeAllPossibleImplications(formula).get();
        assertEquals(true, result.size() == 1);
        assertEquals(result.get(formula.getConsequent()).get(0), formula);
    }

    @Test
    public void testPresumptions() {
        Sentence A = new Sentence("~A");
        Sentence B = new Sentence("~B");
        BCFormula implicationB = new BCFormula(new Formula("A&~C=>~B"));
        BCFormula implicationA = new BCFormula(new Formula("B&~C=>~A"));
        BCFormula formula = new BCFormula(new Formula("A&B=>C"));
        Map<Sentence, List<BCFormula>> result;
        result = implications.makeAllPossibleImplications(formula).get();
        assertEquals(true, result.size() == 3);
        assertEquals(result.get(formula.getConsequent()).get(0), formula);
        assertEquals(result.get(A).get(0), implicationA);
        assertEquals(result.get(B).get(0), implicationB);
    }
}

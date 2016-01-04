package Tests;

import BackwardChaining.BCFormula;
import BackwardChaining.Sentence;
import Model.Formula;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BCFormulaTest {
    @Test
    public void testConsequent() {
        Formula formula = new Formula("A");
        BCFormula bcFormula = new BCFormula(formula);
        assertTrue(bcFormula.getPresumptions().isEmpty());
        assertEquals("A", bcFormula.getConsequent().getSentence());
    }

    @Test
    public void testBCFormula() {
        Formula formula = new Formula("A&B=>C");
        BCFormula bcFormula = new BCFormula(formula);
        assertEquals("C", bcFormula.getConsequent().getSentence());
        assertEquals(true, bcFormula.getPresumptions().contains(new Sentence("A")));
        assertEquals(true, bcFormula.getPresumptions().contains(new Sentence("B")));
    }
}

package Tests;

import BackwardChaining.BCFormula;
import BackwardChaining.BackwardChaining;
import Interafaces.IFormula;
import Model.Formula;
import Model.Knowledge;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class BCTest {
    private Knowledge knowledge = new Knowledge();
    private List<IFormula> formulas;
    private BackwardChaining bc;

    @Before
    public void init() {
        formulas = new ArrayList<>();
        bc = new BackwardChaining();
    }

    @Test
    public void firstTest() {
        Formula formula = new Formula("A&B=>C");
        formulas.add(formula);
        formulas.add(new Formula("A"));
        formulas.add(new Formula("B"));
        knowledge.setThesis("C");
        knowledge.setFormulas(formulas);
        bc.pullOutKnowlegde(knowledge);
        assertEquals(bc.findFormula().get(), new BCFormula(formula));
    }

    @Test
    public void secondTest() {
        formulas.add(new Formula("D&B=>C"));
        formulas.add(new Formula("E&J=>C"));
        formulas.add(new Formula("G&K=>C"));
        formulas.add(new Formula("A&B=>C"));
        formulas.add(new Formula("A"));
        formulas.add(new Formula("B"));
        formulas.add(new Formula("G"));
        knowledge.setThesis("C");
        knowledge.setFormulas(formulas);
        bc.pullOutKnowlegde(knowledge);
        assertEquals(true, bc.confirmThesis());
    }

    @Test
    public void thirdTest() {
        formulas.add(new Formula("C&B=>A"));
        formulas.add(new Formula("D&G=>C"));
        formulas.add(new Formula("E&F=>D"));
        formulas.add(new Formula("H&I=>G"));
        formulas.add(new Formula("G&J=>B"));
        formulas.add(new Formula("K=>C"));
        formulas.add(new Formula("H"));
        formulas.add(new Formula("I"));
        formulas.add(new Formula("J"));
        formulas.add(new Formula("E"));
        formulas.add(new Formula("K"));
        knowledge.setThesis("A");
        knowledge.setFormulas(formulas);
        bc.pullOutKnowlegde(knowledge);
        assertEquals(true, bc.confirmThesis());
    }

    @Test
    public void forthTest() {
        formulas.add(new Formula("C&B=>A"));
        formulas.add(new Formula("D&G=>C"));
        formulas.add(new Formula("E&F=>D"));
        formulas.add(new Formula("H&I=>G"));
        formulas.add(new Formula("G&J=>B"));
        formulas.add(new Formula("K=>C"));
        formulas.add(new Formula("H"));
        formulas.add(new Formula("I"));
        formulas.add(new Formula("J"));
        formulas.add(new Formula("E"));
        formulas.add(new Formula("K"));
        knowledge.setThesis("A");
        knowledge.setFormulas(formulas);
        bc.pullOutKnowlegde(knowledge);
        assertEquals(true, bc.confirmThesis());
    }

    @Test
    public void fifthTest() {
        formulas.add(new Formula("A&B=>C"));
        formulas.add(new Formula("B=>D"));
        formulas.add(new Formula("C=>~D"));
        formulas.add(new Formula("~D=>C"));
        formulas.add(new Formula("C=>E"));
        formulas.add(new Formula("D=>~E"));
        formulas.add(new Formula("E"));
        knowledge.setThesis("~B");
        knowledge.setFormulas(formulas);
        bc.pullOutKnowlegde(knowledge);
        assertEquals(true, bc.confirmThesis());
    }
}

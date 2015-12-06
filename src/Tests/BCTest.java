package Tests;

import BackwardChaining.BackwardChaining;
import BackwardChaining.BCFormula;
import Interafaces.IFormula;
import Model.Formula;
import Model.Knowledge;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class BCTest {
    private Knowledge knowledge = new Knowledge();
    private List<IFormula> formulas;
    private BackwardChaining bc ;

    @Before
    public void init(){
        formulas= new ArrayList<>();
        bc = new BackwardChaining();
    }

    @Test
    public void firstTest(){
        Formula formula = new Formula("A&B=>C");
        formulas.add(formula);
        formulas.add(new Formula("A"));
        formulas.add(new Formula("B"));
        knowledge.thesis="C";
        knowledge.formulas=formulas;
        bc.pullOutKnowlegde(knowledge);
        assertEquals(bc.findFormula().get(), new BCFormula(formula));
    }

    @Test
    public void secondTest(){
        formulas.add(new Formula("D&B=>C"));
        formulas.add(new Formula("E&J=>C"));
        formulas.add(new Formula("G&K=>C"));
        formulas.add(new Formula("A&B=>C"));
        formulas.add(new Formula("A"));
        formulas.add(new Formula("B"));
        formulas.add(new Formula("G"));
        knowledge.thesis="C";
        knowledge.formulas = formulas;
        bc.pullOutKnowlegde(knowledge);
        assertEquals(true,bc.confirmThesis());
    }
}

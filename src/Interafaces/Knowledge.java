package Interafaces;

public abstract class Knowledge implements IFormula{
    Iterable<IFormula> formulas;
    Iterable<String> facts;
    String thesis;

    public abstract Iterable<String> getFacts();

    public abstract String getThesis();

}

package Interafaces;

public abstract class Knowledge{
    protected Iterable<IFormula> formulas;
    protected String thesis;

    public abstract String getThesis();

    public abstract Iterable<IFormula> getFormulas();

}

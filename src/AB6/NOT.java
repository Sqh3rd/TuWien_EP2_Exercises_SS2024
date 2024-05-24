package AB6;

/**
 * This class represents a Boolean negation (NOT).
 */
public class NOT implements Condition
{
    private final Condition c;

    /**
     * Initializes 'this' as the negation 'NOT c'.
     * @param c the operand for the NOT operator, c != null.
     */
    public NOT(Condition c) {
        this.c = c;
    }

    @Override
    public IntVarSet getVarSet() {
        return c.getVarSet();
    }

    @Override
    public boolean getValue(IntVarConstMap assignments) {
        return !c.getValue(assignments);
    }
}

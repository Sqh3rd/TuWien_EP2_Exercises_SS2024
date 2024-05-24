package AB6;

/**
 * This class represents a Boolean AND-combination.
 */
public class AND implements Condition
{
    private final Condition c1;
    private final Condition c2;

    /**
     * Initializes 'this' as the Boolean combination 'c1 AND c2'.
     * @param c1 the first operand, c1 != null.
     * @param c2 the second operand, c2 != null.
     */
    public AND(Condition c1, Condition c2) {
        this.c1 = c1;
        this.c2 = c2;
    }

    @Override
    public IntVarSet getVarSet() {
        return new IntVarHashSet(c1.getVarSet(), c2.getVarSet());
    }

    @Override
    public boolean getValue(IntVarConstMap assignments) {
        return c1.getValue(assignments) && c2.getValue(assignments);
    }
}



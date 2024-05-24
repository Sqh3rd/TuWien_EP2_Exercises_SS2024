package AB6;

/**
 * This class represents IS EQUAL condition. It represents the constraint that
 * two expressions are equal when their variables are assigned to specific values
 * (using the same variable assignments in both expressions).
 */
public class IsEqual implements Condition
{
    private final LinearExpression e1;
    private final LinearExpression e2;

    /**
     * Initializes 'this' with two linear expressions.
     * @param e1 the first expression, e1 != null.
     * @param e2 the second expression, e2 != null.
     */
    public IsEqual(LinearExpression e1, LinearExpression e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public IntVarSet getVarSet() {
        return new IntVarHashSet(e1.iterator(), e2.iterator());
    }

    /**
     * Returns e1.assignValue(assignments).equals(e2.assignValue(assignments)).
     * @param assignments the map with variable assignments, assignments != null.
     * @return e1.assignValue(assignments).equals(e2.assignValue(assignments)).
     */
    public boolean getValue(IntVarConstMap assignments) {
        return e1.assignValue(assignments).equals(e2.assignValue(assignments));
    }
}

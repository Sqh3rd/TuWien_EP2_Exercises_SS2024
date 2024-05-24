package AB6;

/**
 * This class represents a free variable which can take on integer values. Each object of
 * this class represents a different variable (regardless of the name). This means that
 * for two 'IntVar' reference values 'a' and 'b', a.equals(b) == true only if 'a' and 'b'
 * refer to the same object (a == b has value 'true').
 */
public class IntVar implements IntVarTerm {
    private final String name;

    /**
     * Initializes this variable with a specified name.
     *
     * @param name, the name != null.
     */
    public IntVar(String name) {

        this.name = name;
    }

    /**
     * Returns the name of this variable.
     *
     * @return the name of this variable.
     */
    public String getName() {

        return name;
    }

    @Override
    /**
     * Returns a readable representation of 'this', which is the name of this variable.
     * @return a readable representation of 'this'.
     */
    public String toString() {

        return name;
    }

    @Override
    public IntVar getVar() {
        return this;
    }

    @Override
    public IntConst getCoeff() {
        return LinearExpression.ONE;
    }

    @Override
    public LinearExpression plus(LinearExpression e) {
        return e.plus(this);
    }

    @Override
    public LinearExpression negate() {
        return new ConstVarProduct(LinearExpression.ONE.negate(), this);
    }

    @Override
    public LinearExpression times(IntConst c) {
        return new ConstVarProduct(c, this);
    }

    @Override
    public LinearExpression assignValue(IntVarConstMap varValues) {
        IntConst value = varValues.get(this);
        if (value == null)
            return this;
        return value;
    }

    @Override
    public IntVarIterator iterator() {
        return new GenericIterator(this);
    }

    @Override
    public IntVarSet varSet() {
        return new IntVarHashSet(this);
    }
}

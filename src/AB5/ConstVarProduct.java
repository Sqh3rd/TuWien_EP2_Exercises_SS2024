package AB5;

/**
 * This class represents a product of a constant coefficient and a variable (i.e. a linear term).
 */
public class ConstVarProduct implements IntVarTerm
{
    private IntConst coeff;
    private final IntVar var;

    /**
     * Initialized this product of 'coeff' and 'var' (for example 3x is such a product).
     * @param coeff the coefficient of the term which is not 0 or 1,
     *              coeff != null && coeff.isZero() == false &&
     *              coeff.plus(new IntConst(-1)).isZero == false.
     * @param var the variable in the term, var != null.
     */
    public ConstVarProduct(IntConst coeff, IntVar var) {
        this.coeff = coeff;
        this.var = var;
    }

    @Override
    public IntVar getVar() {
        return var;
    }

    @Override
    public IntConst getCoeff() {
        return coeff;
    }

    @Override
    public LinearExpression plus(LinearExpression e) {
        return e.plus(this);
    }

    @Override
    public LinearExpression negate() {
        return new ConstVarProduct(coeff.negate(), var);
    }

    @Override
    public LinearExpression times(IntConst c) {
        return c.isZero() ? ZERO : new ConstVarProduct(coeff.times(c), var);
    }

    @Override
    public LinearExpression assignValue(IntVarConstMap varValues) {
        IntConst assignedValue = varValues.get(var);
        if (assignedValue == null)
            return this;
        return assignedValue.times(coeff);
    }

    @Override
    public IntConst largestCoefficient() {
        return coeff;
    }

    @Override
    public IntVarIterator iterator() {
        return new SEIterator<>(var);
    }

    @Override
    public String toString() {
        if (coeff.plus(ONE.negate()).isZero())
            return var.toString();
        if (coeff.plus(ONE).isZero())
            return STR."-\{var.toString()}";
        return coeff.toString() + var.toString();
    }
}

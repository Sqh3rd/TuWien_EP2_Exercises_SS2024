package AB4;

/**
 * This class represents a simple linear expression. A linear expression is a sum of
 * linear terms each containing at most a single variable. For example, 3x - y + 5 is such an
 * expression.
 */
public class LinearExpression
{
    private IntConst intConst;
    private IntVarConstTreeMap varMap;

    /**
     * Constructs this linear expression from a specified constant.
     *
     * @param c the constant being wrapped as this linear expression, c != null.
     */
    public LinearExpression(IntConst c)
    {
        this.intConst = c;
        this.varMap = new IntVarConstTreeMap();
    }

    /**
     * Constructs this linear expression from a specified variable.
     *
     * @param v the variable being wrapped as this linear expression, v != null.
     */
    public LinearExpression(IntVar v)
    {
        this.intConst = new IntConst(0);
        this.varMap = new IntVarConstTreeMap();
        this.varMap.put(v, new IntConst(1));
    }

    /**
     * Constructs a linear expression as a copy of the specified expression.
     * Calling methods of this expression will not affect the specified expression
     * and vice versa.
     *
     * @param e the expression from which all the terms are copied to this new expression,
     *          e != null.
     */
    public LinearExpression(LinearExpression e)
    {
        this.intConst = e.intConst.plus(new IntConst(0));
        this.varMap = new IntVarConstTreeMap(e.varMap);
    }

    /**
     * Returns a new linear expression representing the sum of 'this' and 'v'.
     *
     * @param v the second summand != null.
     * @return the sum of 'this' and 'v'.
     */
    public LinearExpression plus(IntVar v)
    {
        LinearExpression result = new LinearExpression(this);
        result.varMap.putIfAbsent(v, new IntConst(0));
        result.varMap.put(v, result.varMap.get(v).plus(new IntConst(1)));
        return result;
    }

    /**
     * Returns a new linear expression representing the sum of 'this' and 'e'.
     *
     * @param e the second summand != null.
     * @return the sum of 'this' and 'e'.
     */
    public LinearExpression plus(LinearExpression e)
    {
        LinearExpression result = new LinearExpression(this);
        result.intConst = result.intConst.plus(e.intConst);
        e.varMap.getEntries().forEach(it -> {
            result.varMap.putIfAbsent(it.getKey(), new IntConst(0));
            result.varMap.put(it.getKey(), result.varMap.get(it.getKey()).plus(it.getValue()));
        });
        return result;
    }

    /**
     * Returns a new expression representing the negative of 'this', such that for an expression 'e'
     * the following condition holds: e.plus(e.negate()) represents 0.
     *
     * @return the negative of 'this'.
     */
    public LinearExpression negate()
    {
        LinearExpression result = new LinearExpression(this);
        result.intConst = result.intConst.negate();
        result.varMap.getEntries().forEach(entry -> {
            result.varMap.put(entry.getKey(), entry.getValue().negate());
        });
        return result;
    }

    /**
     * Returns a new expression resulting from assigning specific values to certain variables.
     * In this new expression the corresponding variables have been substituted by the constant
     * values, that were assigned to these variables through mappings in 'varValues'. For example,
     * if 'varValues' associates variable x with constant 2 and variable y with constant 3,
     * calling the method on the expression representing 2x + y will result in the expression
     * representing the constant 7. If 'varValues' contains the mapping for y, but no mapping for
     * x, then the result is the expression 2x + 3. If there is neither a mapping for x nor for y,
     * then the result is a new expression representing 2x + y.
     *
     * @param varValues the map containing mappings from variables to their assigned values,
     *                  varValues != null.
     * @return the new expression in which specific variables have been replaced by constant
     * values (as specified by 'varValues').
     */
    public LinearExpression assignValue(IntVarConstTreeMap varValues)
    {
        LinearExpression result = new LinearExpression(this);
        varValues.getEntries().forEach(entry -> {
            IntConst val = result.varMap.put(entry.getKey(), new IntConst(0));
            if (val == null) return;
            result.intConst = result.intConst.plus(val.times(entry.getValue()));
        });
        return result;
    }

    /**
     * A readable representation of this expression in which each of its variables appears only
     * once preceded by a coefficient, unless the coefficient is 1 or -1.
     * The variables appear in lexicographic order according to their names.
     * Terms that are zero are omitted. For example, -y + x + x - 4 would be represented by
     * "2x - y - 4" and x + y - x + 0 would be represented by "y".
     * (See Test.java for further examples.)
     *
     * @return readable representation of this expression.
     */
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        varMap.getEntries().forEach(entry -> {
            if (entry.getValue().isZero())
                return;
            if (!sb.isEmpty() && !entry.getValue().lessThan(new IntConst(0))) sb.append("+");
            if (!(entry.getValue().plus(new IntConst(1)).isZero()
                || entry.getValue().plus(new IntConst(-1)).isZero()))
                sb.append(entry.getValue().toString());
            else if (entry.getValue().lessThan(new IntConst(0)))
                sb.append("-");
            sb.append(entry.getKey().toString());
        });
        if (!sb.isEmpty() && intConst.isZero()) return sb.toString();
        if (!sb.isEmpty() && !intConst.lessThan(new IntConst(0))) sb.append("+");
        sb.append(intConst);
        return sb.toString();
    }
}



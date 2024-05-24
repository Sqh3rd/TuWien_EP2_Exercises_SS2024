package AB6;

/**
 * This class represents a linear expression consisting of more than one term.
 * For example, 3x - y + 5 consists of multiple terms. 'SumOfTerms' provides an iterator over all
 * variables occurring in 'this'. The order of the iteration is not specified.
 * In this example it iterates over elements 'x' and 'y'. This class implements 'LinearExpression'.
 */
public class SumOfTerms implements LinearExpression
{
    private final IntVarConstHashMap map;
    private IntConst c = LinearExpression.ZERO;

    private SumOfTerms(IntVarConstHashMap map, IntConst c) {
        this.map = new IntVarConstHashMap(map);
        this.c = c;
    }

    /**
     * Initializes 'this' as a sum of two terms, each with a variable.
     * @param t1 the first term in this sum.
     * @param t2 the second term in this sum.
     *          The following conditions holds: t1.getVar().equals(t2.getVar()) == false.
     */
    public SumOfTerms(IntVarTerm t1, IntVarTerm t2) {
        map = new IntVarConstHashMap();
        if (t1.getVar().equals(t2.getVar())) {
            map.put(t1.getVar(), t1.getCoeff().plus(t2.getCoeff()));
        } else {
            map.put(t1.getVar(), t1.getCoeff());
            map.put(t2.getVar(), t2.getCoeff());
        }
    }

    /**
     * Initializes 'this' as a sum of a term with a variable and a constant.
     * @param t the term != null.
     * @param c a constant != null, for which c.isZero() == false.
     */
    public SumOfTerms(IntVarTerm t, IntConst c) {
        map = new IntVarConstHashMap();
        map.put(t.getVar(), t.getCoeff());
        this.c = c;
    }

    @Override
    public LinearExpression plus(LinearExpression e) {
        if (e instanceof IntConst)
            return plus((IntConst) e);
        else if (e instanceof IntVarTerm)
            return plus((IntVarTerm) e);
        else if (e.getClass() != this.getClass())
            throw new IllegalArgumentException("No Idea how to handle that implementation of LinearExpression");

        SumOfTerms castArgument = (SumOfTerms) e;
        SumOfTerms result = new SumOfTerms(map, c.plus(castArgument.c));

        for (IntVar key : castArgument.map.keySet()) {
            IntConst coeff = result.map.get(key);
            IntConst otherCoeff = castArgument.map.get(key);
            if (coeff == null)
                result.map.put(key, otherCoeff);
            else if (!coeff.plus(otherCoeff).isZero())
                result.map.put(key, coeff.plus(otherCoeff));
            else
                result.map.remove(key);
        }
        return result;
    }

    @Override
    public LinearExpression plus(IntConst c) {
        return new SumOfTerms(map, this.c.plus(c));
    }

    @Override
    public LinearExpression plus(IntVarTerm t) {
        SumOfTerms result = new SumOfTerms(map, c);
        IntConst prevCoeff = result.map.get(t.getVar());
        if (prevCoeff != null && !prevCoeff.plus(t.getCoeff()).isZero())
            result.map.put(t.getVar(), prevCoeff.plus(t.getCoeff()));
        else if (prevCoeff != null)
            result.map.remove(t.getVar());
        else
            result.map.put(t.getVar(), t.getCoeff());
        return result;
    }

    @Override
    public LinearExpression negate() {
        SumOfTerms result = new SumOfTerms(map, c.negate());
        for (IntVar key : result.map.keySet())
            result.map.put(key, result.map.get(key).negate());
        return result;
    }

    @Override
    public LinearExpression times(IntConst c) {
        if (c.isZero())
            return ZERO;
        SumOfTerms result = new SumOfTerms(map, this.c.times(c));
        for (IntVar key : result.map.keySet())
            result.map.put(key, result.map.get(key).times(c));
        return result;
    }

    @Override
    public LinearExpression assignValue(IntVarConstMap varValues) {
        IntConst newConst = c;
        IntVarConstHashMap newMap = new IntVarConstHashMap();

        for (IntVar key : map.keySet()){
            IntConst assignedValue = varValues.get(key);
            if (assignedValue != null)
                newConst = newConst.plus(assignedValue.times(map.get(key)));
            else
                newMap.put(key, map.get(key));
        }

        if (newMap.size() == 1)
            return newConst.plus(ONE.negate()).isZero() ? newMap.keySet().iterator().next() :
                new ConstVarProduct(newConst, newMap.keySet().iterator().next());

        return newMap.size() == 0 ? newConst : new SumOfTerms(newMap, newConst);
    }

    @Override
    public IntVarIterator iterator() {
        return map.keySet().iterator();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        boolean firstEntry = true;
        for (IntVar key : map.keySet()) {
            IntConst coeff = map.get(key);
            if (coeff.lessThan(ZERO)) {
                if (coeff.plus(ONE).isZero())
                    sb.append("-");
                else
                    sb.append(coeff.toString());
                sb.append(key.toString());
                firstEntry = false;
                continue;
            }
            if (!firstEntry)
                sb.append("+");

            if (coeff.plus(ONE.negate()).isZero())
                sb.append(key.toString());
            else
                sb.append(STR."\{coeff.toString()}\{key.toString()}");
            firstEntry = false;
        }
        if (c.isZero())
            sb.append(firstEntry ? c.toString() : "");
        else
            sb.append(STR."\{firstEntry && c.negate().lessThan(ZERO) ? "" : "+"}\{c.toString()}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) return false;
        SumOfTerms sot = (SumOfTerms) obj;
        if (!sot.c.equals(c)) return false;
        return sot.map.equals(map);
    }

    @Override
    public IntVarSet varSet() {
        return map.keySet();
    }
}

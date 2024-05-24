package AB6;

import Collections.Set.HashSet;

/**
 * This class represents an ALL DIFFERENT condition.
 */
public class AllDifferent implements Condition
{
    private final IntVarSet set;

    /**
     * Initializes this 'AllDifferent' constraint object.
     *
     * @param set a set of variables which are required to have unique values
     *            across them. This set defines the scope of the constraint.
     */
    public AllDifferent(IntVarSet set) {
        this.set = set;
    }

    /**
     * Evaluates the condition based on current assignments.
     *
     * This method checks if the values assigned to the variables in the intersection
     * of this condition's variable set and the keys of the provided map are all unique.
     * Only variables that are both in the internal set and the key set of 'assignments'
     * are considered in the evaluation (all other variables are not considered when checking the
     * condition).
     *
     * @param assignments A map (IntVarConstMap) where keys are variables and values are their
     *                    assigned integer values. This map contains current assignments that
     *                    may or may not fully cover all variables in the problem.
     * @return true, if all considered variables have unique values, false otherwise.
     */
    public boolean getValue(IntVarConstMap assignments) {
        HashSet<IntConst> assignmentSet = new HashSet<>();
        for (IntVar key : set) {
            IntConst val = assignments.get(key);
            if (assignmentSet.contains(val)) return false;
            assignmentSet.add(val);
        }
        return true;
    }

    @Override
    public IntVarSet getVarSet() {
        return set;
    }
}

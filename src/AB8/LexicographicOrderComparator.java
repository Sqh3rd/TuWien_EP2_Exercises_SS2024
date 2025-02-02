package AB8;

/**
 * A lexicographic-order comparator that compares hierarchical systems first using one comparator
 * and, if the comparison is equal, using a second comparator.
 */
public class LexicographicOrderComparator implements SystemComparator {
    private final SystemComparator primary;
    private final SystemComparator secondary;

    /**
     * Initializes this comparator as a comparator that first uses the 'primary' comparator and,
     * if the first comparison results in equality, uses the 'secondary' comparator.
     *
     * @param primary the primary comparator to be used, must not be null.
     * @param secondary the secondary comparator to be used if the first comparator considers
     *               two systems equal, must not be null.
     */
    public LexicographicOrderComparator(SystemComparator primary, SystemComparator secondary) {
        this.primary = primary;
        this.secondary = secondary;
    }

    @Override
    /**
     * Compares two hierarchical systems lexicographically by first using the primary comparator.
     * If the primary comparator considers the systems equal, the secondary comparator is used.
     *
     * @param s1 the first hierarchical system to be compared, s1 != null.
     * @param s2 the second hierarchical system to be compared, s1 != null.
     * @return a negative integer, zero, or a positive integer if the first system is less than,
     *         equal to, or greater than the second system, respectively, according to the lexicographic
     *         order defined by the combination of the two comparators.
     */
    public int compare(HierarchicalSystem s1, HierarchicalSystem s2) {
        int initialComp = primary.compare(s1, s2);
        return initialComp != 0 ? initialComp : secondary.compare(s1, s2);
    }
}

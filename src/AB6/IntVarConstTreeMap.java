package AB6;

import Collections.Map.TreeMap;

import java.util.Comparator;

/**
 * This data structure maps variables ('IntVar' objects) to constants ('IntConst' objects).
 * It is implemented as a binary search tree where variables are sorted lexicographically according
 * their name using the 'compareTo' method of String. There is no limit on the number of
 * key-value mappings stored in the map.
 */
public class IntVarConstTreeMap extends TreeMap<IntVar, IntConst> implements IntVarConstMap
{
    public IntVarConstTreeMap() {
        super(Comparator.comparing(IntVar::getName));
    }

    /**
     * Constructs this map as a copy of the specified 'map'. This map has the same key-value mappings
     * as 'map'. However, if 'map' is changed later, it will not affect 'this' and vice versa.
     *
     * @param map the map from which key-value mappings are copied to this new map, map != null.
     */
    public IntVarConstTreeMap(IntVarConstTreeMap map) {
        super(map);
    }

    public IntVarSet keySet() {
        return (IntVarSet) super.keysAsSet();
    }
}


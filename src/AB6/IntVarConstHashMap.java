package AB6;

import Collections.List.Linked.SinglyLinkedList;
import Collections.Map.HashMap;
import Collections.Map.KeyValuePair;

/**
 * This data structure maps variables ('IntVar' objects) to constants ('IntConst' objects).
 * It is implemented as hash map. There is no limit on the number of key-value
 * mappings stored in the map. This class implements 'IntVarConstMap'.
 */
public class IntVarConstHashMap extends HashMap<IntVar, IntConst> implements IntVarConstMap
{
    /**
     * Initializes this map as an empty map.
     */
    public IntVarConstHashMap() {
        super(LinearExpression.ZERO);
    }

    /**
     * Initializes this map as an independent copy of the specified map. Later changes of 'this'
     * will not affect 'map' and vice versa.
     */
    public IntVarConstHashMap(IntVarConstHashMap map) {
        super(map);
    }

    @Override
    public IntVarSet keySet() {
        return new IntVarKeySet(this);
    }

    @Override
    public SinglyLinkedList<IntVar> keys() {
        SinglyLinkedList<IntVar> list = new SinglyLinkedList<>();
        for (SinglyLinkedList<KeyValuePair<IntVar, IntConst>> linkedList : arr) {
            if (linkedList == null) continue;
            for (KeyValuePair<IntVar, IntConst> entry : linkedList)
                list.push(entry.getKey());
        }
        return list;
    }

    public IntVarConstHashMap copy() {
        return new IntVarConstHashMap(this);
    }
}

package AB6;

import Collections.List.Linked.SinglyLinkedList;
import Collections.List.LinkedList;

import java.util.Objects;

public class IntVarSetSimpleImpl implements IntVarSetSimple {
    private final IntVarConstTreeMap map;
    private final IntVar[] keysToIgnore;

    IntVarSetSimpleImpl(IntVarConstTreeMap map) {
        this(map, new IntVar[0]);
    }

    IntVarSetSimpleImpl(IntVarConstTreeMap map, IntVar ...keysToIgnore) {
        this.map = map;
        this.keysToIgnore = keysToIgnore;
    }

    @Override
    public boolean contains(IntVar v) {
        for (IntVar ignore : keysToIgnore)
            if (Objects.equals(v, ignore)) return false;
        return map.containsKey(v);
    }

    @Override
    public IntVar[] asArray() {
        SinglyLinkedList<IntVar> allKeys = map.keys();
        for (IntVar ignore : keysToIgnore) {
            int index = allKeys.indexOf(ignore);
            if (index >= 0) allKeys.remove(index);
        }
        return allKeys.toArray();
    }
}

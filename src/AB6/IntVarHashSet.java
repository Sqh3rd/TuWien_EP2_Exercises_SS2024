package AB6;


import Collections.List.Linked.SinglyLinkedList;
import Collections.Queue.Queue;
import Collections.Set.HashSet;

import java.util.Iterator;

public class IntVarHashSet extends HashSet<IntVar> implements IntVarSet {

    public IntVarHashSet() {
        super();
    }

    public IntVarHashSet(IntVarIterator iter) {
        super(iter);
    }

    public IntVarHashSet(IntVar ...intVars) {
        super(intVars);
    }

    public IntVarHashSet(Iterator<IntVar> ...iterators) {
        super(iterators);
    }

    public IntVarHashSet(Iterable<IntVar> ...iterables) {
        super(iterables);
    }

    @Override
    public IntVarIterator iterator() {
        Queue<IntVar> result = new Queue<>();
        for (SinglyLinkedList<IntVar> list : arr)
            if (list != null) list.forEach(result::add);
        return new GenericIterator(result);
    }
}
